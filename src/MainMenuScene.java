import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;

public class MainMenuScene{
	
	// 场景尺寸
	private int width;
	private int height;
	// 场景左上角相对于屏幕左上角的坐标
	private int posX;
	private int posY;
	// 功能选择菜单使用的图片及其尺寸
	private Image btnImage;
	private int btnWidth;
	private int btnHeight;
	// 菜单标题文字
	private Image selectionImage;
	// 游戏画布
	private Engine engine;
	// 存储各菜单项使用的图片绘制的坐标。items[菜单项索引][0:菜单图片的坐标，1:菜单文本的坐标][0:x坐标；1:y坐标]
	private int items[][][];
	// 当前选中的菜单想的索引
	private int selectedIndex;      
	// 菜单项总数常量，使用的目的是减少获取菜单项数组长度的次数
	private final int MENU_ITEM_NUM = 5;  
	// 菜单项的说明文字
	private String itemNames[] = {"开 始 游 戏 ", "开 启 音 效", "操 作 说 明", "关          于", "退 出 游 戏"};
	// 菜单项对应的游戏状态，与itemNames[]对应
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
	 * 设置菜单界面的左上角相对于屏幕的坐标
	 * 
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y){
		this.posX = x;
		this.posY = y;
		// 重新计算各元素的坐标
		initMenuItems();
	}
	
	/**
	 * 按键消息处理
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
				if(itemNames[1] == "开 启 音 效"){
					itemNames[1] = "关 闭 音 效";
					engine.setSoundSwitch(false);
				}else{
					itemNames[1] = "开 启 音 效";
					engine.setSoundSwitch(true);
				}
			}else{
				engine.setGameState(itemToGameState[selectedIndex]);
			}
			break;
		}
	}
	
	/**
	 * 场景逻辑处理
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
	 * 初始化菜单项
	 * 
	 * 该函数不会重复初始化菜单项
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