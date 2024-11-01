package Main;
import entity.Entity;

public class CollisionChecker {

	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp=gp;
		
	}
	
	public void checkTile(Entity entity) {
		//find entity position
		int entityLeftX= entity.worldX + entity.solidArea.x;
		int entityRightX= entity.worldX + entity.solidArea.x+entity.solidArea.width;
		
		
		int entityLeftCol= entityLeftX/gp.tileSize;
		int entityRightCol= entityRightX/gp.tileSize;

		
		int tileNum1;
		int tileNum2;
		
		
		switch(entity.direction) {
		case "right":
			//predicts movement of entity
			entityRightCol= (entityRightX + entity.speed)/gp.tileSize;
			//there are 2 tiles that it will hit
			
			tileNum1=gp.backM.mapTileNum[gp.currentMap][entityRightCol][8];
			tileNum2=gp.backM.mapTileNum[gp.currentMap][entityRightCol][10];
			
			if(gp.backM.background[tileNum1].collision==true || gp.backM.background[tileNum2].collision==true) {
				entity.collisionOn=true;
			}
			break;
		case "left":
			//predicts movement of entity
			entityLeftCol= (entityLeftX - entity.speed)/gp.tileSize;
			//there are 2 tiles that it will hit
			tileNum1=gp.backM.mapTileNum[gp.currentMap][entityLeftCol][8];
			tileNum2=gp.backM.mapTileNum[gp.currentMap][entityLeftCol][10];
			
			if(gp.backM.background[tileNum1].collision==true || gp.backM.background[tileNum2].collision==true) {
				entity.collisionOn=true;
			}
			break;
		}
		
		
	}
	
	//check monster collision
	public int checkEntity(Entity entity, Entity[][] target) {
		
		int index=999;
		//scan array
		for(int i=0;i<target[1].length;i++) {
			
			if(target[gp.currentMap][i]!=null) {
				//get entity solid area position
				entity.solidArea.x=entity.worldX+entity.solidArea.x;
				entity.solidArea.y=entity.worldY+entity.solidArea.y;
				
				//get target solid area position
				target[gp.currentMap][i].solidArea.x=target[gp.currentMap][i].worldX + target[gp.currentMap][i].solidArea.x;
				target[gp.currentMap][i].solidArea.y=target[gp.currentMap][i].worldY + target[gp.currentMap][i].solidArea.y;
				
				//add a switch() a check entity's direction
				switch(entity.direction) {
				case "right":
					//simulating where entity will be after movement
					entity.solidArea.x+= entity.speed;
					//intersect() check if the entity and target are colliding
					if(entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
						if(target[gp.currentMap][i]!=entity) {
							entity.collisionOn=true;
							index=i;
						}
					}
					break;
				case "left":
					entity.solidArea.x-= entity.speed;
					//intersect() check if the entity and target are colliding
					if(entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
						if(target[gp.currentMap][i]!=entity) {
							entity.collisionOn=true;
							index=i;
						}
					}
					break;
				}
				
			
			
				//need to reset entity and target solidArea
				entity.solidArea.x=entity.solidAreaDefaultX;
				entity.solidArea.y=entity.solidAreaDefaultY;
				target[gp.currentMap][i].solidArea.x=target[gp.currentMap][i].solidAreaDefaultX;
				target[gp.currentMap][i].solidArea.y=target[gp.currentMap][i].solidAreaDefaultY ;
			}
			
		}
		return index;
	}
	
	public void checkPlayer(Entity entity) {
		
			//get entity solid area position
			entity.solidArea.x=entity.worldX+entity.solidArea.x;
			entity.solidArea.y=entity.worldY+entity.solidArea.y;
			
			//get target solid area position
			gp.player.solidArea.x=gp.player.worldX + gp.player.solidArea.x;
			gp.player.solidArea.y=gp.player.worldY + gp.player.solidArea.y;
			
			//add a switch() a check entity's direction
			switch(entity.direction) {
			case "right":
				//simulating where entity will be after movement
				entity.solidArea.x+= entity.speed;
				//intersect() check if the entity and target are colliding
				if(entity.solidArea.intersects(gp.player.solidArea)) {
					entity.collisionOn=true;
					
				}
				break;
			case "left":
				entity.solidArea.x-= entity.speed;
				if(entity.solidArea.intersects(gp.player.solidArea)) {
					entity.collisionOn=true;
					
				}
				break;
			}
			//need to reset entity and target solidArea
			entity.solidArea.x=entity.solidAreaDefaultX;
			gp.player.solidArea.x=gp.player.solidAreaDefaultX;
			entity.solidArea.y=entity.solidAreaDefaultY;
			gp.player.solidArea.y=gp.player.solidAreaDefaultY; 
		
	}
}
