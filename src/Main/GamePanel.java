package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;


import javax.swing.JPanel;

import background.BackgroundManager;
import entity.Entity;
import entity.Player;

public class GamePanel extends JPanel implements Runnable {

	
	//Screen Settings variables
	final int originalTileSize=16;//16x16 tile
	public final int scale=3;//scale for higher resolution. Common scaling for 2D games
	
	public final int tileSize=originalTileSize*scale;//48x48 tile
	
	public final int maxScreenCol=16;//16 tiles horizontally
	public final int maxScreenRow=12;//12 tiles vertically
	//Game Screen Size
	public final int screenWidth=tileSize*maxScreenCol;//768 pixels
	public final int screenHeight=tileSize*maxScreenRow;//576 pixels
	
	public final int maxWorldCol=16;
	public final int maxWorldRow=16;
	
	public final int worldWidth=tileSize*maxWorldCol;
	public final int worldHeight=tileSize*maxWorldRow;
	
	
	public final int maxMap=10;
	public int currentMap=0;
	
	//FPS
	int FPS=60;
	
	//SYSTEM
	//background
	BackgroundManager backM= new BackgroundManager(this);
	//key movement
	public KeyHandler keyH= new KeyHandler(this);
	public UI ui=new UI(this);
	public EventHandler eHandler=new EventHandler(this);
	
	//grameThread keeps program running until it is asked to stop
	Thread gameThread;
	
	//collsion
	public CollisionChecker colCheck= new CollisionChecker(this);
	public AssetSetter aSetter= new AssetSetter(this);
	
	//player, entity
	public Player player= new Player(this,keyH);
	public Entity monster[][]=new Entity[maxMap][10];//num monsters that can be displayed at same time
	ArrayList<Entity> entityList= new ArrayList<>();//all entities into this list, to order entity drawings	
	
	//game state
	public int gameState;
	public final int playState=1;
	public final int gameOverState=2;
	public final int endState=3;
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));//set panel size of this class
		this.setBackground(Color.black);
		
		//if setDoubleBuffered set true, all the drawings from this component will be done in an off screen buffer
		this.setDoubleBuffered(true);//enabling this will improve rendering performance
		
		
		this.addKeyListener(keyH);//GamePanel will recognize the key input
		
		this.setFocusable(true);//GamePanel is focused to receive key input
		
	}
	
	public void setupGame() {
		aSetter.setMonster();
		gameState=playState;
	}
	
	public void startGameThread() {
		gameThread=new Thread(this);//passing GamePanel's class to this thread's constructor
		gameThread.start();
	}
	@Override
	public void run() {
		
		double drawInterval=1000000000/FPS;//draws it frame every 0.01666 sec
		//uses current time to allocate time for a single loop 
		double nextDrawTime=System.nanoTime()+drawInterval;	
		
		//as long as gameThread exists, process inside while loop repeats
		while(gameThread!=null) {
			
			//Update info on character positions
			update();
			//Redraw the screen with the update info
			repaint();//actually calling paintComponent
			
			try {
				//remaining time between repaint() and nextDrawTime
				double remainingTime=nextDrawTime-System.nanoTime();
				//convert nano to milli because sleep() parameter excepts long milli
				remainingTime= remainingTime/1000000;
				
				//if update() and repaint() used more than allocated time, no need to sleep()
				if(remainingTime<0) {
					remainingTime=0;
				}
				
				//pauses game loop, will not do anything until sleep time is over
				Thread.sleep((long) remainingTime);
				
				//once sleep() is over, set nextDrawTime to 0.01666sec later
				nextDrawTime+=drawInterval;
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void update() {
		
		if(gameState==playState) {
			player.update();
			for(int i=0;i<monster[1].length;i++) {
				if(monster[currentMap][i]!=null) {
					monster[currentMap][i].update();
				}
			}
		}		
	}
	
	//standard method to draw on JPanel
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//extends Graphic class to provide more sophisticated control over geometry, color management, and text layout
		Graphics2D g2=(Graphics2D)g;//changing Graphics g to Graphic2D
		
		//draw background first, then entities
		backM.draw(g2);
		
			
		
		//monster 
		for(int i=0;i<monster[1].length;i++) {
			if(monster[currentMap][i]!=null) {
				entityList.add(monster[currentMap][i]);
			}
		}

	      	entityList.add(player);
		
		
		//draw entities
		for(int i=0;i<entityList.size();i++) {
			entityList.get(i).draw(g2);
		}
		
		//after drawn, remove everything from list
		entityList.clear();
		
		
		//UI
		ui.draw(g2);
		if(gameState==playState) {
			g2.setFont(new Font("Arial", Font.PLAIN,20));
			g2.setColor(Color.white);
			g2.drawString("Get the Ruby!", 10, 200);
			g2.drawString("Use arrow keys to move", 10, 225);
			g2.drawString("Attack monster using the space bar", 10, 250);
		}
		
		g2.dispose();//disposes of this graphics context and release system resources
		
	}
}
