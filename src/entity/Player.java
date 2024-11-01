package entity;

import java.awt.AlphaComposite;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;




import Main.GamePanel;
import Main.KeyHandler;


public class Player extends Entity {

	
	KeyHandler keyH;
	
	public final int screenY;
	public final int screenX;
	
	
	
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		super(gp);//calling constructor of superclass
		
		this.keyH=keyH;
		
		
		
		screenY=gp.screenHeight/2;
		screenX=gp.screenHeight/2;
		
		
		solidArea= new Rectangle();
		
		solidArea.x=8;
		solidArea.y=0;
		
		solidArea.width=32;
		solidArea.height=48;
;
		solidAreaDefaultX= solidArea.x;
		solidAreaDefaultY= solidArea.y;
		
		attackArea.width=32;
		attackArea.height=36;
		
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
	}
	
	public void setDefaultValues() {
		//player position
		worldX=100;
		worldY=385;
		speed=4;
		direction="right";
		
		//player status
		maxLife=6;
		life=maxLife;
		
	}
	
	public void getPlayerImage() {
		
		right1=setup("/player/player_right_1",gp.tileSize,gp.tileSize);
		right2=setup("/player/player_right_2",gp.tileSize,gp.tileSize);
		left1=setup("/player/player_left_1",gp.tileSize,gp.tileSize);
		left2=setup("/player/player_left_2",gp.tileSize,gp.tileSize);
		
	}
	
	public void getPlayerAttackImage() {
		attack=setup("/player/attack_2",gp.tileSize,gp.tileSize);
		attack1=setup("/player/attack_1",gp.tileSize*2,gp.tileSize);
//		lattack=setup("/player/left_attack_2",gp.tileSize,gp.tileSize);
//		lattack1=setup("/player/left_attack_1",gp.tileSize*2,gp.tileSize);
		
	}
	public void update(){
	
		if(attacking==true) {
			attacking();
		}
		
		//sprite counter only increases when one of the keys are pressed
		else if(keyH.rightPressed == true || keyH.leftPressed == true || keyH.enterPressed==true) {

			if(keyH.rightPressed==true) {
				direction="right";
			}
			else if(keyH.leftPressed==true){
				direction="left";
			}
			
			//check tile collision
			collisionOn=false;
			gp.colCheck.checkTile(this);
			
			//check monster collision
			int monsterIndex=gp.colCheck.checkEntity(this,gp.monster);
			
			interactMonster(monsterIndex);

			contactMonster(monsterIndex);
			
			gp.eHandler.checkEvent();
			
			
			//if collision is false, player can move
			if(collisionOn==false && keyH.enterPressed==false) {
				switch(direction) {
				case"right":
					worldX+=speed;
					break;
				case "left":
					worldX-=speed;
					break;
				}
			}
			
			gp.keyH.enterPressed=false;
			
			spriteCounter++;
			//switch between standing and running image
			if(spriteCounter>12) {
				if(spriteNum==1) {
					spriteNum=2;
				}
				else if(spriteNum==2) {
					spriteNum=1;
				}
				spriteCounter=0;
			}
		
		}
		
		//
		if(invincible==true) {
			invincibleCounter++;
			if(invincibleCounter>60) {
				invincible=false;
				invincibleCounter=0;
			}
		}
		
		if(life<=0) {
			gp.gameState=gp.gameOverState;
		}
	}
	
	public void attacking() {
		spriteCounter++;
		if(spriteCounter<=5) {
			spriteNum=1;
		}
		//displayed longer than first image
		if(spriteCounter>5 && spriteCounter<=25) {
			spriteNum=2;
			
			//temporary variables, save current values
			int currentWorldX=worldX;
			int currentWorldY=worldY;
			int solidAreaWidth=solidArea.width;
			int solidAreaHeight=solidArea.height;
			
			//adjust player x and y for attackArea
			switch(direction) {
			case "right":
				worldX+=attackArea.width;
				break;
			case "left":
				worldX-=attackArea.width;
				break;
			}
			
			//attackArea become solidArea
			solidArea.width=attackArea.width;
			solidArea.height=attackArea.height;
			
			//check monster collision with updated
			int monsterIndex=gp.colCheck.checkEntity(this, gp.monster);
			//damage monster
			damageMonster(monsterIndex);
			
			worldX=currentWorldX;
			worldY=currentWorldY;
			
			solidArea.width=solidAreaWidth;
			solidArea.height=solidAreaHeight;
			
			
		}
		if(spriteCounter>25) {
			spriteNum=1;
			spriteCounter=0;
			attacking=false;
			
		}
		
	}
	public void interactMonster(int i) {
		
			
			if(gp.keyH.enterPressed==true) {
				attacking=true;
			}
			
		
		gp.keyH.enterPressed=false;
	}
	public void contactMonster(int i) {
		if(i!=999) {
			//receives damage ewhen player is not invincible
			if(invincible==false) {
				life-=1;
				invincible=true;
			}
		}
	}
	
	public void damageMonster(int i){
		if(i!=999) {
			if(gp.monster[gp.currentMap][i].invincible==false) {
				gp.monster[gp.currentMap][i].life-=1;
				gp.monster[gp.currentMap][i].invincible=true;
				
				
				if(gp.monster[gp.currentMap][i].life<=0) {
					gp.monster[gp.currentMap][i]=null;
					
				}
			}
		}
		
	}
	
	
	
	
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image=null;
		
		
		switch(direction) {
		case "right":
			if(attacking==false) {
				if(spriteNum==1) {
					image=right1;
				}
				if(spriteNum==2) {
					image=right2;
				}
			}if(attacking==true) {
				if(spriteNum==1) {
					image=attack;
				}
				if(spriteNum==2) {
					image=attack1;
				}
			}
			break;
		case "left":
			if(spriteNum==1) {
				image=left1;
			}
			if(spriteNum==2) {
				image=left2;
//			}if(attacking==true) {
//				if(spriteNum==1) {
//					image=lattack;
//				}
//				if(spriteNum==2) {
//					image=lattack1;
//				}
			}
			
			break;
		}
		//make transparent when hit
		if(invincible==true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.5f));
		}
		
		g2.drawImage(image, worldX, worldY, null);
		
		//reset opacity
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
		
	}
}
