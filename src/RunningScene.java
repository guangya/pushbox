import javax.microedition.lcdui.Graphics;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.Vector;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

/**
 * 
 * 背景层（使用系统背景或自定义背景）
 * 墙壁层（无法穿行）
 * 目标层（标识箱子的目标位置）
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
		
		// 加载场景用到的资源
		try{
			mapImage = Image.createImage("/image/map&player&box.png");
			playerImage = Image.createImage(mapImage, 0, 32, 48, 48, 0);
			playerImageFront = Image.createImage(mapImage, 0, 32, 48, 16, 0);
			boxImage = Image.createImage(mapImage, 16, 16, 32, 16, 0);
			inlineMenuImage = Image.createImage("/image/inlineMenus.png");
		}catch(Exception e){}

		// 计算菜单项尺寸
		menuItemWidth = inlineMenuImage.getWidth();
		menuItemHeight = inlineMenuImage.getHeight() / 4;
		
		// 初始化玩家
		player = new Sprite(playerImageFront, 16, 16);	
		
		// 初始化游戏地图
		level = engine.getGameLevel();
		initMap(level);
	}

	protected void process(Graphics gra) {
		// 绘制地图
		backMap.paint(gra);
		wallMap.paint(gra);
		destMap.paint(gra);
		
		// 绘制玩家
		player.paint(gra);
		player.nextFrame();
		
		// 绘制箱子
		int boxNum = aBoxes.length;
		for(int i = 0; i < boxNum; ++i){
			aBoxes[i].paint(gra);
		}
		
		// 绘制关卡
		gra.drawRegion(inlineMenuImage, 0, 39, 55, 13, Sprite.TRANS_NONE, 0, 3, Graphics.LEFT|Graphics.TOP);
		gra.setColor(255, 255, 255);
		gra.drawString("" + level, 22, 0, Graphics.LEFT|Graphics.TOP);
		
		// 绘制操作步数统计
		gra.drawRegion(inlineMenuImage, 0, 26, 55, 13, Sprite.TRANS_NONE, canvasWidth - menuItemWidth, 3, Graphics.LEFT|Graphics.TOP);
		gra.drawString("" + count, canvasWidth - menuItemWidth - 10, 0, Graphics.LEFT|Graphics.TOP);
		
		// 绘制菜单
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
		
		// 如果已将所有的箱子放置到目标位置，则进入下一关
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
	 * 初始化游戏场景
	 * 
	 * @param level 当前关卡
	 */
	public void initMap(int level){
		count = 0;
		aBoxes = null;
		// 初始化各地图对象
		backMap = new TiledLayer(15, 18, mapImage, 16, 16);
		wallMap = new TiledLayer(15, 18, mapImage, 16, 16);
		destMap = new TiledLayer(15, 18, mapImage, 16, 16);	
		
		// 临时变量，用来存储箱子对象
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
						// 地图中出现箱子，则新建一个箱子对象，并存储在vBoxes中 
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
		
		// 箱子初始化完毕，将箱子对象集转存到数组中
		boxNum = vBoxes.size();
		aBoxes = new Sprite[boxNum];
		vBoxes.copyInto(aBoxes);
		vBoxes.removeAllElements();
	}

	/**
	 * 测试碰撞
	 * 
	 * @param int dx 在x方向要移动的像素数
	 * @param int dy 在y方向要移动的像素数
	 * @return bool
	 */
	private boolean testCollision(int dx, int dy){
		player.move(dx, dy);
		
		// 与地图碰撞
		if(player.collidesWith(wallMap, false)){
			return true;
		}
		
		// 玩家与箱子的碰撞以及箱子与箱子、箱子与墙壁的碰撞
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
		
		// 玩家操作有效
		return false;
	}	
	
	/**
	 * 检查当前的箱子是否和其它的箱子发生了碰撞
	 * 
	 * @param curBoxIndex 当前箱子在箱子数组aBoxes中的索引
	 * @param pixelLevel  是否进行像素级别的碰撞检测
	 * @return boolean 如果存在碰撞，返回true，否则，返回false
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
	 * 检查是否所有箱子均被推送到目的地
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