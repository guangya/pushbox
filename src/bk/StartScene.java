package bk;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;


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

public class StartScene extends Scene {
	// 开始菜单的背景图像
	private Image startImage;
	// 菜单是否进入可选择状态 
	private boolean isSelectable = false;
	// 游戏的文字性介绍
	private Image introImage;
	// 游戏文字性介绍滚动绘制的当前Y轴坐标
	private int introPosY;
	
	private boolean hideIntro;
	
	private boolean finishPaint = false;
	private int alphaValue = 255;
	
	private static final int STATE_INTRO  = 1;
	private static final int STATE_SWITCH = 2;
	private static final int STATE_MENU   = 4;
	
	private int state;
	
	/**
	 * 初始化开始关卡选择菜单
	 * 
	 * @return void
	 */
	public void initialize(){
		
		sleepTime = 50;
		try{
			startImage = Image.createImage("/image/start_bg.png");
			introImage = Image.createImage("/image/gameIntro.png");
			
		}catch(Exception e){}
		
		this.setFullScreenMode(true);
		
		state = STATE_INTRO;
		introPosY = this.getHeight();
		hideIntro = false;
		
	}
	
	/**
	 * 处理场景逻辑
	 * 
	 * @return void
	 */
	public void process(Graphics gra){
		
		// 绘制背景图片
		if(alphaValue <= 0){
			alphaValue = 0;
		}else{
			startImage = getTransparent(startImage, alphaValue);				
			alphaValue --;
		}
		gra.drawImage(startImage, 0, 0, Graphics.TOP|Graphics.LEFT);
		
		switch(state){
		case STATE_INTRO:
			// 绘制介绍性文字
			
			// 将介绍性文字的位置向上移动2个像素 
			introPosY = (introPosY - 2 <= 0) ? 0 : introPosY - 2;				
			
			if(introPosY <= 0){
				// 文字介绍绘制完成，停顿100毫秒
				try{
					Thread.sleep(100);
				}catch(Exception e){}
				
				hideIntro = true;
			}else{
				try{
					gra.drawImage(introImage, 0, introPosY, Graphics.TOP|Graphics.LEFT);
				}catch(Exception e){}
			}
			break;
		case STATE_SWITCH:
			break;
		case STATE_MENU:
			// 绘制菜单项，并启用键盘操作控制
			break;
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