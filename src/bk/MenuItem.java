package bk;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * �˵�����
 * 
 * ����ָ���Ĳ���������һ������ѡ�к�δѡ������״̬����һ���¼��Ĳ˵���
 * 
 * @author WangGuangya
 * @version 1.0
 * @date 2012/06/01
 */

public class MenuItem {

	private String name;            // ��ť������
	private int width;              // ��ť���
	private int height;             // ��ť�ĸ�
	private boolean isSelected;     // ��ť�Ƿ�ѡ��	
	private Image btnImage;         // ��ťʹ�õ�ͼƬ
	private Image selectedBtnImage;	// �˵��ť��ѡ��״̬��ʹ�õ�ͼƬ
	private Graphics lastGra;       // �ϴλ���ʹ�õĻ���
	private int imgRelPosX;         // �˵���ʹ�õ�ͼƬ����ڲ˵���ť���ĵ��x����
	private int imgRelPosY;         // �˵���ʹ�õ�ͼƬ����ڲ˵���ť���ĵ��y����
	private int txtRelPosX;         // �˵���ʹ�õ������ı�����ڲ˵���ť���ĵ��x����
	private int txtRelPosY;         // �˵���ʹ�õ������ı�����ڲ˵���ť���ĵ��y����	
	
	
	/**
	 * ʹ�ø�����ͼ�񴴽�һ����ť
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
	 * ���Ʋ˵���
	 * 
	 * @param gra �˵�Ҫ���Ƶ�Ŀ��λ�û���
	 * @param x �˵������ĵ�X����
	 * @param y �˵������ĵ�Y����
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
	 * ��ȡ��ť���
	 * 
	 * @return
	 */
	public int getWidth(){
		return width;
	}
	
	/**
	 * ��ȡ��ť�߶�
	 * 
	 * @return
	 */
	public int getHeight(){
		return height;
	}
	
	/**
	 * �޸Ĳ˵�ͼƬ
	 * 
	 * @param btnImage
	 */
	public void setButtonImage(Image btnImage){
		this.btnImage = btnImage;
	}
	/**
	 * �޸Ĳ˵�ѡ��״̬�µ�ͼƬ
	 * 
	 * @param selectedImage
	 */
	public void setSelectedButtonImage(Image selectedImage){
		this.selectedBtnImage = selectedImage;
	}
	
	/**
	 * ���ò˵����ѡ��״̬
	 * 
	 * @param isSelected
	 */
	public void setSelected(boolean isSelected){
		this.isSelected = isSelected;
	}
		
	/**
	 * �����ı�������ʾʱ����ť���������λ��
	 * 
	 * �������ʹ�õĻ��ʺ��ϴλ��Ʋ˵���ʹ�õĻ�����ͬһ�����ʣ����ټ����ı����λ��
	 * 
	 * @param gra
	 */
	private void calculateTxtRelPos(Graphics gra){
		// �����ظ������ı����λ��
		if(gra == lastGra){
			return;
		}
		
		Font font = gra.getFont();
		
		txtRelPosX = font.stringWidth(name) / 2;
		txtRelPosY = font.getHeight() / 2;
		
		// �洢��ǰʹ�õĻ�����Ϊ�жϱ�ʶ�������ظ�����
		lastGra = gra;
	}
}
