package kfk.out.excel;

import java.io.File;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import kfk.java.bean.ActiveUserBean;

public class ExportExcel {

	WritableWorkbook workbook;
	WritableSheet sheet_1;
	WritableSheet sheet_2;

	public void makeBugRate(String[][] file, ActiveUserBean au) {
		try {
			File excel = new File("/Users/ThomasKe/Documents/1mobile/BugDaily/Bug_(" + au.getVersionName() + "_" + au.getStartDate() + ").xls");
			workbook = Workbook.createWorkbook(excel);
			sheet_1 = workbook.createSheet("RugRate", 0);
			sheet_2 = workbook.createSheet("Crash", 1);

			for (int i = 0; i < file.length; i++) {
				if (i >= 3)
					for (int j = 0; j < file[i].length; j++)
						sheet_2.addCell(new Label(j, i - 3, file[i][j]));
				else
					for (int j = 0; j < file[i].length; j++)
						sheet_1.addCell(new Label(j, i, file[i][j]));
			}
			workbook.write();
			workbook.close();
			System.out.println("write succes!");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
}
