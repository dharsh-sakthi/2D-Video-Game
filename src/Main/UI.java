package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import object.HeartObj;
import object.SuperObject;

public class UI {

	GamePanel gp;
	BufferedImage full, half, empty;
	Graphics2D g2;
	
	public UI(GamePanel gp){
		this.gp=gp;
		
		//create hud object
		SuperObject heart= new HeartObj(gp);
		full= heart.image;
		half= heart.image2;
		empty=heart.image3;
		
		
	}
		
	public void draw(Graphics2D g2) {
		this.g2=g2;
		
		
		if(gp.gameState==gp.playState) {
			drawPlayerLife();
		}
		if(gp.gameState==gp.gameOverState) {
			drawGameOverScreen();
		}
		if(gp.gameState==gp.endState) {
			drawEndScreen();
		}
	}
	
	public void drawEndScreen() {
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);
		
		//game over
		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,100f));
		
		text="GAME OVER";
		
		g2.setColor(Color.WHITE);
		x=gp.tileSize;
		y=gp.tileSize*5;
		g2.drawString(text, x, y);
		
//		
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,50f));
		text="You got the Ruby!";
		x+=100;
		y+=gp.tileSize*4;
		g2.drawString(text, x, y);
	}
	
	public void drawGameOverScreen() {
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);
		
		//game over
		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,100f));
		
		text="GAME OVER";
		
		g2.setColor(Color.WHITE);
		x=gp.tileSize*2;
		y=gp.tileSize*5;
		g2.drawString(text, x, y);
		

	}
	public void drawPlayerLife() {
		//gp.player.life=8;
		int x=gp.tileSize/2;
		int y= gp.tileSize/2;
		int i=0;
		
		//draw max life
		while(i<gp.player.maxLife/2) {
			g2.drawImage(empty,  x,  y,  null);
			i++;
			x+=gp.tileSize;
		}
		
		//reset
		x=gp.tileSize/2;
		y= gp.tileSize/2;
		i=0;
		
		//draw current life
		//if i is less than current player life, draw half heart
		while(i<gp.player.life) {
			g2.drawImage(half, x, y, null);
			i++;
			//if i is less than current player life, draw full heart
			if(i<gp.player.life) {
				g2.drawImage(full,x,y,null);
			}
			i++;
			x+=gp.tileSize;
		}
		
	}
}
