package bk;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.midlet.MIDlet;


/**
 * ���֣���������Ϸ��飩����Ļ���뵭�룬��ȫ��ʾ��ͣ��5���Ӻ�Ȼ���������һ���ֱ����ʧ�������֮�������˵���
 * �����������������ܻ����˵��е��л�Ч��������������л�������л�������ֹͣ��
 * 
 * 1����������
 * 2�����ִ��·��˶�����Ļ����
 * 
 * 
 * @author Guangya
 *
 */

public class Engineold extends GameCanvas implements Runnable{
	
	private static final int GAME_STATE_EXIT      = 0;
	private static final int GAME_STATE_SWITCH    = 1;
	private static final int GAME_STATE_INTRO     = 2;
	private static final int GAME_STATE_MENU      = 3;
	private static final int GAME_STATE_RUNNING   = 4;
	private static final int GAME_STATE_WIN       = 5;
	
	private int state;
	private int sleepTime;
	private boolean RUN;
	private Thread thread;
	private Graphics gra;
	
	// ǰһ������ǰ����һ������ʹ�õĻ���
	private Graphics preGra;
	private Graphics curGra;
	private Graphics nextGra;
	
	// ���˵�
	private Menu mainMenu;
	
	// ��������
	private int canvasWidth;
	private int canvasHeight;
	
	
	public Engineold(){
		super(true);
		
		gra = this.getGraphics();
		RUN = true;
		sleepTime = 100;		
		
//		mainMenu = new Menu(this);
		
		thread = new Thread(this);
		thread.start();
	}
	
	public static Engineold getInstance(MIDlet midlet){
		return new Engineold();
	}
	
	/**
	 * �������߼�
	 * 
	 * @return void
	 */
	public void process(){
//		mainMenu.process();
		
//		// ���Ʊ���ͼƬ
//		if(alphaValue <= 0){
//			alphaValue = 0;
//		}else{
//			startImage = getTransparent(startImage, alphaValue);				
//			alphaValue --;
//		}
//		gra.drawImage(startImage, 0, 0, Graphics.TOP|Graphics.LEFT);
//		
//		switch(state){
//		case GAME_STATE_INTRO:
//			// ���ƽ���������
//			
//			// �����������ֵ�λ�������ƶ�2������ 
//			introPosY = (introPosY - 2 <= 0) ? 0 : introPosY - 2;				
//			
//			if(introPosY <= 0){
//				// ���ֽ��ܻ�����ɣ�ͣ��100����
//				try{
//					Thread.sleep(100);
//				}catch(Exception e){}
//				state = GAME_STATE_SWITCH;				
//			}else{
//				try{
//					gra.drawImage(introImage, 0, introPosY, Graphics.TOP|Graphics.LEFT);
//				}catch(Exception e){}
//			}
//			break;
//		case GAME_STATE_SWITCH:
//			break;
//		case GAME_STATE_MENU:
//			// ���Ʋ˵�������ü��̲�������
//			break;
//		}
	}
	
	/**
	 * ��Ϸ���߳�ѭ��
	 */
	public void run() {
		while(RUN){
//			process();
//			mainMenu.process();
			this.flushGraphics();
			try{
				Thread.sleep(sleepTime);
			}catch(Exception e){}
		}		
	}

	/**
	 * ����ͼ��img��ָ��͸�����µĸ���
	 * 
	 * @param img ԭʼͼƬ            
	 * @param value ͸����,ȡֵ��Χ��0x00~0xff(0~255)֮�䣬͸����Խ�ߣ�ͼ����ʾԽ������
	 * @return ����͸�������ͼƬ
	 */
	private static Image getTransparent(Image img, int alphaValue) {
		int width = img.getWidth();
		int height = img.getHeight();

		int[] imgData = new int[width * height];

		img.getRGB(imgData, 0, width, 0, 0, width, height);
		
		int length = imgData.length;
		
		for (int i = 0; i < length; i++) {
			imgData[i] = imgData[i] & 0x00ffffff | ((~alphaValue) << 24);
		}

		return Image.createRGBImage(imgData, width, height, true);
	}
}