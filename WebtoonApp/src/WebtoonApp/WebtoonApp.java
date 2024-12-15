package WebtoonApp;


import java.io.IOException;

public class WebtoonApp {

    public static void main(String[] args) {
        WebtoonManager manager = new WebtoonManager();
        try {
            manager.loadWebtoons("List.txt");
        } catch (IOException e) {
            System.out.println("웹툰 데이터를 로드하는 데 실패했습니다: " + e.getMessage());
        }
        
        new UIpanel(manager.getWebtoons());
    }
}