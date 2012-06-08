package tools;

import java.io.DataInputStream;
import java.io.InputStream;

import javax.microedition.lcdui.game.Sprite;

/**
 * ��ͼ������
 * 
 * ����˵��:
 * 
 * 1������ͼĿ¼����õ�ͼ���������ؿ�����
 * 2������tileWidth��tileHeight
 * 3����mapwin���ɵĵ�ͼת����һ����ά����:[tileIndex][���][rows, columns]�����ܳ��־ֲ���������޷����ʵ������
 * 
 * 
 * @author Guangya
 *
 */

public class MapManger {
	
	private int mapNum = 2;
	private String mapSource[];
	
	public MapManger(){
		scanMaps();
	}

	/**
	 * ����tile�ĳߴ�
	 * 
	 * @param width
	 * @param height
	 */
	public void setTileSize(int width, int height){}
	
	/**
	 * ���ָ��������Ӧ�ĵ�ͼ����
	 * 
	 * @param mapIndex
	 * @return
	 */
	public int[] getMap(int mapIndex){
		
		try{
			InputStream is = this.getClass().getResourceAsStream(mapSource[mapIndex]);
			DataInputStream dis = new DataInputStream(is);
			for(int i = 0; i < 18; ++i){				
				for(int j = 0; j < 15; ++j){
					short a = dis.readShort();
					int b = 0x07 & a >> 13;						
				}
			}	
		}catch(Exception e){}
		
		
		
		return new int[3];
	}
	
	/**
	 * ��õ�ͼ����
	 * 
	 * @return
	 */
	public int getMapNum(){
		return mapNum;
	}
	
	/**
	 * ɨ���ͼĿ¼������Ч�ĵ�ͼ���ļ�·����ŵ�����mapSource��
	 */
	private void scanMaps(){
		
	}
}
