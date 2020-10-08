package Model.Users;

public class Admin extends User
{
    public Admin(String email, String username, String password)
    {
        super(email, username, password);
    }

    public Admin(String email, String username, String password_hash, String salt)
    {
        super(email, username, password_hash, salt);
    }

    public User register_user(String email, String username, String password, boolean is_admin)
    {
        return (is_admin) ?
                new Admin(email,username,password) :
                new User(email,username,password);
    }
}
