import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;



public class OperationScene{
	
	private int width;
	private int height;
	private int posX;             //��Ϸ��鳡�����Ͻ��������Ļ���Ͻǵ�X����
	private int posY;             //��Ϸ��鳡�����Ͻ��������Ļ���Ͻǵ�Y����
	private Engine engine;
	
	private Image operationImage;
	private Image menusImage;
	
	public OperationScene(Engine engine, int width, int height){
		this.width = width;
		this.height = height;
		this.engine = engine;
		posX = 0;
		posY = 0;
		initialize();
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
	
	protected void initialize(){
		try{
			operationImage = Image.createImage("/image/gameOperation.png");
			menusImage = Image.createImage("/image/menus.png");
		}catch(Exception e){}
	}
	
	/**
	 * ������Ϣ����
	 * 
	 * @param gameAction
	 */
	public void keyEvent(int gameAction){
		switch(gameAction){
		case GameCanvas.RIGHT:
			// ������Ϸ���˵�
			engine.setGameState(Engine.GAME_STATE_MENU);
			break;
		default:
			break;
		}
	}
	
	/**
	 * �����߼�����
	 * 
	 * @param gra
	 */
	public void process(Graphics gra){
		gra.drawImage(operationImage, posX, posY, Graphics.LEFT|Graphics.TOP);
          //drawRegion(Image src, int x_src, int y_src, int width, int height, int transform, int x_dest, int y_dest, int anchor) 
		gra.drawRegion(menusImage, 0, 0, 67, 15, Sprite.TRANS_NONE, 0, 0, Graphics.LEFT|Graphics.TOP);
	}
}