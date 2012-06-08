import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;



public class IntroScene{
	
	private int width;
	private int height;
	private int posX;             //��Ϸ��鳡�����Ͻ��������Ļ���Ͻǵ�X����
	private int posY;             //��Ϸ��鳡�����Ͻ��������Ļ���Ͻǵ�Y����
	private Image introImage;
	private Engine engine;
	
	public IntroScene(Engine engine, int width, int height){
		this.width = width;
		this.height = height;
		this.engine = engine;
		posX = 0;
		posY = height;
		try{
			introImage = Image.createImage("/image/gameIntro.png");
		}catch(Exception e){}
	}
	
	/**
	 * ���ò˵���������Ͻ��������Ļ������
	 * 
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y){
		this.posX = x;
		this.posY = y;
	}
	
	/**
	 * ������Ϣ����
	 * 
	 * @param gameAction
	 */
	public void keyEvent(int gameAction){}
	
	/**
	 * �����߼�����
	 * 
	 * @param gra
	 */
	public void process(Graphics gra){
		gra.drawImage(introImage, posX, posY, Graphics.TOP|Graphics.LEFT);
		
		if(0 == posY){
			// ͣ��һ��ʱ��
			try{
				Thread.sleep(1500);
			}catch(Exception e){}
			
			// ������Ϸ���˵�����
			engine.setGameState(Engine.GAME_STATE_MENU);
		}else{
			// ȷ�������겻С��0
			posY = (posY - 2 > 0) ? posY - 2 : 0;
		}
	}
}