import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

public class AboutScene{
	
	private int width;
	private int height;
	private int posX;             //�������Ͻ��������Ļ���Ͻǵ�X����
	private int posY;             //�������Ͻ��������Ļ���Ͻǵ�Y����
	private Engine engine;
	private Image aboutImage;
	private Image menusImage;
	
	
	public AboutScene(Engine engine, int width, int height){
		this.width = width;
		this.height = height;
		this.engine = engine;
		posX = 0;
		posY = 0;
		try{
			aboutImage = Image.createImage("/image/gameAbout.png");
			menusImage = Image.createImage("/image/menus.png");
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
	public void keyEvent(int gameAction){
		switch(gameAction){
		case GameCanvas.UP:
			break;
		case GameCanvas.DOWN:
			break;
		case GameCanvas.FIRE:
			break;
		}
	}
	
	/**
	 * �����߼�����
	 * 
	 * @param gra
	 */
	public void process(Graphics gra){
		
//		gra.drawImage(selectionImage, 64, 40, Graphics.LEFT|Graphics.TOP);
	}
}