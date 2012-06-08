import javax.microedition.lcdui.Graphics;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.Vector;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

/**
 * 
 * �����㣨ʹ��ϵͳ�������Զ��屳����
 * ǽ�ڲ㣨�޷����У�
 * Ŀ��㣨��ʶ���ӵ�Ŀ��λ�ã�
 * 
 * 
 * @author Guangya
 *
 */


public class RunningScene {
	
	private Engine engine;
	private int canvasWidth;
	private int canvasHeight;	
	private Image  mapImage, playerImage, boxImage;
	private Sprite player, aBoxes[];
	private int boxNum;
	private int count;
	private TiledLayer backMap, wallMap, destMap;
	private Image inlineMenuImage;
	private int menuItemWidth;
	private int menuItemHeight;
	private int level;
	private Image playerImageFront;
	private boolean isWin;
	private boolean isWait;
	
	public RunningScene(Engine engine, int width, int height){
		this.engine = engine;
		this.canvasWidth = width;
		this.canvasHeight = height;
		initialize();
	}

	protected void initialize() {
		isWin = false;
		isWait = true;
		
		// ���س����õ�����Դ
		try{
			mapImage = Image.createImage("/image/map&player&box.png");
			playerImage = Image.createImage(mapImage, 0, 32, 48, 48, 0);
			playerImageFront = Image.createImage(mapImage, 0, 32, 48, 16, 0);
			boxImage = Image.createImage(mapImage, 16, 16, 32, 16, 0);
			inlineMenuImage = Image.createImage("/image/inlineMenus.png");
		}catch(Exception e){}

		// ����˵���ߴ�
		menuItemWidth = inlineMenuImage.getWidth();
		menuItemHeight = inlineMenuImage.getHeight() / 4;
		
		// ��ʼ�����
		player = new Sprite(playerImageFront, 16, 16);	
		
		// ��ʼ����Ϸ��ͼ
		level = engine.getGameLevel();
		initMap(level);
	}

	protected void process(Graphics gra) {
		// ���Ƶ�ͼ
		backMap.paint(gra);
		wallMap.paint(gra);
		destMap.paint(gra);
		
		// �������
		player.paint(gra);
		player.nextFrame();
		
		// ��������
		int boxNum = aBoxes.length;
		for(int i = 0; i < boxNum; ++i){
			aBoxes[i].paint(gra);
		}
		
		// ���ƹؿ�
		gra.drawRegion(inlineMenuImage, 0, 39, 55, 13, Sprite.TRANS_NONE, 0, 3, Graphics.LEFT|Graphics.TOP);
		gra.setColor(255, 255, 255);
		gra.drawString("" + level, 22, 0, Graphics.LEFT|Graphics.TOP);
		
		// ���Ʋ�������ͳ��
		gra.drawRegion(inlineMenuImage, 0, 26, 55, 13, Sprite.TRANS_NONE, canvasWidth - menuItemWidth, 3, Graphics.LEFT|Graphics.TOP);
		gra.drawString("" + count, canvasWidth - menuItemWidth - 10, 0, Graphics.LEFT|Graphics.TOP);
		
		// ���Ʋ˵�
		gra.drawRegion(inlineMenuImage, 0, 0, 55, 13, Sprite.TRANS_NONE, canvasWidth - menuItemWidth, canvasHeight - menuItemHeight, Graphics.LEFT|Graphics.TOP);
		gra.drawRegion(inlineMenuImage, 0, 13, 55, 13, Sprite.TRANS_NONE, 0, canvasHeight - menuItemHeight, Graphics.LEFT|Graphics.TOP);
		
		if(isWin){
			if(isWait){
				isWait = false;
			}else{
				try{
					Thread.sleep(1500);
				}catch(Exception e){}
				isWin = false;
				isWait = true;
				level = level + 1;
				engine.setGameLevel(level);
				initMap(level);
			}
		}
	}
	
	public void keyEvent(int gameAction){
		switch(gameAction){
		case Engine.LEFT:
			moveSprite(-16, 0);
			break;		
		case Engine.RIGHT:
			moveSprite(16, 0);
			break;									
		case Engine.UP:
			moveSprite(0, -16);
			break;
		case Engine.DOWN:
			moveSprite(0, 16);
			break;
		}
		
		// ����ѽ����е����ӷ��õ�Ŀ��λ�ã��������һ��
		if(isAllToDest()){
			isWin = true;
		}
	}
	
	private void moveSprite(int dx, int dy){
		if(testCollision(dx, dy)){
			player.move(-dx, -dy);
		}else{
			++count;
		}
	}
	
	/**
	 * ��ʼ����Ϸ����
	 * 
	 * @param level ��ǰ�ؿ�
	 */
	public void initMap(int level){
		count = 0;
		aBoxes = null;
		// ��ʼ������ͼ����
		backMap = new TiledLayer(15, 18, mapImage, 16, 16);
		wallMap = new TiledLayer(15, 18, mapImage, 16, 16);
		destMap = new TiledLayer(15, 18, mapImage, 16, 16);	
		
		// ��ʱ�����������洢���Ӷ���
		Vector vBoxes = new Vector();
		
		try{
			InputStream is = this.getClass().getResourceAsStream("/map/pushbox_map_" + level + ".MAR");
			DataInputStream dis = new DataInputStream(is);
			for(int i = 0; i < 18; ++i){				
				for(int j = 0; j < 15; ++j){
					short a = dis.readShort();
					int b = 0x07 & a >> 13;
					switch(b){
					case 1://back
						backMap.setCell(j, i, 1);
						break;
					case 2://wall
						wallMap.setCell(j, i, 2);
						break;
					case 3://destination
						backMap.setCell(j, i, 1);
						destMap.setCell(j, i, 3);
						break;
					case 4://player
						backMap.setCell(j, i, 1);
						player.setPosition(16*j, 16*i);
						break;
					case 5://box
						backMap.setCell(j, i, 1);
						// ��ͼ�г������ӣ����½�һ�����Ӷ��󣬲��洢��vBoxes�� 
						Sprite newBox = new Sprite(boxImage, 16, 16);
						newBox.setPosition(16*j, 16*i);
						vBoxes.addElement(newBox);
						break;
					default:
						backMap.setCell(j, i, b);
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
		boolean isAll = true;
		for(int i = 0; i < boxNum; ++i){
			if(!aBoxes[i].collidesWith(destMap, false)){
				isAll = false;
			}else if(aBoxes[i].getFrame() == 0){
					aBoxes[i].setFrame(1);
			}
		}
		return isAll;
	}
}