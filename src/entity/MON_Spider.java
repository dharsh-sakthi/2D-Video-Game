package entity;

import entity.Entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;


import Main.GamePanel;

public class MON_Spider extends Entity {

	public MON_Spider(GamePanel gp) {
		super(gp);
		
		direction="left";
		worldX=100;
		worldY=385;
		speed=1;
		maxLife=3;
		life=maxLife;
		
		solidArea.x=3;
		solidArea.y=18;
		
		solidArea.width=42;
		solidArea.height=30;
		solidAreaDefaultX=solidArea.x;
		solidAreaDefaultY=solidArea.y;
		
		getImage();
		
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image=null;
		
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
	
	
	public void getImage() {
		right1=setup("/monster/monster1",gp.tileSize,gp.tileSize);
		right2=setup("/monster/monster1",gp.tileSize,gp.tileSize);
		left1=setup("/monster/monster2",gp.tileSize,gp.tileSize);
		left2=setup("/monster/monster2",gp.tileSize,gp.tileSize);
		
	}
	public void setAction() {
		
		actionLockCounter++;
		
		if(actionLockCounter==120) {
			
			Random random=new Random();
			int i= random.nextInt(100)+1;//random num from 1-100
			
			
			if(i<=50) {//50% chance of going right or left
				direction="right";
			}if(i>50 && i<=100) {
				direction="left";
			}
			
			actionLockCounter=0;
		}
	}

}
