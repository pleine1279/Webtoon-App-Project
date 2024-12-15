package WebtoonApp;


import java.awt.*;
import java.io.*;
import javax.swing.*;

public class UIpanel extends JFrame {
	public UIpanel(Webtoon[] webtoons) {
		super("웹툰 웹 이름");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		
		c.add(new NorthPanel(), BorderLayout.NORTH);
		c.add(new CenterPanel(webtoons), BorderLayout.CENTER);
		
		setSize(700,1000);
		setVisible(true);
	}
	
	class NorthPanel extends JPanel {
		public NorthPanel() {
			setLayout(new BorderLayout());
			
			JButton menuBtn = new JButton("메뉴");
			menuBtn.setPreferredSize(new Dimension(50, 30));
			add(menuBtn, BorderLayout.WEST);
			
			JTextField searchField = new JTextField();
			add(searchField, BorderLayout.CENTER);
			
			JButton searchBtn = new JButton("검색");
			searchBtn.setPreferredSize(new Dimension(50, 30));
			add(searchBtn, BorderLayout.EAST);
		}
	}
	class CenterPanel extends JPanel {
	    public CenterPanel(Webtoon[] webtoons) {
	        setLayout(new GridBagLayout());
	        GridBagConstraints gbc = new GridBagConstraints();

	        gbc.insets = new Insets(10, 10, 10, 10);
	        gbc.fill = GridBagConstraints.NONE;
	        gbc.anchor = GridBagConstraints.CENTER;
	        gbc.weightx = 0;
	        gbc.weighty = 0;

	        int columns = 4;
	        int rows = (int) Math.ceil((double) webtoons.length / columns);

	        for (int i = 0; i < webtoons.length && webtoons[i] != null; i++) {
	            int row = i / columns;
	            int col = i % columns;

	            gbc.gridx = col;
	            gbc.gridy = row;

	            add(WebtoonPanel(webtoons[i]), gbc);
	        }

	        // 빈 셀 추가
	        int totalCells = rows * columns;
	        for (int i = webtoons.length; i < totalCells; i++) {
	            gbc.gridx = i % columns;
	            gbc.gridy = i / columns;
	            add(PlaceholderPanel(), gbc);
	        }
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

	        imgLabel.setPreferredSize(new Dimension(150, 200)); // 이미지 크기 고정

	        JLabel nameLabel = new JLabel(webtoon.getTitle(), SwingConstants.CENTER);

	        panel.add(imgLabel, BorderLayout.CENTER);
	        panel.add(nameLabel, BorderLayout.SOUTH);

	        return panel;
	    }

	    private JPanel PlaceholderPanel() {
	        JPanel panel = new JPanel();
	        panel.setLayout(new BorderLayout());
	        panel.setPreferredSize(new Dimension(150, 240)); // 패널 크기 고정
	        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

	        JLabel imgLabel = new JLabel("빈 칸", SwingConstants.CENTER);
	        imgLabel.setPreferredSize(new Dimension(150, 200)); // 이미지 크기 고정

	        panel.add(imgLabel, BorderLayout.CENTER);
	        return panel;
	    }
	}
}