package Model.Media;

public enum Genre
{

    AFRO,
    ALTERNATIVE_ROCK,
    AVANT_GARDE,
    BLUES,
    CARIBBEAN,
    CHILDREN_S,
    CHRISTIAN,
    CLASSIC,
    COMEDY,
    CONTEMPORARY,
    CONTEMPORARY_RNB,
    COUNTRY,
    DANCE,
    ELETRONIC,
    FLAMENCO,
    FOLK,
    FUSION,
    HARDCORE,
    HEAVY_METAL,
    HIP_HOP,
    HOUSE,
    INSTRUMENTAL,
    JAZZ,
    LATIN,
    OPERA,
    POLKA,
    POP,
    PSYCHEDELIC,
    PUNK_ROCK,
    REGGAE,
    RNB,
    ROCK,
    SOUL,
    THECNO,
    TRANCE,
    TRAP,
    VOCAL,
    WEDDING,
    UNDIFINED;


    public String toName()
    {
        String text = this.toString().replace('_',' ');


        if (text.isEmpty()) {
            return text;
        }

        StringBuilder converted = new StringBuilder();

        boolean convertNext = true;
        for (char ch : text.toCharArray()) {
            if (Character.isSpaceChar(ch)) {
                convertNext = true;
            } else if (convertNext) {
                ch = Character.toTitleCase(ch);
                convertNext = false;
            } else {
                ch = Character.toLowerCase(ch);
            }
            converted.append(ch);
        }

        return converted.toString();
    }
}
