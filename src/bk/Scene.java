package bk;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;

public abstract class Scene extends GameCanvas implements Runnable{
	
	protected boolean RUN = true;
	private Graphics gra;
	private Thread curThread;
	
	protected int sleepTime = 100;
	
	public Scene(){
		super(true);
		
		gra = this.getGraphics();		
		initialize();
	}
	
	/**
	 * 进程循环
	 * 
	 * @return void
	 */
	public void run(){
		while(RUN){
			process(gra);
			this.flushGraphics();
			
			try{
				Thread.sleep(sleepTime);
			}catch(Exception e){}
		}
	}
	
	/**
	 * 显示界面
	 * 
	 * @return void
	 */
	public void show(){
		curThread = new Thread(this);
		curThread.start();
		
		try{
			curThread.join();
		}catch(Exception e){}
	}
	
	/**
	 * 当前线程是否已死
	 * 
	 * @return boolean
	 */
	public boolean isAlive(){
		return curThread.isAlive();
	}
	
	/**
	 * 初始化开始关卡选择菜单
	 * 
	 * @return void
	 */
	protected abstract void initialize();
	
	/**
	 * 处理场景逻辑
	 * 
	 * @return void
	 */
	protected abstract void process(Graphics gra);
}