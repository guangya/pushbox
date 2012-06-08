package bk;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.midlet.MIDlet;


/**
 * 文字（推箱子游戏简介）在屏幕中央淡入，完全显示后，停顿5秒钟后，然后文字向右滑动直至消失，左侧随之出现主菜单，
 * 整个过程类似于智能机主菜单中的切换效果（从慢到快的切换，完成切换后立即停止）
 * 
 * 1、背景淡入
 * 2、文字从下方运动到屏幕中央
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
	
	// 前一个、当前和下一个界面使用的画笔
	private Graphics preGra;
	private Graphics curGra;
	private Graphics nextGra;
	
	// 主菜单
	private Menu mainMenu;
	
	// 画布参数
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
	 * 处理场景逻辑
	 * 
	 * @return void
	 */
	public void process(){
//		mainMenu.process();
		
//		// 绘制背景图片
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
//			// 绘制介绍性文字
//			
//			// 将介绍性文字的位置向上移动2个像素 
//			introPosY = (introPosY - 2 <= 0) ? 0 : introPosY - 2;				
//			
//			if(introPosY <= 0){
//				// 文字介绍绘制完成，停顿100毫秒
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
//			// 绘制菜单项，并启用键盘操作控制
//			break;
//		}
	}
	
	/**
	 * 游戏主线程循环
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
	 * 创建图像img在指定透明度下的副本
	 * 
	 * @param img 原始图片            
	 * @param value 透明度,取值范围在0x00~0xff(0~255)之间，透明度越高，图像显示越不清晰
	 * @return 返回透明化后的图片
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