package Model.Media;

import Model.Utils.Utils;

public class Audio extends Media
{
    // ---------- Data Defenition ---------- //

    // Meta information
    private String name;
    private String author;
    private Genre genre;

    // ---------- Constructors ---------- //

    public Audio(String name , String author , Genre genre, String path)
    {
        super(path);
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    // ---------- Getters and Setters ---------- //

    public String getName()
    {
        return name;
    }
    public String getAuthor()
    {
        return author;
    }
    public Genre getGenre()
    {
        return genre;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    public void setAuthor(String author)
    {
        this.author = author;
    }
    public void setGenre(Genre genre)
    {
        this.genre = genre;
    }

    // ---------- Nonstandard Methods ---------- //

    @Override
    public String getID()
    {
        return Utils.bytesToHexString(Utils.sha256(this.name + this.author + this.getFileName()));
    }

}