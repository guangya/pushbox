import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * 创建一个具有文本、背景色、选中状态及绑定事件的菜单
 * 
 * 菜单中心点的位置可以通过setPosition方法进行设定，
 * 文本、背景色等元素的绘制均是以该点为中心点
 * 
 * @author guangya
 * @email guangyait@gmail.com
 *
 */
public class Menu {

	// 菜单的状态标识
	public static final int MENU_ITEM_STATE_NORMAL   = 0;
	public static final int MENU_ITEM_STATE_SELECTED = 1;
	
	// 菜单显示的文本（菜单名字）
	private String text;
	// 菜单文本使用的字体，如果未设置，则使用系统字体
	private Font font;
	// 菜单中心点坐标
	private int posX;
	private int posY;
	// 菜单绑定的事件
	private int boundEvent;
	// 菜单的状态
	private int state;
	// 菜单在未选中时的背景图片
	private Image background;
	// 菜单在选中时的背景图片
	private Image selectedBackground;
	// 菜单文字整体的左上角相对于菜单中心点的坐标
	private int textPosX;
	private int textPosY;
	// 菜单背景的左上角相对于菜单中心点的坐标
	private int bgPosX;
	private int bgPosY;
	// 选中状态下菜单背景的左上角相对于菜单中心点的坐标
	private int sBgPosX;
	private int sBgPosY;
	
	public Menu(String text){
		this.text = text;
		// 默认使用系统字体显示菜单文本
		font = Font.getFont(Font.FACE_SYSTEM,Font.STYLE_PLAIN,Font.SIZE_LARGE);
		// 计算文本相对于菜单中心点的坐标，依赖于text、font的初始化，勿颠倒顺序
		calculateTextPos();
		
		posX = 0;
		posY = 0;
		boundEvent = -1;
		state = MENU_ITEM_STATE_NORMAL;		
		bgPosX   = 0;
		bgPosY   = 0;
		sBgPosX  = 0;
		sBgPosY  = 0;
	}
	
	/**
	 * 将菜单绘制到显示设备上
	 * 
	 * @param gra
	 */
	public void paint(Graphics gra){
		// 根据菜单的不同状态，绘制不同的背景
		switch(state){
		
		case MENU_ITEM_STATE_SELECTED:
			if(null != selectedBackground){
				gra.drawImage(selectedBackground, posX + sBgPosX, posY + sBgPosY, Graphics.LEFT|Graphics.TOP);
			}
			break;
			
		case MENU_ITEM_STATE_NORMAL:
		default:
			if(null != background){
				gra.drawImage(background, posX + bgPosX, posY + bgPosY, Graphics.LEFT|Graphics.TOP);
			}
			break;
		}
		
		// 如果设定了菜单文本，则绘制该文本，否则，不进行绘制操作
		if(text != ""){
			gra.setFont(font);
			gra.drawString(text, posX + textPosX, posY + textPosY, Graphics.LEFT|Graphics.TOP);
		}
	}
	
	/**
	 * 设置菜单中心点相对于画布左上角的坐标
	 * 
	 * @param x
	 * @param y
	 */
	public void setPosition(int x, int y){
		this.posX = x;
		this.posY = y;
	}
	
	/**
	 * 设置菜单显示的文本
	 * 
	 * @param text
	 */
	public void setText(String text){
		this.text = text;
		// 重新计算文本坐标
		calculateTextPos();
	}
	
	/**
	 * 设置菜单文本使用的字体
	 * 
	 * @param font
	 */
	public void setFont(Font font){
		this.font = font;
		// 重新计算文本坐标
		calculateTextPos();
	}
	
	/**
	 * 设置菜单绑定的事件
	 * 
	 * @param event
	 */
	public void setBoundEvent(int event){
		this.boundEvent = event;
	}
	
	/**
	 * 设置菜单的状态
	 * 
	 * @param state
	 */
	public void setState(int state){
		this.state = state;
	}
	
	/**
	 * 设置菜单背景图片
	 * 
	 * @param bg
	 */
	public void setBackground(Image bg){
		this.background = bg;
		
		bgPosX = -(background.getWidth() / 2);
		bgPosY = -(background.getHeight() / 2);
	}
	
	/**
	 * 设置菜单被选中时的背景图片
	 * 
	 * @param sBg
	 */
	public void setSelectedBackground(Image sBg){
		this.selectedBackground = sBg;
		
		sBgPosX = -(selectedBackground.getWidth() / 2);
		sBgPosY = -(selectedBackground.getHeight() / 2);
	}
	
	/**
	 * 获取菜单的X轴坐标
	 * 
	 * @return
	 */
	public int getX(){
		return posX;
	}
	
	/**
	 * 获取菜单的Y轴坐标
	 * @return
	 */
	public int getY(){
		return posY;
	}
	
	/**
	 * 获取菜单显示的文本
	 * 
	 * @return
	 */
	public String getText(){
		return text;
	}
	
	/**
	 * 获取菜单绑定的事件
	 * 
	 * @return
	 */
	public int getBoundEvent(){
		return boundEvent;
	}
	
	/**
	 * 获取菜单当前的状态
	 * 
	 * @return
	 */
	public int getState(){
		return state;
	}
	
	/**
	 * 计算菜单文本相对于菜单中心的坐标
	 */
	private void calculateTextPos(){
		if(null == text || "" == text || null == font){
			textPosX = 0;
			textPosY = 0;
			return;
		}		
		
		textPosX = -(font.stringWidth(text) / 2);
		textPosY = -(font.getHeight() / 2);
	}
}