import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;


public class MainMidlet extends MIDlet{
	
	private Display dis;
	private Engine engine;
	
	public MainMidlet() {
		dis = Display.getDisplay(this);
		engine = new Engine();
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		System.out.println("enter in destroy app");
	}

	protected void pauseApp() {}

	protected void startApp() throws MIDletStateChangeException {
		dis.setCurrent(engine);
		engine.run();
		
		switch(engine.getGameState()){
		case Engine.GAME_STATE_PAUSE:
			// 游戏暂停
			pauseApp();
			break;
		case Engine.GAME_STATE_EXIT:
			// 设备进入Destoryed state
			destroyApp(true);
			break;
		}
	}
}