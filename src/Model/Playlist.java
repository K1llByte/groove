package Model;

import Model.Media.Media;
import Model.Users.User;

import java.util.HashSet;
import java.util.Set;

public class Playlist
{
    // ---------- Data Defenition ---------- //

    private String playlist_name;
    private Set<Media> playlist;
    private User author;

    // ---------- Constructors ---------- //

    public Playlist(String playlist_name, User author)
    {
        this.playlist_name = playlist_name;
        this.playlist = new HashSet<>();
        this.author = author;
    }

    // ---------- Getters and Setters ---------- //

    public String getPlaylistName()
    {
        return playlist_name;
    }
    public Set<Media> getPlaylist()
    {
        return playlist;
    }
    public User getAuthor()
    {
        return author;
    }

    public void setPlaylistName(String playlist_name)
    {
        this.playlist_name = playlist_name;
    }

    // ---------- Nonstandard Methods ---------- //

    public void add(Media m)
    {
        this.playlist.add(m);
    }

    public void remove(Media m)
    {
        this.playlist.remove(m);
    }
    public void remove(String m)
    {
        this.playlist.forEach(e -> { if(e.getID().equals(m)) this.playlist.remove(e); } );
    }
}
