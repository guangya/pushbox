package bk;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;


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

public class StartScene extends Scene {
	// ��ʼ�˵��ı���ͼ��
	private Image startImage;
	// �˵��Ƿ�����ѡ��״̬ 
	private boolean isSelectable = false;
	// ��Ϸ�������Խ���
	private Image introImage;
	// ��Ϸ�����Խ��ܹ������Ƶĵ�ǰY������
	private int introPosY;
	
	private boolean hideIntro;
	
	private boolean finishPaint = false;
	private int alphaValue = 255;
	
	private static final int STATE_INTRO  = 1;
	private static final int STATE_SWITCH = 2;
	private static final int STATE_MENU   = 4;
	
	private int state;
	
	/**
	 * ��ʼ����ʼ�ؿ�ѡ��˵�
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
	 * �������߼�
	 * 
	 * @return void
	 */
	public void process(Graphics gra){
		
		// ���Ʊ���ͼƬ
		if(alphaValue <= 0){
			alphaValue = 0;
		}else{
			startImage = getTransparent(startImage, alphaValue);				
			alphaValue --;
		}
		gra.drawImage(startImage, 0, 0, Graphics.TOP|Graphics.LEFT);
		
		switch(state){
		case STATE_INTRO:
			// ���ƽ���������
			
			// �����������ֵ�λ�������ƶ�2������ 
			introPosY = (introPosY - 2 <= 0) ? 0 : introPosY - 2;				
			
			if(introPosY <= 0){
				// ���ֽ��ܻ�����ɣ�ͣ��100����
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
			// ���Ʋ˵�������ü��̲�������
			break;
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