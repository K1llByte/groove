package Model.Media;

import Model.Utils.Utils;

public class Video extends Media
{
    // Meta information
    private String name;

    public Video(String name,String path)
    {
        super(path);
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String getID()
    {
        return Utils.bytesToHexString(Utils.sha256(this.name + this.getFileName()));
    }
}
