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
	 * ����ѭ��
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
	 * ��ʾ����
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
	 * ��ǰ�߳��Ƿ�����
	 * 
	 * @return boolean
	 */
	public boolean isAlive(){
		return curThread.isAlive();
	}
	
	/**
	 * ��ʼ����ʼ�ؿ�ѡ��˵�
	 * 
	 * @return void
	 */
	protected abstract void initialize();
	
	/**
	 * �������߼�
	 * 
	 * @return void
	 */
	protected abstract void process(Graphics gra);
}