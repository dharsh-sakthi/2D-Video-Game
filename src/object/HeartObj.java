package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class HeartObj extends SuperObject{

	GamePanel gp;
	
	public HeartObj(GamePanel gp) {
		this.gp=gp;
		name="Heart";
		try {
			image=ImageIO.read(getClass().getResourceAsStream("/objects/full.png"));
			
			image2=ImageIO.read(getClass().getResourceAsStream("/objects/half.png"));

			image3=ImageIO.read(getClass().getResourceAsStream("/objects/empty.png"));
			
			//scale images
			image=uTool.scaleImage(image, gp.tileSize, gp.tileSize);
			image2=uTool.scaleImage(image2, gp.tileSize, gp.tileSize);
			image3=uTool.scaleImage(image3, gp.tileSize, gp.tileSize);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
