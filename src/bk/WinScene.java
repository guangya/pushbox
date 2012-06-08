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
	 * ��ʼ����ʼ�ؿ�ѡ��˵�
	 * 
	 * @return void
	 */
	protected void initialize(){
		try{			
			winImage = Image.createImage("/image/win.png");
		}catch(Exception e){}
		
		cmdExit = new Command("�˳�",Command.EXIT, 1);	
		cmdContinue = new Command("������һ��",Command.SCREEN, 1);		
		addCommand(cmdExit);
		addCommand(cmdContinue);
		
		this.setCommandListener(this);
	}
	
	/**
	 * �������߼����������˳���Ϣ
	 * 
	 * @return �����˳���Ϣ
	 */
	protected void process(Graphics gra){		
		try{			
			gra.drawImage(winImage, 0, 0, Graphics.TOP|Graphics.LEFT);
		}catch(Exception e){}
	}

	/**
	 * ����˵�����
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