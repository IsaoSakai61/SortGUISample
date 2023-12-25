package SortGUI;

import java.awt.Color;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class MySort {
	
	/**
	 * バブルソート
	 * @param array ソートを行うint配列
	 */
	public void bubbleSort(int[] array,
			BiConsumer<Integer,Integer> drawLine,
			Consumer<Color> setColor,
			Runnable repaint) {
		
		// デバッグ表示
		//System.out.println("**BubbleSort Start**");
		//dbg_arrayPrint(array);
				
		for(int i=0; i<array.length; i++) {
			for(int j=0; j<array.length-i-1; j++) {
				if( array[j] > array[(j + 1)] ) {
					
					ArraySwap(array, j, (j + 1),
							drawLine,
							setColor,
							repaint);
				}
			}
		}
	}
	
	/**
	 * 配列のaとbのデータを交換する
	 * @param array
	 * @param a
	 * @param b
	 */
	private void ArraySwap(int[] array, int a, int b,
			BiConsumer<Integer,Integer> drawLine,
			Consumer<Color> setColor,
			Runnable repaint) {
		
		if(setColor != null) 
			setColor.accept(Color.white);
		if(drawLine != null)
			drawLine.accept(Integer.valueOf(a), Integer.valueOf(b));
		
		
		int buf;
		buf = array[a];
		array[a] = array[b];
		array[b] = buf;
		
		
		if(setColor != null) 
			setColor.accept(Color.black);
		if(drawLine != null)
			drawLine.accept(Integer.valueOf(a), Integer.valueOf(b));
		if(repaint != null)
			repaint.run();
		
		
		// デバッグ表示
		dbg_arrayPrint(array);
	}
	
	/**
	 * 配列を表示する
	 * @param array
	 */
	private void dbg_arrayPrint(int[] array) {
		/*String str = new String();
		for(int i=0;i<array.length;i++) {
			str += "" + array[i] + " ";
		}
		System.out.println(str);*/
	}
	
	/**
	 * 挿入ソート
	 * @param array ソートを行うint配列
	 */
	public void insertionSort(int[] array,
			BiConsumer<Integer,Integer> drawLine,
			Consumer<Color> setColor,
			Runnable repaint) {
		
		System.out.println("**InsertionSort Start**");
		
		insertionSort(array,1,drawLine,setColor,repaint);
		
	}
	
	/**
	 * 挿入ソート
	 * シェルソートでも挿入ソートを使うのでskip追加
	 * 
	 * @param array ソートを行うint配列
	 * @param skip 比較するときのskip数
	 * 
	 */
	private void insertionSort(int[] array, int skip,
			BiConsumer<Integer,Integer> drawLine,
			Consumer<Color> setColor,
			Runnable repaint) {
		
		// デバッグ表示
		dbg_arrayPrint(array);
				
		for (int i=skip; i < array.length; i=(i + skip)) {
			for (int j=i-skip; j >= 0; j=(j - skip)) {
				if(array[j] > array[j + skip]) {
					ArraySwap(array, (j + skip), j,drawLine,setColor,repaint);
				}
			}
		}
	}
	
	/**
	 * シェルソート
	 * @param array ソートを行うint配列
	 */
	public void shellSort(int[] array,
			BiConsumer<Integer,Integer> drawLine,
			Consumer<Color> setColor,
			Runnable repaint) {
		int skip;
		System.out.println("**ShellSort Start**");
		
		skip = array.length / 2;	// この間隔はどのような間隔が最適解かは未解決。121→40→13→4→1とするのが良いらしい。
		
		while( skip > 0 ) {
			insertionSort(array,skip,drawLine,setColor,repaint);
			skip = skip / 2;
		}
	}
	
	/**
	 * クイックソート
	 * @param array ソートを行うint配列
	 */
	public void quickSort(int[] array,
			BiConsumer<Integer,Integer> drawLine,
			Consumer<Color> setColor,
			Runnable repaint) {
		// デバッグ表示
		System.out.println("**QuickSort Start**");
		dbg_arrayPrint(array);
				
		quickSort(array,0,array.length-1,drawLine,setColor,repaint);
	}
	/**
	 * クイックソート
	 * 再帰で処理を行う
	 * @param array ソートを行うint配列
	 * @param left 
	 * @param right
	 */
	private void quickSort(int[] array, int left, int right,
			BiConsumer<Integer,Integer> drawLine,
			Consumer<Color> setColor,
			Runnable repaint
			) {
		// 検索位置が同じか右が左より左側になったら再帰処理終了
		if(left >= right) {
			return;
		}
		
		// ピボット位置
		int pivot = array[(left + right)/2];
		int l = left;
		int r = right;
		
		while(l <= r) {
			while(array[l] < pivot) { l++; }	// 左側の位置を探す
			while(array[r] > pivot) { r--; }	// 右側の位置を探す
			
			if(l <= r) {				// lとrが交差したら処理しない
				ArraySwap(array, l, r,
						drawLine,setColor,repaint);	// lとrの位置のデータを交換
				l++;
				r--;
			}
		}
		quickSort(array, left, r,drawLine,setColor,repaint);	//ピボット位置より左側をソート
		quickSort(array, l ,right,drawLine,setColor,repaint);	//ピボット位置より右側をソート
	}
}
