//package bk;
//import javax.microedition.lcdui.Command;
//import javax.microedition.lcdui.CommandListener;
//import javax.microedition.lcdui.Displayable;
//import javax.microedition.lcdui.Graphics;
//import javax.microedition.lcdui.Image;
//import javax.microedition.lcdui.game.GameCanvas;
//
//
//public class Help extends GameCanvas implements CommandListener{
//
//	
//	public final static int HELP_THEME_ABOUT = 1;
//	public final static int HELP_THEME_Operation = 2;
//	
//	private int curTheme;
//	private Image aboutImage;	
//	private int width;
//	private int height;	
//	private Graphics gra;
//	
//	public final static int HELP_THEME_INTRO = 2;
//	private Image bgImage;
//	private Image introImage;
//	
//	public Help(MainMidlet engine){
//		super(false);
//		curTheme = HELP_THEME_ABOUT;
//		try{
//			bgImage = Image.createImage("/image/background.png");
//			introImage = Image.createImage("/image/gameIntro.png");
//			aboutImage = Image.createImage("/image/gameAbout.png");
//		}catch(Exception e){}
//		
//		gra = this.getGraphics();
//		width = this.getWidth();
//		height = this.getHeight();
//	}
//	
//	public void setTheme(int theme){
//		curTheme = theme;
//	}
//	
//	public void show(){
//		gra.drawImage(bgImage, 0, 0, Graphics.TOP|Graphics.LEFT);
////		gra.setColor(0, 0, 0);
////		gra.fillRect(0, 0, width, height);
//		switch(curTheme){
//		case HELP_THEME_ABOUT:
//			gra.drawImage(aboutImage, 0, 0, Graphics.TOP|Graphics.LEFT);
//			break;
//		case HELP_THEME_INTRO:
//			gra.drawImage(introImage, 0, 0, Graphics.TOP|Graphics.LEFT);
//			break;
//		}
//	}
//
//	public void commandAction(Command cmd, Displayable dis) {
////		if(cmd == )
//		
//	}
//}
