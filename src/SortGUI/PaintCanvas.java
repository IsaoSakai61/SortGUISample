package SortGUI;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class PaintCanvas extends Canvas{
	private static final long serialVersionUID = 1L;
	
	private BufferedImage image = null;
	private Graphics2D g2;
	private int width,height;
	
	public PaintCanvas(int w, int h) {
        width = w;
        height = h;
        this.setBackground(Color.white);
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        g2 = (Graphics2D) image.getGraphics();
        this.cls();
    }
	
	/**
	 * 画面消去
	 */
	public void cls() {
		if(g2 == null) return;
		
		g2.setColor(Color.white);
		g2.fillRect(0, 0, width, height);
		//g2.clearRect(0, 0, width, height);
	}
	
	public void update(Graphics g) {
		//super.update(g);
		paint(g);
	}
	/**
	 * 描画メイン
	 * 描画するときはrepaint()を呼ぶ
	 */
	public void paint(Graphics g) {
		if(g2 == null) return;
		
		g.drawImage(image,0,height - this.getHeight(),null);
	}
	
	/**
	 * 色の設定
	 * @param c
	 */
	public void setColor(Color c) {
		if(g2 == null) return;
		g2.setColor(c);
	}
	/**
	 * 線の描画
	 * @param x
	 * @param y
	 * @param x2
	 * @param y2
	 * @param width
	 */
	public void drawLine(int x, int y, int x2, int y2, float width) {
		if(g2 == null) return;
		// 線の太さ設定
		BasicStroke wideStroke = new BasicStroke(width);
	    g2.setStroke(wideStroke);
	    
		g2.drawLine(x, y, x2, y2);
	}
	
}
