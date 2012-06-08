package bk;

import java.io.DataInputStream;
import java.io.InputStream;
import java.util.Vector;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

import bk.GameEngine;

public class RunScene extends Scene implements CommandListener{
	
	private Image  mapImage, playerImage, boxImage;
	private Sprite player, aBoxes[];
	private int boxNum;
	private int count;
	private TiledLayer backMap, wallMap, destMap;
	
	// 游戏运行界面的菜单项
	private Command cmdRestart;
	private Command cmdResume;
	private Command cmdExit;
	private Command cmdCancel;

	protected void initialize() {
		// 加载场景用到的资源
		try{
			mapImage = Image.createImage("/image/pushbox.png");	
			playerImage = Image.createImage(mapImage, 96, 0, 32, 16, 0);
			boxImage = Image.createImage(mapImage, 48, 0, 16, 16, 0);
		}catch(Exception e){}		

		// 初始化玩家
		player = new Sprite(playerImage, 16, 16);
		
		// 初始化各地图对象
		backMap = new TiledLayer(15, 18, mapImage, 16, 16);
		wallMap = new TiledLayer(15, 18, mapImage, 16, 16);
		destMap = new TiledLayer(15, 18, mapImage, 16, 16);		
		
		// 初始化游戏地图
		initMap(GameEngine.getLevel());
		
		// 初始化菜单项
		cmdRestart = new Command("重新开始本关",Command.SCREEN, 1);
		cmdResume = new Command("返回主菜单",Command.SCREEN, 1);
		cmdExit = new Command("退出",Command.SCREEN, 1);	
		cmdCancel = new Command("取消",Command.SCREEN, 1);
		
		// 添加菜单到显示区
		addCommand(cmdRestart);
		addCommand(cmdResume);
		addCommand(cmdExit);	
		addCommand(cmdCancel);
		this.setCommandListener(this);		
	}

	protected void process(Graphics gra) {		
		switch(this.getKeyStates()){
		case GameCanvas.LEFT_PRESSED:
			player.setFrame(1);
			if(testCollision(-16, 0)) player.move(16, 0);
			++count;				
			break;
		
		case GameCanvas.RIGHT_PRESSED:
			player.setFrame(0);
			if(testCollision(16, 0)) player.move(-16, 0);
			++count;						
			break;
									
		case GameCanvas.UP_PRESSED:
			if(testCollision(0, -16)) player.move(0, 16);
			++count;					
			break;
		
		case GameCanvas.DOWN_PRESSED:
			if(testCollision(0, 16)) player.move(0, -16);
			++count;					
			break;			
		}
		
		gra.setColor(0, 0, 0);
		gra.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		if(isAllToDest()){
			// 过关
			GameEngine.setState(GameEngine.GAME_STATE_WIN);
			RUN = false;
		}else{
			backMap.paint(gra);
			wallMap.paint(gra);
			destMap.paint(gra);
			player.paint(gra);
			
			// 绘制箱子
			int boxNum = aBoxes.length;
			for(int i = 0; i < boxNum; ++i){
				aBoxes[i].paint(gra);
			}
		}
	}
	
	/**
	 * 初始化游戏场景
	 * 
	 * @param level 当前关卡
	 */
	public void initMap(int level){
		count = 0;
		
		// 临时变量，用来存储箱子对象
		Vector vBoxes = new Vector();
		
		try{
			InputStream is = this.getClass().getResourceAsStream("/map/pushbox_map" + level + ".MAR");
			DataInputStream dis = new DataInputStream(is);			
			for(int i = 0; i < 18; ++i){				
				for(int j = 0; j < 15; ++j){
					short a = dis.readShort();
					int b = 0x07 & a >> 13;
					switch(b){
						case 1:
							wallMap.setCell(j, i, 1);
							break;
						case 2:
							backMap.setCell(j, i, 2);
							break;
						case 7:
							backMap.setCell(j, i, 2);
							player.setPosition(16*j, 16*i);	
							break;
						case 4:
							backMap.setCell(j, i, 2);
							
							// 地图中出现箱子，则新建一个箱子对象，并存储在vBoxes中 
							Sprite newBox = new Sprite(boxImage, 16, 16);
							newBox.setPosition(16*j, 16*i);
							vBoxes.addElement(newBox);
							break;
						case 6:
							destMap.setCell(j, i, 6);
							break;
						default :
							backMap.setCell(j, i, b);	
							break;
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
	 * 菜单动作处理
	 */
	public void commandAction(Command c, Displayable d){
		if(c == cmdExit){
			// 退出游戏
			GameEngine.setState(GameEngine.GAME_STATE_EXIT);
			RUN = false;
		}else if(c == cmdResume){
			// 返回主菜单
			GameEngine.setState(GameEngine.GAME_STATE_MENU);
			RUN = false;
		}else if(c == cmdCancel){
			// 关闭菜单，返回游戏
		}else if(c == cmdRestart){
			// 重新开始本关卡
			System.gc();
			initMap(GameEngine.getLevel());
		}
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
		for(int i = 0; i < boxNum; ++i){
			if(!aBoxes[i].collidesWith(destMap, false)){
				return false;
			}
		}
		return true;
	}
}