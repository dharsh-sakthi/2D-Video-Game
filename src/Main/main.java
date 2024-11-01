package Main;

import javax.swing.JFrame;

public class main {

	public static void main(String[] args) {
		
		JFrame frame=new JFrame();
			
		frame.setTitle("The Ruby Collector");//game title
		frame.setResizable(false);//prevent from resizing
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//exit out of app
		
		
		GamePanel gamePanel=new GamePanel();
		frame.add(gamePanel);
		//pack() causes frame to be sized to fit preferred size of GamePanel
		frame.pack();
		
		frame.setVisible(true);//makes frame visible
		frame.setLocationRelativeTo(null);
		
		gamePanel.setupGame();
		gamePanel.startGameThread();
		
	}

}
