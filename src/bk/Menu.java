package bk;

import javax.microedition.lcdui.Graphics;



public class Menu{
	
	private int width;
	private int height;	
	private GameEngine engine;
	
	public Menu(GameEngine engine, int width, int height){
		this.width = width;
		this.height = height;
		this.engine = engine;
	}
	
	/**
	 * ���ò˵���������Ͻ��������Ļ������
	 * 
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y){
		
	}
	
	/**
	 * ������Ϣ����
	 * 
	 * @param gameAction
	 */
	public void keyEvent(int gameAction){
		
	}
	
	/**
	 * �����߼�����
	 * 
	 * @param gra
	 */
	public void process(Graphics gra){
		
	}
}