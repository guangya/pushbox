package bk;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;


public class WinScene extends Scene implements CommandListener {
	private Image winImage;
	private Command cmdExit;	
	private Command cmdContinue;
	
	/**
	 * 初始化开始关卡选择菜单
	 * 
	 * @return void
	 */
	protected void initialize(){
		try{			
			winImage = Image.createImage("/image/win.png");
		}catch(Exception e){}
		
		cmdExit = new Command("退出",Command.EXIT, 1);	
		cmdContinue = new Command("进入下一关",Command.SCREEN, 1);		
		addCommand(cmdExit);
		addCommand(cmdContinue);
		
		this.setCommandListener(this);
	}
	
	/**
	 * 处理场景逻辑，并返回退出消息
	 * 
	 * @return 场景退出消息
	 */
	protected void process(Graphics gra){		
		try{			
			gra.drawImage(winImage, 0, 0, Graphics.TOP|Graphics.LEFT);
		}catch(Exception e){}
	}

	/**
	 * 处理菜单操作
	 * 
	 * @return void
	 */
	public void commandAction(Command cmd, Displayable dis) {
		if(cmd == cmdExit){
			RUN = false;
		}else if(cmd == cmdContinue){
			RUN = false;
		}		
	}	
}