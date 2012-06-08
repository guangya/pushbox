package bk;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;


public class MenuScene extends Scene {
	
	private Image menuImage,btnImage, selectedBtnImage;
	private int level;
	private int totalLevelNum;
	private int optionsY[];

	protected void initialize() {
		
		level = 1;
		totalLevelNum = 3;
		
		this.setFullScreenMode(true);
		
		try{
			menuImage = Image.createImage("/image/push_menu.png");
			btnImage = Image.createImage("/image/button.png");
			selectedBtnImage = Image.createImage("/image/button-selected.png");
		}catch(Exception e){}
		
		initOptions();
		
	}

	protected void process(Graphics gra) {
		
		sleepTime = 100;
		switch(this.getKeyStates()){
		case GameCanvas.UP_PRESSED:	
			level = (level == 0)? totalLevelNum : level -1;
			break;				
		case GameCanvas.DOWN_PRESSED:	
			++level;
			break;	
		case GameCanvas.FIRE_PRESSED:
			GameEngine.setLevel(level);
			
			// 根据玩家的选择，使游戏进入不同的状态
			if(level == 0){
				GameEngine.setState(GameEngine.GAME_STATE_EXIT);
			}else{
				GameEngine.setState(GameEngine.GAME_STATE_RUN);
			}
			
			RUN = false;
			break;
		}		
		
		level %= totalLevelNum + 1;
		
		gra.drawImage(menuImage, 0, 0, Graphics.TOP|Graphics.LEFT);
		drawOptions(gra);
		
	}
	
	
	/**
	 * 初始化菜单选项Y轴坐标
	 */
	private void initOptions(){
		
		optionsY = new int[totalLevelNum + 1];
		
		// 各选项总高度设置为100px，各选项按钮平均分布
		int dis = 100 / totalLevelNum;
		
		// 确保各个按钮不会重叠
		int btnHeight = btnImage.getHeight();
		if(dis < btnHeight) dis = btnHeight + 5;
		
		optionsY[0] = 110 + totalLevelNum*dis;
		
		for(int i = 1; i <= totalLevelNum; ++i){
			optionsY[i] = 110 + (i-1)*dis;
		}
		
	}

	
	/**
	 * 绘制菜单选项
	 */
	private void drawOptions(Graphics gra){
		
		gra.setColor(0, 0, 0);
		gra.setFont(Font.getFont(Font.FACE_SYSTEM,Font.STYLE_ITALIC,Font.SIZE_MEDIUM));
		
		// 绘制退出选项按钮
		if(level == 0){
			gra.drawImage(selectedBtnImage, 85, optionsY[0], Graphics.TOP|Graphics.LEFT);
		}else{
			gra.drawImage(btnImage, 85, optionsY[0], Graphics.TOP|Graphics.LEFT);
		}		
		gra.drawString("退       出", 95, optionsY[0], Graphics.LEFT|Graphics.TOP);
		
		// 绘制各关卡选项按钮
		for(int i = 1; i <= totalLevelNum; ++i){
			if(level == i){
				gra.drawImage(selectedBtnImage, 85, optionsY[i], Graphics.TOP|Graphics.LEFT);
			}else{
				gra.drawImage(btnImage, 85, optionsY[i], Graphics.TOP|Graphics.LEFT);
			}
			gra.drawString("第    " + i + "  关", 95, optionsY[i], Graphics.LEFT|Graphics.TOP);
		}		
	}
	
}
