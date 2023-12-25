package SortGUI;

import java.awt.FlowLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends Thread implements ActionListener{
	private static JFrame frame;
	private static PaintCanvas canvas;
	private static int width = 640;
	private static int height = 550;
	private static int canvas_height = 480;
	JButton btn1,btn2,btn3,btn4,btn5;
	MySort sort = new MySort();
	int[] list = new int[width];
	SORT exe = SORT.NONE;
	int delay = 0;
	
	enum SORT{
		NONE,
		BUBBLE,
		INSERTION,
		SHELL,
		QUICK,
	};
	
	Main(){
		// Windowの作成
		initFrame(width, height);
		initCanvas(frame, width, canvas_height);
	}
	
	public static void main(String[] args) {
		Main main = new Main();
		main.run();
		
	}
	/**
	 * swingフレームの初期化
	 * @param w
	 * @param h
	 */
	private void initFrame(int w, int h) {
		frame = new JFrame();
		frame.getContentPane().setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("SortSample");
		frame.setSize(w, h);
		frame.setResizable(false);	//最大化不可
		
		btn1 = new JButton("バブルソート");
		btn1.addActionListener(this);
		btn2 = new JButton("挿入ソート");
		btn2.addActionListener(this);
		btn3 = new JButton("シェルソート");
		btn3.addActionListener(this);
		btn4 = new JButton("クイックソート");
		btn4.addActionListener(this);
		btn5 = new JButton("初期化");
		btn5.addActionListener(this);
		
		JPanel pnl = new JPanel();
		pnl.add(btn1);
		pnl.add(btn2);
		pnl.add(btn3);
		pnl.add(btn4);
		pnl.add(btn5);
		frame.getContentPane().add(pnl);
		
	}
	/**
	 * Canvasの初期化
	 * @param frame
	 * @param w
	 * @param h
	 */
	private static void initCanvas(JFrame frame,int w, int h) {
		// PaintCanvasのインスタンスを生成
        canvas = new PaintCanvas(w,h);
        canvas.setPreferredSize(new Dimension(w, h));
        frame.getContentPane().add(canvas);
        
		frame.setVisible(true);
	}
	/**
	 * スレッドにしないとうまく描画されない
	 */
	public void run() {
		
		//System.out.println("run");
		exe = SORT.NONE;
		
		while(true) {
			executeSort(exe);
			exe = SORT.NONE;
			//System.out.println("run");
			try {
				Thread.sleep(1);
			}catch(Exception e) {}
		
			 
		}
	}
	
	/**
	 * 各配列データを画面に描画
	 * @param list
	 * @param index
	 */
	private static void drawList(int[] list,int index) {
		canvas.drawLine(index,
				canvas_height,
				index,
				canvas_height - (list[index] * 4),
				1.0f);
	}
	/**
	 * ソートするデータリストの初期化
	 * データ数は画面の横サイズ(データ１つが1pxの縦線になる)
	 */
	void initSortList() {
		canvas.cls();
		Random rnd = new Random();
		canvas.setColor(Color.black);
		for(int i=0;i<list.length;i++) {
			list[i] = rnd.nextInt(0, 100);
			drawList(list,i);
		}
		canvas.repaint();
	}
	/**
	 * 各種ソートの実行
	 * @param sortId
	 */
	private void executeSort(SORT sortId) {
		
		// ラムダ式関数群
		BiConsumer<Integer,Integer> drawLine = new BiConsumer<Integer,Integer>(){
			@Override
			public void accept(Integer t, Integer u) {
				drawList(list,t);
				drawList(list,u);
			}
		};
		
		Consumer<Color> setColor = new Consumer<Color>() {
			@Override
			public void accept(Color t) {
				canvas.setColor(t);
			}
			
		};
		Runnable repaint = new Runnable(){
			@Override
			public void run() {
				canvas.repaint();
				try {
					Thread.sleep(delay);
				}catch(Exception e) {}
				
			}
			
		};
		
		switch(sortId) {
		case BUBBLE:
			// バブルソート
			sort.bubbleSort( list, drawLine, setColor, repaint );
			break;
		case INSERTION:
			// 挿入ソート
			sort.insertionSort(list, drawLine, setColor, repaint);
			break;
		case SHELL:
			// シェルソート
			sort.shellSort(list, drawLine, setColor, repaint);
			break;
		case QUICK:
			// クイックソート
			sort.quickSort(list, drawLine, setColor, repaint);
			break;
		default:
			break;
		}
	}
	
	
	
	/**
	 * ボタンアクション
	 */
	public void actionPerformed(ActionEvent e){
		
		//ボタンが押されたら実行フラグを立てる
		if(e.getSource() == btn1) {
			System.out.println("バブルソート");
			exe = SORT.BUBBLE;
			delay = 0;
		}
		if(e.getSource() == btn2) {
			System.out.println("挿入ソート");
			exe = SORT.INSERTION;
			delay = 0;
		}
		if(e.getSource() == btn3) {
			System.out.println("シェルソート");
			exe = SORT.SHELL;
			delay = 0;
		}
		if(e.getSource() == btn4) {
			System.out.println("クイックソート");
			exe = SORT.QUICK;
			delay = 1;	//クイックソートは早すぎるのでわざと遅延させる
		}
		if(e.getSource() == btn5) {
			System.out.println("初期化");
			
			initSortList();
			exe = SORT.NONE;
			delay = 0;
		}
		
	}
}
