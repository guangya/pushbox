//package bk;
//
//import javax.microedition.lcdui.Font;
//import javax.microedition.lcdui.Graphics;
//import javax.microedition.lcdui.Image;
//import javax.microedition.lcdui.game.GameCanvas;
//import tools.Tools;
//
//
//class MainMenu extends GameCanvas{
//
//	private boolean RUN;
//	private int sleepTime;
//	private MainMidlet engine;
//	private int canvasWidth;
//	private int canvasHeight;
//	private Graphics gra;
//	
//	// 场景当前所处的状态
//	private static final int MENU_STATE_START   = 0;
//	private static final int MENU_STATE_INTRO   = 1;
//	private static final int MENU_STATE_MENU    = 2;
//	private static final int MENU_STATE_SWITCH  = 3;
//	private int menuState;	
//	
//	private Image bgImage;
//	private int alphaValue;
//	
//	private Image introImage;
//	private int introPosY;
//	private int introPosX;
//	
//	private MenuItem menuItems[];
//	private int selectedMenuItemIndex;
//	private String itemNames[] = {"开始游戏", "声音设置", "操作说明", "关        于", "退出游戏"};
//	private int    itemToGameState[] = {MainMidlet.GAME_STATE_RUN, MainMidlet.GAME_STATE_SOUND, MainMidlet.GAME_STATE_INTRO, MainMidlet.GAME_STATE_ABOUT, MainMidlet.GAME_STATE_EXIT};
//	private final int MENU_ITEM_NUM = 5;
//	private Image btnImage;
//	private Image selectedBtnImage;
//	private int menuPosX;            
//	private int menuPosY;
//	
//	private int switchRate;
//	
//	public MainMenu(MainMidlet engine) {
//		super(false);
//		this.setFullScreenMode(true);
//		RUN = true;
//		sleepTime = 25;
//		this.engine = engine;
//		canvasWidth = this.getWidth();
//		canvasHeight = this.getHeight();		
//		gra = this.getGraphics();
//		menuState = MENU_STATE_START;
//		
//		try{
//			bgImage = Image.createImage("/image/background.png");
//			introImage = Image.createImage("/image/gameIntro.png");
//			btnImage = Image.createImage("/image/button.png");
//			selectedBtnImage = Image.createImage("/image/button-selected.png");
//		}catch(Exception e){}
//		
//		alphaValue = 255;
//		bgImage = Tools.getTransparent(bgImage, alphaValue);
//		
//		introPosY = canvasHeight;
//		introPosX = 100;
//		menuPosX = -canvasWidth;
//		menuPosY = 0;
//		switchRate = 0;
//		
//		initMenuItems();
//	}
//		
//	private void process(){
//		// 绘制背景图片
//		gra.drawImage(bgImage, 0, 0, Graphics.TOP|Graphics.LEFT);
//		
//		switch(menuState){
//		case MENU_STATE_START:
//			if(alphaValue >= 220){
//				bgImage = Tools.getTransparent(bgImage, alphaValue);				
//				alphaValue--;
//			}else{
//				bgImage = Tools.getTransparent(bgImage, 0);
//				menuState = MENU_STATE_INTRO;
//			}
//			break;
//		case MENU_STATE_INTRO:
//			showIntro();
//			break;
//		case MENU_STATE_MENU:
//			showMenu();
//			break;
//		case MENU_STATE_SWITCH:
//			if(introPosX >= canvasWidth){
//				if(menuPosX != 0){
//					menuPosX = 0;
//				}else{
//					try{
//						Thread.sleep(1500);
//					}catch(Exception e){}
//					menuState = MENU_STATE_MENU;
//				}
//			}else{
//				introPosX = switchRate * switchRate / 2;
//				switchRate++;
//				menuPosX = introPosX - canvasWidth;
//			}
//			
//			gra.drawImage(introImage, introPosX, 0, Graphics.TOP|Graphics.LEFT);			
//			showMenu();
//			break;
//		}
//	}
//	
//	protected void keyReleased(int keyCode){
//		if(menuState != MENU_STATE_MENU){
//			return;
//		}
//		
//		switch(getGameAction(keyCode)){
//		case UP:
//			changeSelectedItem(-1);
//			break;
//		case DOWN:
//			changeSelectedItem(1);
//			break;
//		case FIRE:
//			engine.setGameState(itemToGameState[selectedMenuItemIndex]);
//			RUN = false;
//			break;
//		}
//	}
//	
//	/**
//	 * 更改选中项
//	 * 
//	 * @param dis 新的 选中项相对于当前选中项的索引
//	 */
//	private void changeSelectedItem(int dis){
//		// 将当前选中项设置为未选中状态
//		menuItems[selectedMenuItemIndex].setSelected(false);
//		
//		// 计算新的选中项的索引
//		selectedMenuItemIndex += dis;
//		if(selectedMenuItemIndex < 0){
//			selectedMenuItemIndex += MENU_ITEM_NUM;
//		}
//		selectedMenuItemIndex %= MENU_ITEM_NUM;
//		
//		// 设置新的选中项
//		menuItems[selectedMenuItemIndex].setSelected(true);
//	}
//	
//	private void showMenu(){
//		for(int i = 0; i < menuItems.length; i++){
//			menuItems[i].draw(gra, menuPosX + canvasWidth / 2, menuPosY + 110 + i * 20);
//		}
//	}
//	
//	/**
//	 * 初始化菜单项
//	 * 
//	 * 该函数不会重复初始化菜单项
//	 * 
//	 * @return void
//	 */
//	private void initMenuItems(){
//		// 避免重复初始化菜单项
//		if(null != menuItems){
//			return;
//		}
//		
//		// 设置菜单项名称使用的字体
//		Font font = Font.getFont(Font.FACE_SYSTEM,Font.STYLE_PLAIN,Font.SIZE_MEDIUM);
//		gra.setFont(font);
//		
//		// 初始化菜单项
//		menuItems = new MenuItem[MENU_ITEM_NUM];
//		for(int i = 0; i < MENU_ITEM_NUM; i++){			
//			menuItems[i] = new MenuItem(itemNames[i], btnImage, selectedBtnImage);
////			menuItems[i].setBoundEvent(itemToGameState[i]);
//		}
//		
//		// 将当前选中的菜单项索引设置为0
//		selectedMenuItemIndex = 0;
//		menuItems[selectedMenuItemIndex].setSelected(true);
//	}
//	
//	private void showIntro(){
//		// 将介绍性文字的位置向上移动2个像素 
//		introPosY = (introPosY - 2 <= 0) ? 0 : introPosY - 2;				
//		
//		if(introPosY <= 0){
//			// 文字介绍绘制完成，停顿1000毫秒
//			try{
//				Thread.sleep(1500);
//			}catch(Exception e){}
//			menuState = MENU_STATE_SWITCH;
//		}else{
//			gra.drawImage(introImage, 0, introPosY, Graphics.TOP|Graphics.LEFT);
//		}
//	}
//	
//	public void show(){
//		while(RUN){
//			process();
//			this.flushGraphics();
//			try{
//				Thread.sleep(sleepTime);
//			}catch(Exception e){}
//		}
//	}
//}