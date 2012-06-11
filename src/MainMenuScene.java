import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;

/**
 * 游戏主菜单，玩家在不同界面切换的窗口
 * 
 * @author guangya
 * @email  guangyait@gmail.com
 *
 */
public class MainMenuScene extends Scene{
	
	// 功能选择菜单使用的图片及其尺寸
	private int btnWidth;
	private int btnHeight;
	// 菜单标题文字
	private Image selectionImage;
	// 当前选中的菜单想的索引
	private int selectedIndex;      
	// 菜单项总数常量，使用的目的是减少获取菜单项数组长度的次数
	private final int MENU_ITEM_NUM = 5;  	
	// 菜单项
	private Menu menuItems[];
	
	public MainMenuScene(IEngine engine, int width, int height){
		super(engine, width, height);

		try{
			selectionImage = Image.createImage("/image/selection.png");
		}catch(Exception e){}
		
		// 菜单项的说明文字
		String itemNames[] = {"开 始 游 戏 ", "开 启 音 效", "操 作 说 明", "关          于", "退 出 游 戏"};
		// 菜单项对应的游戏状态，与itemNames[]对应
		int    itemToGameState[] = {IEngine.GAME_STATE_RUN, -1, IEngine.GAME_STATE_OPERATION, IEngine.GAME_STATE_ABOUT, IEngine.GAME_STATE_EXIT};
		Image menuItemBg;
		Image menuItemSBg;
		Image btnImage;
		
		menuItems = new Menu[MENU_ITEM_NUM];
		
		try{
			btnImage = Image.createImage("/image/buttons.png");
			
			btnWidth = btnImage.getWidth();
			btnHeight = btnImage.getHeight() / 2;
			
			menuItemBg = Image.createImage(btnImage, 0, 0, btnWidth, btnHeight, Sprite.TRANS_NONE);
			menuItemSBg = Image.createImage(btnImage, 0, btnHeight, btnWidth, btnHeight, Sprite.TRANS_NONE);
			
			for(int i = 0; i < MENU_ITEM_NUM; i++){
				menuItems[i] = new Menu(itemNames[i]);
				menuItems[i].setBoundEvent(itemToGameState[i]);
				menuItems[i].setBackground(menuItemBg);
				menuItems[i].setSelectedBackground(menuItemSBg);				
			}
			calculateMenuItemsPos();
			menuItems[0].setState(Menu.MENU_ITEM_STATE_SELECTED);
		}catch(Exception e){}
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
		calculateMenuItemsPos();
	}
	
	/**
	 * 按键消息处理
	 * 
	 * @param gameAction
	 */
	public void keyEvent(int gameAction){
		switch(gameAction){
		case GameCanvas.UP:
			menuItems[selectedIndex].setState(Menu.MENU_ITEM_STATE_NORMAL);
			selectedIndex--;
			if(selectedIndex < 0){
				selectedIndex += MENU_ITEM_NUM;
			}
			menuItems[selectedIndex].setState(Menu.MENU_ITEM_STATE_SELECTED);
			break;
		case GameCanvas.DOWN:
			menuItems[selectedIndex].setState(Menu.MENU_ITEM_STATE_NORMAL);
			selectedIndex++;
			selectedIndex %= MENU_ITEM_NUM;
			menuItems[selectedIndex].setState(Menu.MENU_ITEM_STATE_SELECTED);
			break;
		case GameCanvas.FIRE:
			if(1 == selectedIndex){
				if(menuItems[selectedIndex].getText() == "开 启 音 效"){
					menuItems[selectedIndex].setText("关 闭 音 效");
					engine.setSoundSwitch(false);
				}else{
					menuItems[selectedIndex].setText("开 启 音 效");
					engine.setSoundSwitch(true);
				}
			}else{
				engine.setGameState(menuItems[selectedIndex].getBoundEvent());
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
		gra.setColor(0, 0, 0);
		gra.drawImage(selectionImage, 64, 40, Graphics.LEFT|Graphics.TOP);
		
		for(int i = 0; i < MENU_ITEM_NUM; i++){
			menuItems[i].paint(gra);
		}
	}
	
	/**
	 * 未添加左右菜单，不处理左右菜单键消息
	 */
	public void menuEvent(int menu) {}
	
	/**
	 * 计算各菜单项在菜单界面的坐标
	 */
	private void calculateMenuItemsPos(){
		int menuCenterPosX = posX + canvasWidth / 2;
		for(int i = 0; i < MENU_ITEM_NUM; i++){
			menuItems[i].setPosition(menuCenterPosX, posY + 110 + i*(btnHeight + 5));
		}
	}
}