package Model;

import Model.Exceptions.*;
import Model.Media.Media;
import Model.Users.User;
import java.util.Collection;

public interface MediaCenterMask
{
    void addMedia(Media media);

    void addUser(User new_user) throws UserAlreadyExists;

    void login(String email, String password) throws Exception;

    void logout();

    User getLoggedInUser();

    Collection<Media> getAllMedia();

    Collection<Media> getMediaUser();
}
