package tools;

import javax.microedition.lcdui.Image;

public class Tools {

	/**
	 * ����ͼ��img��ָ��͸�����µĸ���
	 * 
	 * @param img ԭʼͼƬ            
	 * @param value ͸����,ȡֵ��Χ��0x00~0xff(0~255)֮�䣬͸����Խ�ߣ�ͼ����ʾԽ������
	 * @return ����͸�������ͼƬ
	 */
	public static Image getTransparent(Image img, int alphaValue) {
		int width = img.getWidth();
		int height = img.getHeight();

		int[] imgData = new int[width * height];

		img.getRGB(imgData, 0, width, 0, 0, width, height);
		
		int length = imgData.length;
		
		for (int i = 0; i < length; i++) {
			imgData[i] = imgData[i] & 0x00ffffff | ((~alphaValue) << 24);
		}

		return Image.createRGBImage(imgData, width, height, true);
	}
	
	
	/**
	 * �����������д
	 * @param str
	 */
	public static void p(String str){
		System.out.println(str);
	}
	public static void p(int num){
		System.out.println("" + num);
	}
}
