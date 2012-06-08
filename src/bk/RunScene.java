package bk;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.Vector;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

import bk.GameEngine;

public class RunScene extends Scene implements CommandListener{
	
	private Image  mapImage, playerImage, boxImage;
	private Sprite player, aBoxes[];
	private int boxNum;
	private int count;
	private TiledLayer backMap, wallMap, destMap;
	
	// ��Ϸ���н���Ĳ˵���
	private Command cmdRestart;
	private Command cmdResume;
	private Command cmdExit;
	private Command cmdCancel;

	protected void initialize() {
		// ���س����õ�����Դ
		try{
			mapImage = Image.createImage("/image/pushbox.png");	
			playerImage = Image.createImage(mapImage, 96, 0, 32, 16, 0);
			boxImage = Image.createImage(mapImage, 48, 0, 16, 16, 0);
		}catch(Exception e){}		

		// ��ʼ�����
		player = new Sprite(playerImage, 16, 16);
		
		// ��ʼ������ͼ����
		backMap = new TiledLayer(15, 18, mapImage, 16, 16);
		wallMap = new TiledLayer(15, 18, mapImage, 16, 16);
		destMap = new TiledLayer(15, 18, mapImage, 16, 16);		
		
		// ��ʼ����Ϸ��ͼ
		initMap(GameEngine.getLevel());
		
		// ��ʼ���˵���
		cmdRestart = new Command("���¿�ʼ����",Command.SCREEN, 1);
		cmdResume = new Command("�������˵�",Command.SCREEN, 1);
		cmdExit = new Command("�˳�",Command.SCREEN, 1);	
		cmdCancel = new Command("ȡ��",Command.SCREEN, 1);
		
		// ��Ӳ˵�����ʾ��
		addCommand(cmdRestart);
		addCommand(cmdResume);
		addCommand(cmdExit);	
		addCommand(cmdCancel);
		this.setCommandListener(this);		
	}

	protected void process(Graphics gra) {		
		switch(this.getKeyStates()){
		case GameCanvas.LEFT_PRESSED:
			player.setFrame(1);
			if(testCollision(-16, 0)) player.move(16, 0);
			++count;				
			break;
		
		case GameCanvas.RIGHT_PRESSED:
			player.setFrame(0);
			if(testCollision(16, 0)) player.move(-16, 0);
			++count;						
			break;
									
		case GameCanvas.UP_PRESSED:
			if(testCollision(0, -16)) player.move(0, 16);
			++count;					
			break;
		
		case GameCanvas.DOWN_PRESSED:
			if(testCollision(0, 16)) player.move(0, -16);
			++count;					
			break;			
		}
		
		gra.setColor(0, 0, 0);
		gra.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		if(isAllToDest()){
			// ����
			GameEngine.setState(GameEngine.GAME_STATE_WIN);
			RUN = false;
		}else{
			backMap.paint(gra);
			wallMap.paint(gra);
			destMap.paint(gra);
			player.paint(gra);
			
			// ��������
			int boxNum = aBoxes.length;
			for(int i = 0; i < boxNum; ++i){
				aBoxes[i].paint(gra);
			}
		}
	}
	
	/**
	 * ��ʼ����Ϸ����
	 * 
	 * @param level ��ǰ�ؿ�
	 */
	public void initMap(int level){
		count = 0;
		
		// ��ʱ�����������洢���Ӷ���
		Vector vBoxes = new Vector();
		
		try{
			InputStream is = this.getClass().getResourceAsStream("/map/pushbox_map" + level + ".MAR");
			DataInputStream dis = new DataInputStream(is);			
			for(int i = 0; i < 18; ++i){				
				for(int j = 0; j < 15; ++j){
					short a = dis.readShort();
					int b = 0x07 & a >> 13;
					switch(b){
						case 1:
							wallMap.setCell(j, i, 1);
							break;
						case 2:
							backMap.setCell(j, i, 2);
							break;
						case 7:
							backMap.setCell(j, i, 2);
							player.setPosition(16*j, 16*i);	
							break;
						case 4:
							backMap.setCell(j, i, 2);
							
							// ��ͼ�г������ӣ����½�һ�����Ӷ��󣬲��洢��vBoxes�� 
							Sprite newBox = new Sprite(boxImage, 16, 16);
							newBox.setPosition(16*j, 16*i);
							vBoxes.addElement(newBox);
							break;
						case 6:
							destMap.setCell(j, i, 6);
							break;
						default :
							backMap.setCell(j, i, b);	
							break;
					}						
				}
			}	
		}catch(Exception e){}	
		
		// ���ӳ�ʼ����ϣ������Ӷ���ת�浽������
		boxNum = vBoxes.size();
		aBoxes = new Sprite[boxNum];
		vBoxes.copyInto(aBoxes);
		vBoxes.removeAllElements();
	}
	
	/**
	 * �˵���������
	 */
	public void commandAction(Command c, Displayable d){
		if(c == cmdExit){
			// �˳���Ϸ
			GameEngine.setState(GameEngine.GAME_STATE_EXIT);
			RUN = false;
		}else if(c == cmdResume){
			// �������˵�
			GameEngine.setState(GameEngine.GAME_STATE_MENU);
			RUN = false;
		}else if(c == cmdCancel){
			// �رղ˵���������Ϸ
		}else if(c == cmdRestart){
			// ���¿�ʼ���ؿ�
			System.gc();
			initMap(GameEngine.getLevel());
		}
	}

	/**
	 * ������ײ
	 * 
	 * @param int dx ��x����Ҫ�ƶ���������
	 * @param int dy ��y����Ҫ�ƶ���������
	 * @return bool
	 */
	private boolean testCollision(int dx, int dy){
		player.move(dx, dy);
		
		// ���ͼ��ײ
		if(player.collidesWith(wallMap, false)){
			return true;
		}
		
		// ��������ӵ���ײ�Լ����������ӡ�������ǽ�ڵ���ײ
		int boxNum = aBoxes.length;
		for(int i = 0; i < boxNum; ++i){
			if(player.collidesWith(aBoxes[i], false)){
				aBoxes[i].move(dx, dy);
				if(aBoxes[i].collidesWith(wallMap, false)|| testBoxesCollision(i, false)){
					aBoxes[i].move(-dx, -dy);
					return true;
				}
			}
		}
		
		// ��Ҳ�����Ч
		return false;
	}	
	
	/**
	 * ��鵱ǰ�������Ƿ�����������ӷ�������ײ
	 * 
	 * @param curBoxIndex ��ǰ��������������aBoxes�е�����
	 * @param pixelLevel  �Ƿ�������ؼ������ײ���
	 * @return boolean ���������ײ������true�����򣬷���false
	 */
	private boolean testBoxesCollision(int curBoxIndex, boolean pixelLevel){
		for(int i = 0; i < boxNum; ++i){
			if(i == curBoxIndex){
				continue;
			}
			if(aBoxes[curBoxIndex].collidesWith(aBoxes[i], pixelLevel)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * ����Ƿ��������Ӿ������͵�Ŀ�ĵ�
	 * 
	 * @return boolean
	 */
	private boolean isAllToDest(){
		for(int i = 0; i < boxNum; ++i){
			if(!aBoxes[i].collidesWith(destMap, false)){
				return false;
			}
		}
		return true;
	}
}