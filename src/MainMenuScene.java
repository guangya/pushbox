import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;

public class MainMenuScene{
	
	private int width;
	private int height;
	private int posX;             //�������Ͻ��������Ļ���Ͻǵ�X����
	private int posY;             //�������Ͻ��������Ļ���Ͻǵ�Y����
	private Image btnImage;
	private Image selectionImage;
	private Engine engine;
	private int items[][][];      // items[�˵�������][0:�˵�ͼƬ�����꣬1:�˵��ı�������][0:x���ꣻ1:y����]
	private int selectedIndex;
	private final int MENU_ITEM_NUM = 5;
	private int btnWidth;
	private int btnHeight;
	private String itemNames[] = {"�� ʼ �� Ϸ", "�� �� �� Ч", "�� �� ˵ ��", "��           ��", "�� �� �� Ϸ"};
	private int    itemToGameState[] = {Engine.GAME_STATE_RUN, -1, Engine.GAME_STATE_OPERATION, Engine.GAME_STATE_ABOUT, Engine.GAME_STATE_EXIT};
	
	
	public MainMenuScene(Engine engine, int width, int height){
		this.width = width;
		this.height = height;
		this.engine = engine;
		posX = 0;
		posY = 0;
		try{
			btnImage = Image.createImage("/image/buttons.png");
			selectionImage = Image.createImage("/image/selection.png");
		}catch(Exception e){}
		
		btnWidth = btnImage.getWidth();
		btnHeight = btnImage.getHeight() / 2;
		initMenuItems();
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
		// ���¼����Ԫ�ص�����
		initMenuItems();
	}
	
	/**
	 * ������Ϣ����
	 * 
	 * @param gameAction
	 */
	public void keyEvent(int gameAction){
		switch(gameAction){
		case GameCanvas.UP:
			selectedIndex--;
			if(selectedIndex < 0){
				selectedIndex += MENU_ITEM_NUM;
			}
			break;
		case GameCanvas.DOWN:
			selectedIndex++;
			selectedIndex %= MENU_ITEM_NUM;
			break;
		case GameCanvas.FIRE:
			if(1 == selectedIndex){
				if(itemNames[1] == "�� �� �� Ч"){
					itemNames[1] = "�� �� �� Ч";
					engine.setSoundSwitch(false);
				}else{
					itemNames[1] = "�� �� �� Ч";
					engine.setSoundSwitch(true);
				}
			}else{
				engine.setGameState(itemToGameState[selectedIndex]);
			}
			break;
		}
	}
	
	/**
	 * �����߼�����
	 * 
	 * @param gra
	 */
	public void process(Graphics gra){
		
		gra.drawImage(selectionImage, 64, 40, Graphics.LEFT|Graphics.TOP);
		
		for(int i = 0; i < MENU_ITEM_NUM; i++){
			if(selectedIndex == i){
				gra.drawRegion(btnImage, 0, btnHeight, btnWidth, btnHeight, Sprite.TRANS_NONE, items[i][0][0], items[i][0][1], Graphics.LEFT|Graphics.TOP);
			}else{
				gra.drawRegion(btnImage, 0, 0, btnWidth, btnHeight, Sprite.TRANS_NONE, items[i][0][0], items[i][0][1], Graphics.LEFT|Graphics.TOP);
			}
			gra.drawString(itemNames[i], items[i][1][0], items[i][1][1], Graphics.LEFT|Graphics.TOP);
		}
	}
	
	/**
	 * ��ʼ���˵���
	 * 
	 * �ú��������ظ���ʼ���˵���
	 * 
	 * @return void
	 */
	private void initMenuItems(){
		items = new int[MENU_ITEM_NUM][2][2];
		for(int i = 0; i < MENU_ITEM_NUM; i++){
			items[i][0][0] = posX + (width - btnWidth) / 2;
			items[i][0][1]= posY + 110 + i * (btnHeight + 5) - btnHeight / 2;
			items[i][1][0] = items[i][0][0] + 17;
			items[i][1][1]= items[i][0][1] + 2;
		}
		
		selectedIndex = 0;
	}
}