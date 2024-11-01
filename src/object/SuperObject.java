package object;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Main.UtilityTool;

public class SuperObject {

	public BufferedImage image,image2,image3;
	
	public String name;
	public boolean collision=false;
	public int worldX;
	public int worldY;
	public Rectangle solidArea=new Rectangle(0,0,48,48);
	UtilityTool uTool= new UtilityTool();
}
