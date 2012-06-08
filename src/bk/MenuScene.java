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
			
			// ������ҵ�ѡ��ʹ��Ϸ���벻ͬ��״̬
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
	 * ��ʼ���˵�ѡ��Y������
	 */
	private void initOptions(){
		
		optionsY = new int[totalLevelNum + 1];
		
		// ��ѡ���ܸ߶�����Ϊ100px����ѡ�ťƽ���ֲ�
		int dis = 100 / totalLevelNum;
		
		// ȷ��������ť�����ص�
		int btnHeight = btnImage.getHeight();
		if(dis < btnHeight) dis = btnHeight + 5;
		
		optionsY[0] = 110 + totalLevelNum*dis;
		
		for(int i = 1; i <= totalLevelNum; ++i){
			optionsY[i] = 110 + (i-1)*dis;
		}
		
	}

	
	/**
	 * ���Ʋ˵�ѡ��
	 */
	private void drawOptions(Graphics gra){
		
		gra.setColor(0, 0, 0);
		gra.setFont(Font.getFont(Font.FACE_SYSTEM,Font.STYLE_ITALIC,Font.SIZE_MEDIUM));
		
		// �����˳�ѡ�ť
		if(level == 0){
			gra.drawImage(selectedBtnImage, 85, optionsY[0], Graphics.TOP|Graphics.LEFT);
		}else{
			gra.drawImage(btnImage, 85, optionsY[0], Graphics.TOP|Graphics.LEFT);
		}		
		gra.drawString("��       ��", 95, optionsY[0], Graphics.LEFT|Graphics.TOP);
		
		// ���Ƹ��ؿ�ѡ�ť
		for(int i = 1; i <= totalLevelNum; ++i){
			if(level == i){
				gra.drawImage(selectedBtnImage, 85, optionsY[i], Graphics.TOP|Graphics.LEFT);
			}else{
				gra.drawImage(btnImage, 85, optionsY[i], Graphics.TOP|Graphics.LEFT);
			}
			gra.drawString("��    " + i + "  ��", 95, optionsY[i], Graphics.LEFT|Graphics.TOP);
		}		
	}
	
}
