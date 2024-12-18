package WebtoonApp;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class WebtoonApp {

    public static void main(String[] args) {
        Authentication auth = new Authentication();
        WebtoonManager manager = new WebtoonManager();

        try {
            manager.loadWebtoons("List.txt");
        } catch (IOException e) {
            System.out.println("웹툰 데이터를 로드하는 데 실패했습니다: " + e.getMessage());
        }

        // 로그인 화면 먼저 표시
        if (showLoginDialog(auth)) {
            playSound("login.wav");
        	SwingUtilities.invokeLater(() -> {
                new UIpanel(manager.getWebtoons()); // 메인 화면 표시
            });
        } else {
            JOptionPane.showMessageDialog(null, "프로그램을 종료합니다.");
            System.exit(0);
        }
    }

    private static boolean showLoginDialog(Authentication auth) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField(10);
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(10);

        panel.add(idLabel);
        panel.add(idField);
        panel.add(passLabel);
        panel.add(passField);

        while (true) {
            int option = JOptionPane.showConfirmDialog(null, panel, "로그인", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                String id = idField.getText();
                String password = new String(passField.getPassword());

                if (auth.login(id, password)) {
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "아이디 혹은 비밀번호가 잘못되었습니다.");
                }
            } else {
                return false;
            }
        }
    }
    
    private static void playSound(String filePath) {
        try {
            File soundFile = new File(filePath); // 사운드 파일 경로
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start(); // 사운드 재생
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("사운드를 재생할 수 없습니다: " + e.getMessage());
        }
    }
}
