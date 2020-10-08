package Model;

import Model.Media.Audio;
import Model.Media.Media;
import Model.Media.Video;
import Model.Users.Admin;
import Model.Users.User;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MediaCenter implements MediaCenterMask
{
    private Map<User, Set<Audio>> owned_audio;
    private Map<User, Set<Video>> owned_video;
    private Map<User, Set<Playlist>> owned_playlists;
    private Map<String,Media> all_media;
    private Map<String,User> all_users;
    private User logged_in_user;

    public MediaCenter()
    {
        this.owned_audio = new HashMap<>();
        this.owned_video = new HashMap<>();
        this.all_media = new HashMap<>();
        this.all_users = new HashMap<>();
        this.owned_playlists = new HashMap<>();
        this.logged_in_user = null;
        this.addUser(new Admin("root","root","root"));
    }

    @Override
    public void addMedia(Media media)
    {
        this.all_media.put(media.getID(),media);

        if(media instanceof Audio)
            this.owned_audio.get(logged_in_user).add((Audio) media);
        else
            this.owned_video.get(logged_in_user).add((Video) media);
    }

    @Override
    public void addUser(User new_user)
    {
        this.all_users.put(new_user.getEmail(),new_user);
        this.owned_audio.put(new_user,new HashSet<>());
        this.owned_video.put(new_user,new HashSet<>());
        this.owned_playlists.put(new_user,new HashSet<>());
    }

    @Override
    public void login(String email, String password) throws Exception
    {
        User u;
        try { u = this.all_users.get(email); }
        catch(NullPointerException e) { throw new Exception("Invalid Email"); }

        if(u.checkPassword(password))
            this.logged_in_user = u;
        else
            throw new Exception("Invalid Password");
    }

    @Override
    public void logout()
    {
        this.logged_in_user = null;
    }

    @Override
    public User getLoggedInUser()
    {
        return this.logged_in_user;
    }

    public Collection<Media> getAllMedia()
    {
        return this.all_media.values();
    }

}
