package Model.Users;

import Model.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class User
{
    public static final class Username
    {
        private static final int MIN_LEN = 2;
        private static final int MAX_LEN = 32;
    }

    public static final class Password
    {
        private static final int MIN_LEN = 5;
        private static final int MAX_LEN = 32;
    }


    private String email;
    private String username;
    private String password_hash;
    private String salt;
    private List<User> friends;


    public User(String email, String username, String password)
    {
        this.email = email;
        this.username = username;
        this.salt = Utils.saltGenerator(8);
        this.password_hash = Utils.sha256String(password + salt);
        this.friends = new ArrayList<>();
    }

    public User(String email, String username, String password_hash, String salt)
    {
        this.email = email;
        this.username = username;
        this.salt = salt;
        this.password_hash = password_hash;
        this.friends = new ArrayList<>();
    }


    public String getEmail()
    {
        return email;
    }
    public String getUsername()
    {
        return username;
    }
    public String getPassword_hash()
    {
        return password_hash;
    }
    public String getSalt()
    {
        return salt;
    }
    public List<User> getFriends()
    {
        return friends;
    }


    public boolean checkPassword(String inPassword)
    {
        return this.password_hash.equals(Utils.sha256String(inPassword + this.salt));
    }


    public static boolean isValidPassword(String password)
    {
        int pw_leng = password.length();
        if (pw_leng < Password.MIN_LEN || pw_leng > Password.MAX_LEN)
            return false;


        // current mandated criteria, can be changed afterwards.
        boolean has_lowercase = false;
        boolean has_uppercase = false;
        boolean has_digit = false;
        boolean has_symbol = true;
        /*final char[] allowed_symbol = {'?','!'};*/

        for (int i = 0 ; i < pw_leng ; i++)
        {
            char c = password.charAt(i);
            if (Character.isLowerCase(c)) {
                has_lowercase = true;
            } else if (Character.isUpperCase(c)) {
                has_uppercase = true;
            } else if (Character.isDigit(c)) {
                has_digit = true;
            } else {
                //TODO: check symbol defenition , if symbols allowed are limited we have to change here
                has_symbol = true;
            }
        }

        return has_lowercase && has_uppercase && has_digit && has_symbol;
    }


    public static boolean isValidUsername(String username) {
        return true;
        /*final int username_len = username.length();

        if (username_len < Username.MIN_LEN ||
                username_len > Username.MAX_LEN)
        {
            return false;
        }

        boolean is_only_whitespace = true;

        for (int i = 0; i < username_len; ++i) {
            if (isWhiteSpace(username[i])) {
                if (i + 1 < username_len && isWhiteSpace(username[i + 1])) {
                    return false;  // repeated whitespace not allowed
                }
            } else {
                if (!(isLetterOrDigit(username[i]))
                {
                    return false;
                }
                is_only_whitespace = false;
            }
        }

        return !is_only_whitespace;*/
    }


    public static boolean isValidEmail(String email)
    {
        /*

        */
        return true;
    }

    public void addFriend(User u)
    {
        if(!this.friends.contains(u))
            this.friends.add(u);
    }

    public void removeFriend(User u)
    {
        this.friends.remove(u);
    }


    /* ========== Properties Traits ========== */

    public static final class UsernameTraits
    {
        public static final class Length
        {
            public static final int MIN = 3;
            public static final int MAX = 32;
        }
    }

    public static final class EmailTraits
    {
        public static final class Length
        {
            public static final class Recipient
            {
                public static final int MIN = 1;
                public static final int MAX = 64;
            }

            public static final class Domain
            {
                public static final int MIN = 1;
                public static final int MAX = 253;
            }

            public static final class TLD
            {
                public static final int MIN = 1;
                public static final int MAX = 64;
            }

            public static final class Full
            {
                public static final int MIN =
                        Recipient.MIN + Domain.MIN + TLD.MIN + 1; // '@' symbol.
                public static final int MAX =
                        Recipient.MAX + Domain.MAX + TLD.MAX + 1;
            }
        }
    }

    public static final class PasswordTraits
    {
        public static final class Length
        {
            public static final int MIN = 6;
            public static final int MAX = 32;
        }
    }
}
