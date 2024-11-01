package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	
	public boolean rightPressed;
	public boolean leftPressed;
	public boolean enterPressed;
	
	GamePanel gp;

	public KeyHandler(GamePanel gp) {
		this.gp=gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code= e.getKeyCode();//returns integer keyCode of key event pressed
		
		if(code==KeyEvent.VK_RIGHT) {
			rightPressed=true;
		}
		
		if(code==KeyEvent.VK_LEFT) {
			leftPressed=true;
		}
		
		if(code==KeyEvent.VK_SPACE) {
			enterPressed=true;
		}
		if(code==KeyEvent.VK_E) {
			switch(gp.currentMap) {
			case 0:
				gp.backM.loadMap("/map/map2.txt", 1);
				break;
			}
			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code=e.getKeyCode();
		
		if(code==KeyEvent.VK_RIGHT) {
			rightPressed=false;
		}
		
		if(code==KeyEvent.VK_LEFT) {
			leftPressed=false;
		}
		if(code==KeyEvent.VK_ENTER) {
			enterPressed=false;
		}
		
	}

}
