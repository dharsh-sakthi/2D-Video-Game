package Main;

import entity.MON_Spider;

public class AssetSetter {

	GamePanel gp;
	
	public AssetSetter(GamePanel gp) {
		this.gp=gp;
	}
	
	public void setMonster() {
		int mapNum=0;
		gp.monster[mapNum][0]=new MON_Spider(gp);
		gp.monster[mapNum][0].worldX=500;
		gp.monster[mapNum][0].worldY=385;
		
		mapNum=1;
		
		gp.monster[mapNum][1]=new MON_Spider(gp);
		gp.monster[mapNum][1].worldX=200;
		gp.monster[mapNum][1].worldY=385;
		
		gp.monster[mapNum][2]=new MON_Spider(gp);
		gp.monster[mapNum][2].worldX=385;
		gp.monster[mapNum][2].worldY=385;
		
		
	}
	
	
}
