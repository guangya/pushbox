import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;


// 1������ʾ��ͼƬ������Ϊ�����ӿڣ������պ�������ʾ������Ϣ�Ľӿڣ���
// 2���������˵��İ�ť����ѡ���ṩ���ýӿڣ����δ���ã�ʹ����ͨ�ı����档����
// 3����Ϸ���棨�Ա��ڸı���Ϸ����״̬����
// 4�������ߴ磻
// 5����ǰ��������Ϸ�����е����꣨��ѡ���ṩ���ýӿڣ����δ���ã�Ĭ��ʹ�ã�0��0��

class Info{

	private Engine engine;              // ��Ϸ����
	private int canvasWidth;            // ��Ϸ�����Ŀ�
	private int canvasHeight;           // ��Ϸ�����ĸ�
	private int posX;                   // ��ǰ�������Ͻ�����ڻ������Ͻǵ�X����
	private int posY;                   // ��ǰ�������Ͻ�����ڻ������Ͻǵ�Y����
	
	private Image imgMsg;               // ����������ʾ����Ϣ
	private Image imgLeftMenu;          // ��˵�ʹ�õ�ͼ��
	private Image imgRightMenu;         // �Ҳ˵�ʹ�õ�ͼ��
	private int leftMenuBoundState;     // ��˵��󶨵���Ϸ״̬
	private int rightMenuBoundState;	// �Ҳ˵��󶨵���Ϸ״̬
//	private int leftMenuWidth;          // ��˵��Ŀ��
	private int leftMenuHeight;         // ��˵��ĸ߶�
	private int rightMenuWidth;         // �Ҳ˵��Ŀ��
	private int rightMenuHeight;        // �Ҳ˵��ĸ߶�
	
	public Info(Engine engine, int width, int height){
		this.engine = engine;
		this.canvasWidth = width;
		this.canvasHeight = height;
		
		posX = 0;
		posY = 0;
	}
	
	/**
	 * ���õ�ǰ����Ҫ��ʾ����Ϣ
	 * 
	 * @param imgMsg
	 */
	public void setMsg(Image imgMsg){
		this.imgMsg = imgMsg;
	}
	
	/**
	 * ���ý�����˵�����
	 * 
	 * @param imgMenu   ��˵���ʾ����ϢͼƬ
	 * @param boundGameState ��˵��󶨵���Ϸ״̬
	 */
	public void setLeftMenu(Image imgMenu, int boundGameState){
		imgLeftMenu = imgMenu;
		leftMenuBoundState = boundGameState;
//		leftMenuWidth = imgMenu.getWidth();
		leftMenuHeight = imgMenu.getHeight();
	}
	
	/**
	 * ���ý����Ҳ˵�����
	 * 
	 * @param imgMenu   �˵���ʾ����ϢͼƬ
	 * @param boundGameState �˵��󶨵���Ϸ״̬
	 */
	public void setRightMenu(Image imgMenu, int boundGameState){
		imgRightMenu = imgMenu;
		rightMenuBoundState = boundGameState;
		rightMenuWidth = imgMenu.getWidth();
		rightMenuHeight = imgMenu.getHeight();
	}
	
	/**
	 * ���ó����ڻ���������ڻ������Ͻǵ�����
	 * 
	 * @param x �������Ͻ�����ڻ������Ͻǵ�X����
	 * @param y �������Ͻ�����ڻ������Ͻǵ�Y����
	 */
	public void setPosition(int x, int y){
		posX = x;
		posY = y;
	}
	
	/**
	 * ����ǰ�������߼�����������
	 * 
	 * @param gra
	 */
	public void process(Graphics gra){
		// ������Ҫ��Ϣ
		if(null != imgMsg){
			gra.drawImage(imgMsg, posX, posY, Graphics.LEFT|Graphics.TOP);
		}
		
		// ������˵�
		if(null != imgLeftMenu){
			gra.drawImage(imgLeftMenu, posX, posY + canvasHeight - leftMenuHeight, Graphics.LEFT|Graphics.TOP);
		}
		
		// �����Ҳ˵�
		if(null != imgRightMenu){
			gra.drawImage(imgRightMenu, posX + canvasWidth - rightMenuWidth, posY + canvasHeight - rightMenuHeight, Graphics.LEFT|Graphics.TOP);
		}
	}
	
	/**
	 * ����ǰ�����İ����¼�
	 * 
	 * @param gameAction
	 */
	public void keyEvent(int gameAction){
		switch(gameAction){
		case Engine.MENU_LEFT:
			if(null != imgLeftMenu){
				engine.setGameState(leftMenuBoundState);
			}
			break;
		case Engine.MENU_RIGHT:
			if(null != imgRightMenu){
				engine.setGameState(rightMenuBoundState);
			}
			break;
		}
	}
}