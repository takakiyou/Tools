package kfk.ui;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

import java.awt.*;

public class AcquireFileDir extends JPanel {
	private JFileChooser dir;
	private String dirStr;
	
	public  void openDialog(int i) {
		dir = new JFileChooser();
		String temp;
		int returnVal = 0;
		switch (i) {
		case 0:
		returnVal = dir.showOpenDialog(AcquireFileDir.this);
		break;
		case 1:
		returnVal = dir.showSaveDialog(AcquireFileDir.this);
		break;
		}
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			 System.out.println("You chose to open this file: " +
					 dir.getSelectedFile().getParent());
			 temp = dir.getSelectedFile().getParent();
			 setDir(temp);
		}
	}
	
	public  String getDir() {
		return  dirStr;
	}
	public void setDir(String dir) {
		this.dirStr = dir;
	}
}  
