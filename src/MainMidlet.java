import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;


public class MainMidlet extends MIDlet{
	
	private Display dis;
	MyEngine myEngine;
	
	public MainMidlet() {
		dis = Display.getDisplay(this);
		myEngine = new MyEngine();
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {}

	protected void pauseApp() {}

	protected void startApp() throws MIDletStateChangeException {
		dis.setCurrent(myEngine);
		myEngine.start();
	
		switch(myEngine.getGameState()){
		case IEngine.GAME_STATE_PAUSE:
			// 游戏暂停
			pauseApp();
			break;
		case IEngine.GAME_STATE_EXIT:
			// 设备进入Destoryed state
			destroyApp(true);
			break;
		}
	}
}