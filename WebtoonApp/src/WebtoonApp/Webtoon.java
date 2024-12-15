package WebtoonApp;

public class Webtoon {
    private String title;
    private String author;
    private String genre;
    private String week;

    public Webtoon(String title, String author, String genre, String week) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.week = week;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getWeek() {
        return week;
    }
}
