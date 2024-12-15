package WebtoonApp;

import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.*;

public class WebtoonManager {
    private Webtoon[] webtoons;
    private int size;

    public WebtoonManager() {
        this.webtoons = new Webtoon[30];
        this.size = 0;
    }

    private void expandArray() {
        this.webtoons = Arrays.copyOf(this.webtoons, this.webtoons.length * 2);
    }

    public void loadWebtoons(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String title = parts[0].trim();
                    String author = parts[1].trim();
                    String genre = parts[2].trim();
                    String day = parts[3].trim();

                    if (size == webtoons.length) {
                        expandArray();
                    }
                    webtoons[size++] = new Webtoon(title, author, genre, day);
                }
            }
        }
    }
    public Webtoon[] getWebtoons() {
        return Arrays.copyOf(this.webtoons, this.size);
    }
}

