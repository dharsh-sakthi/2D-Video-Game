package background;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import Main.GamePanel;
import Main.UtilityTool;

public class BackgroundManager {
	
	GamePanel gp;
	public Background[] background;
	
	public int mapTileNum[][][];
	
	
	public BackgroundManager(GamePanel gp) {
		this.gp=gp;
		
		mapTileNum=new int[gp.maxMap][gp.maxScreenCol][gp.maxScreenRow];
		
		
		//create 10 kinds of background tile
		background= new Background[10];
		
		getBackgroundImage();
		loadMap("/maps/map1.txt",0);
		loadMap("/maps/map2.txt",1);
	}
	
	public void getBackgroundImage() {

		setup(0,"grass",false);
		setup(1,"sky0",false);
		setup(2,"cloud",false);
		setup(3,"dirt",false);
		setup(4,"skyCol",true);
		setup(5,"arrow",true);
		setup(6,"ruby",false);
		
		
		
	}
	public void setup(int index, String imagePath, boolean collision) {
		UtilityTool uTool=new UtilityTool();
		
		try {
			background[index]=new Background();
			background[index].image = ImageIO.read(getClass().getResourceAsStream("/background/"  +imagePath +".png"));
			background[index].image=uTool.scaleImage(background[index].image, gp.tileSize, gp.tileSize);
			background[index].collision= collision;
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	//place map data into 2Darray mapTileNum
	public void loadMap(String filePath, int map){
		try {
			//imports text file or map data
			InputStream is=getClass().getResourceAsStream(filePath);
			
			//read content of file
			BufferedReader br=new BufferedReader(new InputStreamReader(is));
			
			int col=0;
			int row=0;
			
			while(col<gp.maxScreenCol && row<gp.maxScreenRow) {
				
				String line=br.readLine();
				
				//get each number in txt file one by one and put in array
				while(col<gp.maxScreenCol) {
					String numbers[]=line.split(" ");
					
					//changes numbers from String array to int array because of readLine() and split()
					int num=Integer.parseInt(numbers[col]);
					
					mapTileNum[map][col][row]=num;
					col++;
					
				}
				if(col==gp.maxScreenCol) {
					col=0;
					row++;
				}
				
			}

			br.close();			
			
		}catch(Exception e) {
			
		}
	}
	
	public void draw(Graphics2D g2) {
		int col=0;
		int row=0;
		int x=0;
		int y=0;
		
		while(col<gp.maxScreenCol && row<gp.maxScreenRow) {
			
			int tileNum=mapTileNum[gp.currentMap][col][row];
			g2.drawImage(background[tileNum].image, x, y, null);
			col++;
			x+=gp.tileSize;
			if(col==gp.maxScreenCol) {
				col=0;
				x=0;
				row++;
				y+=gp.tileSize;
			}

		}
	}
}

