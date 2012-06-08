package tools;

import javax.microedition.lcdui.Image;

public class Tools {

	/**
	 * 创建图像img在指定透明度下的副本
	 * 
	 * @param img 原始图片            
	 * @param value 透明度,取值范围在0x00~0xff(0~255)之间，透明度越高，图像显示越不清晰
	 * @return 返回透明化后的图片
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
	 * 简化输出语句的书写
	 * @param str
	 */
	public static void p(String str){
		System.out.println(str);
	}
	public static void p(int num){
		System.out.println("" + num);
	}
}
