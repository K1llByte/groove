package Model;

import Model.Exceptions.MediaAlreadyExists;
import Model.Exceptions.UserAlreadyExists;
import Model.Media.Audio;
import Model.Media.Genre;
import Model.Media.Media;
import Model.Media.Video;
import Model.Users.Admin;
import Model.Users.User;
import Model.Utils.Utils;

import java.sql.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class RemoteMediaCenter implements MediaCenterMask
{
    private final static String url = "jdbc:mariadb://localhost/Groove";
    private Connection connection;
    private Statement command;

    private User logged_in_user;


    public RemoteMediaCenter()
    {
        try
        {
            this.connection = DriverManager.getConnection(url,"dss","dss_admin");
            this.command = this.connection.createStatement();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void addMedia(Media media)
    {
        try
        {
            if(media instanceof Video)
            {
                Video tmp = (Video) media;

                this.command.executeUpdate(String.format("INSERT INTO Video (id_media,file_path,name) " +
                        "VALUES(\"%s\",\"%s\",\"%s\")",
                        tmp.getID(),tmp.getPath(),tmp.getName()));

                this.command.execute(String.format("INSERT INTO Owned_Media (user_email,id_media) " +
                        "VALUES(\"%s\",\"%s\");",this.logged_in_user.getEmail(),tmp.getID()));
            }
            else if(media instanceof Audio)
            {
                Audio tmp = (Audio) media;

                this.command.executeUpdate(String.format("INSERT INTO Audio (id_media,file_path,name,author,id_genre) " +
                                "VALUES(\"%s\",\"%s\",\"%s\",\"%s\",%d);",
                        tmp.getID(),tmp.getPath(),tmp.getName(),tmp.getAuthor(),tmp.getGenre().ordinal()));

                this.command.execute(String.format("INSERT INTO Owned_Media (user_email,id_media) " +
                        "VALUES(\"%s\",\"%s\");",this.logged_in_user.getEmail(),tmp.getID()));
            }
            else
                System.err.println("Nem um nem outro");
        }
        catch(SQLException e) { }
    }

    @Override
    public void addUser(User new_user) throws UserAlreadyExists
    {
        try
        {
            this.command.executeUpdate("INSERT INTO User (user_email,username,password,salt,is_admin) " +
                    "VALUES (\"" + new_user.getEmail() + "\",\""+new_user.getUsername()+"\",\""+
                    new_user.getPassword_hash()+"\",\""+new_user.getSalt()+"\","+(new_user instanceof Admin)+");");
        }
        catch(SQLException e)
        {
            throw new UserAlreadyExists();
        }
    }

    @Override
    public void login(String email, String password) throws Exception
    {
        User u;
        try
        {
            ResultSet cursor = this.command.executeQuery("SELECT username,password,salt,is_admin from User WHERE user_email = \"" + email + "\";");
            cursor.next();
            String username = cursor.getString(1);
            String password_hash = cursor.getString(2);
            String salt = cursor.getString(3);
            boolean is_admin = cursor.getBoolean(4);

            u = (is_admin) ?
                new Admin(email,username,password_hash,salt) :
                new User(email,username,password_hash,salt);
        }
        catch(SQLException e)
        {
            throw new Exception("Invalid Email/Password");
        }
        catch (NullPointerException e)
        {
            throw new Exception("Could not connect to server");
        }

        if(u.checkPassword(password))
            this.logged_in_user = u;
        else
            throw new Exception("Invalid Email/Password");
    }

    public void logout()
    {
        this.logged_in_user = null;
    }

    public User getLoggedInUser()
    {
        return this.logged_in_user;
    }

    @Override
    public Collection<Media> getAllMedia()
    {
        Set<Media> tmp = new HashSet<>();
        try
        {
            ResultSet cursor = this.command.executeQuery("SELECT name,file_path from Video;");
            while(cursor.next())
            {
                tmp.add(new Video(cursor.getString(1),cursor.getString(2)));
            }

            cursor = this.command.executeQuery("SELECT name,author,id_genre,file_path from Audio;");
            while(cursor.next())
            {
                //(String name , String author , Genre genre, String path)
                tmp.add(new Audio(cursor.getString(1),cursor.getString(2),Genre.values()[cursor.getInt(3)],cursor.getString(4)));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }

        return tmp;
    }
}
