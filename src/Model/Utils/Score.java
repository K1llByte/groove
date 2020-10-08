package Model.Utils;

import org.jetbrains.annotations.Contract;

import java.io.Serializable;

public class Score implements Serializable
{
    public final static short MAX_SCORE = 50;
    public final static short MIN_SCORE = 0;
    private long num_scores;
    private short score;


    @Contract(pure = true)
    public Score()
    {
        num_scores = 0;
        score = 0;
    }


    private Score(Score s)
    {
        this.score = s.getScore();
        this.num_scores = s.getNum_scores();
    }


    public short getScore()
    {
        return score;
    }
    private long getNum_scores()
    {
        return num_scores;
    }

    //sets desnecessarios

    @Override
    public String toString()
    {
        return String.valueOf(score);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score1 = (Score) o;
        return num_scores == score1.num_scores &&
                score == score1.score;
    }

    public Score clone()
    {
        return new Score(this);
    }

    // ---------- Nonstandard Methods ---------- //

    public void addScore(short value) throws ScoreException
    {
        if(value >= MIN_SCORE && value <= MAX_SCORE)
            this.score = (short) ( (num_scores*score + value) / (++num_scores) );
        else
            throw new ScoreException("Invalid score bounds: [ " + MIN_SCORE + " , " + MAX_SCORE + " ]");
    }

    public void reset()
    {
        this.score = 0;
        this.num_scores = 0;
    }

    public static class ScoreException extends Exception
    {
        public ScoreException(String msg)
        {
            super(msg);
        }
    }
}
