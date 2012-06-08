package bk;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * 菜单项类
 * 
 * 根据指定的参数，生成一个具有选中和未选中两种状态、绑定一个事件的菜单项
 * 
 * @author WangGuangya
 * @version 1.0
 * @date 2012/06/01
 */

public class MenuItem {

	private String name;            // 按钮的名称
	private int width;              // 按钮宽宽
	private int height;             // 按钮的高
	private boolean isSelected;     // 按钮是否被选中	
	private Image btnImage;         // 按钮使用的图片
	private Image selectedBtnImage;	// 菜单项按钮被选中状态下使用的图片
	private Graphics lastGra;       // 上次绘制使用的画笔
	private int imgRelPosX;         // 菜单项使用的图片相对于菜单按钮中心点的x坐标
	private int imgRelPosY;         // 菜单项使用的图片相对于菜单按钮中心点的y坐标
	private int txtRelPosX;         // 菜单项使用的名称文本相对于菜单按钮中心点的x坐标
	private int txtRelPosY;         // 菜单项使用的名称文本相对于菜单按钮中心点的y坐标	
	
	
	/**
	 * 使用给定的图像创建一个按钮
	 * 
	 * @param name
	 * @param btnImage
	 * @param selectedBtnImage
	 */
	public MenuItem(String name, Image btnImage, Image selectedBtnImage){
		this.name = name;
		this.btnImage = btnImage;
		this.selectedBtnImage = selectedBtnImage;		
		width = btnImage.getWidth();
		height = btnImage.getHeight();			
		imgRelPosX = width / 2;
		imgRelPosY = height / 2;
		txtRelPosX = 0;
		txtRelPosY = 0;		
		isSelected = false;
	}
	
	/**
	 * 绘制菜单项
	 * 
	 * @param gra 菜单要绘制的目标位置画笔
	 * @param x 菜单项中心点X坐标
	 * @param y 菜单项中心点Y坐标
	 * @return void
	 */
	public void draw(Graphics gra, int x, int y){
		if(isSelected){
			gra.drawImage(selectedBtnImage, x - imgRelPosX, y - imgRelPosY, Graphics.TOP|Graphics.LEFT);
		}else{
			gra.drawImage(btnImage, x - imgRelPosX, y - imgRelPosY, Graphics.TOP|Graphics.LEFT);
		}
		
		calculateTxtRelPos(gra);
		gra.drawString(name, x - txtRelPosX, y - txtRelPosY, Graphics.TOP|Graphics.LEFT);
	}
	
	/**
	 * 获取按钮宽度
	 * 
	 * @return
	 */
	public int getWidth(){
		return width;
	}
	
	/**
	 * 获取按钮高度
	 * 
	 * @return
	 */
	public int getHeight(){
		return height;
	}
	
	/**
	 * 修改菜单图片
	 * 
	 * @param btnImage
	 */
	public void setButtonImage(Image btnImage){
		this.btnImage = btnImage;
	}
	/**
	 * 修改菜单选中状态下的图片
	 * 
	 * @param selectedImage
	 */
	public void setSelectedButtonImage(Image selectedImage){
		this.selectedBtnImage = selectedImage;
	}
	
	/**
	 * 设置菜单项的选中状态
	 * 
	 * @param isSelected
	 */
	public void setSelected(boolean isSelected){
		this.isSelected = isSelected;
	}
		
	/**
	 * 计算文本居中显示时，按钮中心坐标的位置
	 * 
	 * 如果本次使用的画笔和上次绘制菜单项使用的画笔是同一个画笔，则不再计算文本相对位置
	 * 
	 * @param gra
	 */
	private void calculateTxtRelPos(Graphics gra){
		// 避免重复计算文本相对位置
		if(gra == lastGra){
			return;
		}
		
		Font font = gra.getFont();
		
		txtRelPosX = font.stringWidth(name) / 2;
		txtRelPosY = font.getHeight() / 2;
		
		// 存储当前使用的画笔作为判断标识，避免重复计算
		lastGra = gra;
	}
}
