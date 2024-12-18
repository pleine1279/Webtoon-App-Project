package WebtoonApp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class UIpanel extends JFrame {
    private Webtoon[] originalWebtoons; // 원본 웹툰 데이터 저장

    public UIpanel(Webtoon[] webtoons) {
        super("웹툰 웹 이름");
        this.originalWebtoons = webtoons; // 원본 데이터 저장
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();
        c.setLayout(new BorderLayout());

        WebtoonLibrary library = new WebtoonLibrary();
        library.addWebtoons(Arrays.asList(webtoons)); // WebtoonLibrary에 전체 웹툰 추가

        CenterPanel centerPanel = new CenterPanel(webtoons);
        c.add(new NorthPanel(webtoons, centerPanel, library), BorderLayout.NORTH);
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        c.add(scrollPane, BorderLayout.CENTER);

        setSize(700, 1000);
        setVisible(true);
    }

    class NorthPanel extends JPanel {
        public NorthPanel(Webtoon[] webtoons, CenterPanel centerPanel, WebtoonLibrary library) {
            setLayout(new BorderLayout());

            // 상단 패널 (검색 필드와 버튼)
            JPanel topPanel = new JPanel(new BorderLayout());

            JButton menuBtn = new JButton("메뉴");
            menuBtn.setPreferredSize(new Dimension(50, 30));
            topPanel.add(menuBtn, BorderLayout.WEST);

            JTextField searchField = new JTextField();
            topPanel.add(searchField, BorderLayout.CENTER);

            JButton searchBtn = new JButton("검색");
            searchBtn.setPreferredSize(new Dimension(50, 30));
            topPanel.add(searchBtn, BorderLayout.EAST);

            add(topPanel, BorderLayout.NORTH);

            // 하단 패널 (정렬과 홈 버튼)
            JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

            JButton sortBtn = new JButton("정렬");
            sortBtn.setPreferredSize(new Dimension(70, 30));
            bottomPanel.add(sortBtn);

            JButton homeBtn = new JButton("홈");
            homeBtn.setPreferredSize(new Dimension(70, 30));
            bottomPanel.add(homeBtn);

            add(bottomPanel, BorderLayout.SOUTH);

            // 메뉴 버튼 이벤트
            menuBtn.addActionListener(e -> openMenuWindow());

            // 검색 버튼 이벤트
            searchBtn.addActionListener(e -> {
                String keyword = searchField.getText().trim();
                if (!keyword.isEmpty()) {
                    List<Webtoon> searchResults = library.search(keyword);
                    centerPanel.updateWebtoons(searchResults.toArray(new Webtoon[0]));
                } else {
                    JOptionPane.showMessageDialog(null, "검색어를 입력하세요.");
                }
            });

            // 정렬 버튼 이벤트
            sortBtn.addActionListener(e -> {
                new Thread(() -> {
                    Webtoon[] sortedWebtoons = centerPanel.webtoons.clone();
                    Arrays.sort(sortedWebtoons, Comparator.comparing(Webtoon::getTitle));
                    SwingUtilities.invokeLater(() -> centerPanel.updateWebtoons(sortedWebtoons));
                }).start();
            });

            // 홈 버튼 이벤트 (원래 목록으로 돌아감)
            homeBtn.addActionListener(e -> {
                centerPanel.updateWebtoons(originalWebtoons);
                searchField.setText(""); // 검색어 필드 초기화
            });
        }
    }

    private void openMenuWindow() {
        JFrame menuFrame = new JFrame("메뉴");
        menuFrame.setSize(300, 200);
        menuFrame.setLayout(new GridLayout(5, 1));

        JButton naverBtn = createLinkButton("네이버 웹툰", "https://comic.naver.com/");
        JButton kakaoBtn = createLinkButton("카카오 웹툰", "https://webtoon.kakao.com/");
        JButton lezhinBtn = createLinkButton("레진코믹스", "https://www.lezhin.com/");

        JButton logoutBtn = new JButton("로그아웃");
        logoutBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "로그아웃 되었습니다.");
            menuFrame.dispose();
            dispose();
        });

        menuFrame.add(naverBtn);
        menuFrame.add(kakaoBtn);
        menuFrame.add(lezhinBtn);
        menuFrame.add(new JLabel(""));
        menuFrame.add(logoutBtn);

        menuFrame.setVisible(true);
    }

    private JButton createLinkButton(String text, String url) {
        JButton button = new JButton(text);
        button.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new URI(url));
            } catch (IOException | URISyntaxException ex) {
                JOptionPane.showMessageDialog(null, "링크를 열 수 없습니다.");
            }
        });
        return button;
    }

    class CenterPanel extends JPanel {
        private Webtoon[] webtoons;

        public CenterPanel(Webtoon[] webtoons) {
            this.webtoons = webtoons;
            initializeUI();
        }

        private void initializeUI() {
            removeAll();
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();

            gbc.insets = new Insets(5, 5, 5, 5);
            gbc.fill = GridBagConstraints.BOTH;

            int columns = 4;
            for (int i = 0; i < webtoons.length; i++) {
                int row = i / columns;
                int col = i % columns;

                gbc.gridx = col;
                gbc.gridy = row;

                add(WebtoonPanel(webtoons[i]), gbc);
            }
            revalidate();
            repaint();
        }

        public void updateWebtoons(Webtoon[] newWebtoons) {
            this.webtoons = newWebtoons;
            SwingUtilities.invokeLater(this::initializeUI);
        }

        private JPanel WebtoonPanel(Webtoon webtoon) {
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.setPreferredSize(new Dimension(150, 240));
            panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            String imgPath = "images/" + webtoon.getTitle() + ".jpg";
            JLabel imgLabel;

            if (new File(imgPath).exists()) {
                ImageIcon imgIcon = new ImageIcon(new ImageIcon(imgPath).getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH));
                imgLabel = new JLabel(imgIcon, SwingConstants.CENTER);
            } else {
                imgLabel = new JLabel("이미지 없음", SwingConstants.CENTER);
            }
            imgLabel.setPreferredSize(new Dimension(150, 200));

            JPanel infoPanel = new JPanel(new GridLayout(3, 1));
            JLabel nameLabel = new JLabel(webtoon.getTitle(), SwingConstants.CENTER);
            JLabel authorLabel = new JLabel("작가: " + webtoon.getAuthor(), SwingConstants.CENTER);
            JLabel weekLabel = new JLabel("요일: " + webtoon.getDay() + " | 평점: " + webtoon.getRate(), SwingConstants.CENTER);

            infoPanel.add(nameLabel);
            infoPanel.add(authorLabel);
            infoPanel.add(weekLabel);

            panel.add(imgLabel, BorderLayout.CENTER);
            panel.add(infoPanel, BorderLayout.SOUTH);

            return panel;
        }
    }
}