package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;
import Main.UtilityTool;


public class Entity {
	
	GamePanel gp;

	public int worldX;
	public int worldY=385;//place ontop of grass
	public int speed;
	
	//character status
	public int maxLife;
	public int life;
	
	//describes an image with an accessible buffer of image data, stores image files
	public BufferedImage left1, left2, right1, right2;
	public BufferedImage attack,attack1,lattack,lattack1;
	public String direction;
	
	public int spriteCounter=0;
	public int spriteNum=1;
	
	//collision box
	public Rectangle solidArea=new Rectangle(0,0,48,48);  
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn=false;
	
	//hit detetcion rect
	public Rectangle attackArea=new Rectangle(0,0,0,0);
	
	public boolean invincible=false;
	public int invincibleCounter=0;
	
	boolean attacking=false;
	
	public int actionLockCounter=0;
	
	public Entity(GamePanel gp) {
		this.gp=gp;
	}
	
	public void setAction() {}
	public void update(){
		
		setAction();//created in main class
		
		
		collisionOn=false;
		gp.colCheck.checkTile(this);
		
		gp.colCheck.checkEntity(this, gp.monster);
		gp.colCheck.checkPlayer(this);
		
		
		//if collision is false, player can move
		
		if(collisionOn==false) {
			switch(direction) {
			case"right":
				worldX+=speed;
				break;
			case "left":
				worldX-=speed;
				break;
			}
		}
		
		spriteCounter++;
		//switch between left and right image
		if(spriteCounter>12) {
			if(spriteNum==1) {
				spriteNum=2;
			}
			else if(spriteNum==2) {
				spriteNum=1;
			}
			spriteCounter=0;
		}
		
		if(invincible==true) {
			invincibleCounter++;
			if(invincibleCounter>40) {
				invincible=false;
				invincibleCounter=0;
			}
		}
		
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image=null;
		
		g2.drawImage(image, worldX, worldY, null);
		
	
		switch(direction) {
		case "right":
			if(spriteNum==1) {
				image=right1;
			}
			if(spriteNum==2) {
				image=right2;
			}
			break;
		case "left":
			if(spriteNum==1) {
				image=left1;
			}
			if(spriteNum==2) {
				image=left2;
			}
			
			break;
		}
		
		if(invincible==true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.6f));
		}
	
		
		
		g2.drawImage(image, worldX, worldY, null);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
	
	}
	

	public BufferedImage setup(String imagePath, int width, int height) {
		UtilityTool uTool=new UtilityTool();
		BufferedImage image=null;
		
		
		try {
			
			image = ImageIO.read(getClass().getResourceAsStream(imagePath +".png"));
			image=uTool.scaleImage(image,width, height);
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}

}
