package Model.Media;

import Model.Utils.Score;

import java.awt.*;

public abstract class Media
{
    // Relative path to file
    private String path;
    // User rating
    private Score score;

    public Media(String path)
    {
        this.path = path;
        this.score = new Score();
    }

    public String getPath()
    {
        return this.path;
    }
    public short getRating()
    {
        return this.score.getScore();
    }

    public String getFileName()
    {
        String[] tmp1 = this.path.split("/");
        String[] tmp2 = tmp1[tmp1.length-1].split("\\.");
        return tmp2[tmp2.length-2];
    }


    public abstract String getID();

    public void addScore(byte new_score)
    {
        // We'll silence the exception because the GUI will not permit legally to input an invalid score
        // TODO: Analize this situation later
        try { this.score.addScore(new_score); }
        catch(Score.ScoreException e) { }
    }
}