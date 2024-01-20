import java.util.concurrent.ThreadLocalRandom;

public class RandomColor {
    public String generate(){
        int r = ThreadLocalRandom.current().nextInt(0, 256);
        int g = ThreadLocalRandom.current().nextInt(0, 256);
        int b = ThreadLocalRandom.current().nextInt(0, 256);


        return  "rgb(" .concat(String.valueOf(r))
                .concat(", ")
                .concat(String.valueOf(g))
                .concat(", ")
                .concat(String.valueOf(b))
                .concat(")");
    }
}
