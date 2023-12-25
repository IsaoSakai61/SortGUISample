package SortGUI;

import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Mainクラス
 * ループでの再描画が反映されなかったのでスレッドとして動作するように
 * ActionListenerはボタンのリスナー
 */
public class Main extends Thread implements ActionListener{
	private static JFrame frame;			// JFrameインスタンス
	private static PaintCanvas canvas;		// PaintCanvasインスタンス(Canvas継承)
	private static int width = 640;			// WIndow横サイズ
	private static int height = 550;		// Window縦サイズ
	private static int canvas_height = 480;	// Canvas縦サイズ
	JButton btn1,btn2,btn3,btn4,btn5;		// ボタンインスタンス
	MySort sort = new MySort();				// ソートクラス（ソート処理をまとめただけのクラス）
	int[] list = new int[width];			// ソートするデータリスト
	SORT exe = SORT.NONE;					// 実行するソートの種類
	int delay = 0;							// ウェイト時間(ms)
	
	// Enumerate(列挙型)
	enum SORT{
		NONE,		// ソートなし
		BUBBLE,		// バブルソート
		INSERTION,	// 挿入ソート
		SHELL,		// シェルソート
		QUICK,		// クイックソート
	};
	
	Main(){
		// Windowの作成
		initFrame(width, height);					// Frame(Window)を生成し初期化
		initCanvas(frame, width, canvas_height);	// 描画するCanvasを生成し初期化
	}
	
	/**
	 * main()
	 * アプリのエントリーポイントになる
	 * @param args
	 */
	public static void main(String[] args) {
		Main main = new Main();						// 自身のインスタンスを生成する。
		main.run();									// run()を実行（プログラムはここからはじまる）
		
	}
	/**
	 * swingフレームの初期化
	 * @param w
	 * @param h
	 */
	private void initFrame(int w, int h) {
		frame = new JFrame();									// Frame生成
		frame.getContentPane().setLayout(new FlowLayout());		// レイアウト設定
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// 閉じるボタン設定
		frame.setTitle("SortSample");							// タイトル設定
		frame.setSize(w, h);									// サイズ設定
		frame.setResizable(false);								// 最大化不可
		
		// 各ボタン設定
		// ボタンごとのリスターは全て同じリスナー関数にして関数内でボタンの識別をしている。
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
		
		// JPanelに各種ボタンを追加しJFrameにJPanelを追加する
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
        canvas.setPreferredSize(new Dimension(w, h));	// このコンポーネントの適切なサイズを設定します。
        frame.getContentPane().add(canvas);				// JFrameにCanvasを追加
        
		frame.setVisible(true);							// 可視化
	}
	/**
	 * スレッドにしないとうまく描画されない
	 */
	public void run() {
		
		//System.out.println("run");
		exe = SORT.NONE;
		
		// メインループ
		while(true) {
			executeSort(exe);	// exeにフラグが設定されてたらソートを実行する
			exe = SORT.NONE;	// ソートを実行したらフラグ初期化
			//System.out.println("run");
			try {
				Thread.sleep(1);	// 念のためにsleep(1)しとく
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
			list[i] = rnd.nextInt(0, 100);	// 0～100の乱数
			drawList(list,i);				// 作ったデータを描画
		}
		canvas.repaint();					// 全部のデータを描画したら再描画して画面に反映
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
				canvas.repaint();			//再描画
				try {
					Thread.sleep(delay);	// ウェイト処理
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
