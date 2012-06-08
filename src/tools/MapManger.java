package tools;

import java.io.DataInputStream;
import java.io.InputStream;


/**
 * 地图管理类
 *
 * 功能说明:
 *
 * 1、检查地图目录，获得地图总数（即关卡数）
 * 2、设置tileWidth、tileHeight
 * 3、将mapwin生成的地图转化成一个多维数组:[tileIndex][组号][rows, columns]（可能出现局部数组对象无法访问的情况）
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
	 * 设置tile的尺寸
	 * 
	 * @param width
	 * @param height
	 */
	public void setTileSize(int width, int height){}
	
	/**
	 * 获得指定索引对应的地图数据
	 * 
	 * @param mapIndex
	 * @return
	 */
	public int[] getMap(int mapIndex){
		
		try{
			InputStream is = this.getClass().getResourceAsStream(mapSource[mapIndex]);
			DataInputStream dis = new DataInputStream(is);
//			for(int i = 0; i < 18; ++i){				
//				for(int j = 0; j < 15; ++j){
//					short a = dis.readShort();
//					int b = 0x07 & a >> 13;						
//				}
//			}	
		}catch(Exception e){}		
		
		return new int[3];
	}
	
	/**
	 * 获得地图总数
	 * 
	 * @return
	 */
	public int getMapNum(){
		return mapNum;
	}
	
	/**
	 * 扫描地图目录并将有效的地图的文件路径存放到数组mapSource中
	 */
	private void scanMaps(){
		
	}
}
