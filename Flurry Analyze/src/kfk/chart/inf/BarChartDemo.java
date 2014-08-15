//package kfk.chart.inf;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//import org.jfree.chart.ChartFactory;
//import org.jfree.chart.ChartUtilities;
//import org.jfree.chart.JFreeChart;
//import org.jfree.chart.plot.PlotOrientation;
//import org.jfree.data.category.CategoryDataset;
//import org.jfree.data.category.DefaultCategoryDataset;
//
//public class BarChartDemo {
//	public static void main(String args[]) throws IOException{
//		CategoryDataset dataset = getDataSet2();
//		JFreeChart chart = ChartFactory.createBarChart3D(
//				"Fruit Chart", 
//				"Fruit", 
//				"quanty", 
//				dataset, 
//				PlotOrientation.VERTICAL, 
//				false, 
//				false, 
//				false
//				);
//		FileOutputStream fru_jpg = null;
//		try{
//		fru_jpg = new FileOutputStream("D:\\Fruit.jpg");
//		ChartUtilities.writeChartAsJPEG(fru_jpg, chart, 400, 300);
//		}finally{
//			try{
//				fru_jpg.close();
//			}catch(Exception e){}
//		}
//		
//	}
//	/**
//	 * �?个演示用的简单数据集对象
//	 * @return
//	 */
//	private static CategoryDataset getDataSet() {
//		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//		dataset.addValue(100, null, "apple");
//		dataset.addValue(200, null, "pear");
//		dataset.addValue(300, null, "grape");
//		dataset.addValue(400, null, "banana");
//		dataset.addValue(500, null, "cheer");
//		return dataset;
//	}
//	/** 
//     * 获取�?个演示用的组合数据集对象
//     * @return 
//     */ 
//     private static CategoryDataset getDataSet2() { 
//         DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
//         dataset.addValue(100, "北京", "苹果"); 
//         dataset.addValue(100, "上海", "苹果"); 
//         dataset.addValue(100, "广州", "苹果"); 
//         dataset.addValue(200, "北京", "梨子"); 
//         dataset.addValue(200, "上海", "梨子"); 
//         dataset.addValue(200, "广州", "梨子"); 
//         dataset.addValue(300, "北京", "葡萄"); 
//         dataset.addValue(300, "上海", "葡萄"); 
//         dataset.addValue(300, "广州", "葡萄"); 
//         dataset.addValue(400, "北京", "香蕉"); 
//         dataset.addValue(400, "上海", "香蕉"); 
//         dataset.addValue(400, "广州", "香蕉"); 
//         dataset.addValue(500, "北京", "荔枝"); 
//         dataset.addValue(500, "上海", "荔枝"); 
//         dataset.addValue(500, "广州", "荔枝"); 
//         return dataset; 
//     }
//
//}
