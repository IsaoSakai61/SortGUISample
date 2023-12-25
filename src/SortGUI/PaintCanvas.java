package SortGUI;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class PaintCanvas extends Canvas{
	private static final long serialVersionUID = 1L;
	
	private BufferedImage image = null;		// Canvasに直接描かないでBufferImageに描きこみ
											// ImageをCanvasに描画するダブルバッファ方式。
	private Graphics2D g2;					// imageのGraphics2Dインスタンス
	private int width,height;				// Canvasサイズ
	
	/**
	 * コンストラクタ
	 * @param w	サイズ
	 * @param h サイズ
	 */
	public PaintCanvas(int w, int h) {
        width = w;
        height = h;							
        this.setBackground(Color.white);							// 背景は白
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);// ImageはINT_RGBで生成
        g2 = (Graphics2D) image.getGraphics();						// Graphics2D取得（描画する際に使用する）
        this.cls();													// 画面を消去して初期化
    }
	
	/**
	 * 画面消去
	 */
	public void cls() {
		if(g2 == null) return;
		
		g2.setColor(Color.white);			// 白設定
		g2.fillRect(0, 0, width, height);	// 全体を塗りつぶす
		//g2.clearRect(0, 0, width, height);
	}
	
	/**
	 * repaint()が呼ばれるとupdate()が呼ばれる
	 * デフォルトだと余計なことをするのでupdate()はオーバーライドしとく
	 */
	public void update(Graphics g) {
		//super.update(g);
		paint(g);
	}
	/**
	 * 描画メイン
	 * 描画するときはrepaint()を呼ぶ。このメソッドを直に呼び出さない。
	 */
	public void paint(Graphics g) {
		if(g2 == null) return;
		
		// 描画されたImageをCanvasに描画する
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
