package kfk.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import kfk.java.bean.ActiveUserBean;
import kfk.main.FADemo;

public class AnalyzeUI {

	public static void main(String[] args) {
		AnalyzeUI AU = new AnalyzeUI();
		AU.showFrame();
	}

	JFrame frame;
	JButton button;
	JComboBox versionCombo;
	JTextField apiKey, starDate, endDate, interval, inputDir, outputDir;

	public AnalyzeUI() {

		button = new JButton("analyze");
		apiKey = new JTextField("KQSV5WXHB4TCMVC4D3GX", 20);
		starDate = new JTextField("2014-08-06", 20);
		endDate = new JTextField("2014-08-06", 20);
		interval = new JTextField("2014_08_06-2014_08_07", 20);
		inputDir = new JTextField("csv的下载路径", 20);
		outputDir = new JTextField("生成Bug统计的路径", 20);

		JPanel main_panel = new JPanel();	
		JPanel p_center = new JPanel();
		p_center.setLayout(new BoxLayout(p_center,BoxLayout.Y_AXIS));
		p_center.add(getLabel("version"));
		p_center.add(getVersionCombo());
		p_center.add(getLabel("apiKey"));
		p_center.add(apiKey);
		p_center.add(getLabel("starDate"));
		p_center.add(starDate);
		p_center.add(getLabel("endDate"));
		p_center.add(endDate);
		p_center.add(getLabel("interval"));
		p_center.add(interval);
		p_center.add(getLabel("csv输入路径"));
		p_center.add(inputDir);
		p_center.add(getLabel("Bug生成路径"));
		p_center.add(outputDir);
		main_panel.add(getLabel("请按照格式填写，确认无误后点击 '" + button.getLabel() + "' :)"),BorderLayout.NORTH);
		main_panel.add(p_center,BorderLayout.CENTER);
		main_panel.add(button, BorderLayout.NORTH);

		frame = new JFrame("FLurry Analyze");
		frame.setContentPane(main_panel);
		frame.setSize(320, 480);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		ActionLisen ln = new ActionLisen();
		button.addActionListener(ln);
		versionCombo.addActionListener(ln);
	}

	public JLabel getLabel(String text) {
		JLabel label = new JLabel(text);
		return label;
	}

	public JComboBox getVersionCombo() {
		versionCombo = new JComboBox();
		versionCombo.addItem("4.0");// KQSV5WXHB4TCMVC4D3GX
		versionCombo.addItem("4.0.3");// J85WBFF7DNYH3N59RGKW
		versionCombo.addItem("4.0.5");// CWV3S4TYHR93Z9TYPWR9
		versionCombo.addItem("4.0.6");//84SSPWJ5JY7NYG2T8F79
		versionCombo.addItem("4.0_for_Web");//37N3P96QT7VK3MJ8VZG6
		return versionCombo;
	}

	public void showFrame() {
		frame.setVisible(true);
	}

	/**
	 * 实现ActionListener接口
	 * 
	 * @author ThomasKe
	 * 
	 */
	public class ActionLisen implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == versionCombo) {
				if (versionCombo.getSelectedItem().equals("4.0"))
					apiKey.setText("KQSV5WXHB4TCMVC4D3GX");
				if (versionCombo.getSelectedItem().equals("4.0.3"))
					apiKey.setText("J85WBFF7DNYH3N59RGKW");
				if (versionCombo.getSelectedItem().equals("4.0.5"))
					apiKey.setText("CWV3S4TYHR93Z9TYPWR9");
				if (versionCombo.getSelectedItem().equals("4.0.6"))
					apiKey.setText("84SSPWJ5JY7NYG2T8F79");
				if (versionCombo.getSelectedItem().equals("4.0_for_Web"))
					apiKey.setText("37N3P96QT7VK3MJ8VZG6");
			}
			if (e.getSource() == button) {
				ActiveUserBean bean = new ActiveUserBean();
				bean.setApiKey(apiKey.getText());
				bean.setStartDate(starDate.getText());
				bean.setEndDate(endDate.getText());
				bean.setVersionName(versionCombo.getSelectedItem().toString());
				bean.setInterval(interval.getText());
				bean.setInputDir(inputDir.getText());
				bean.setOutputDir(outputDir.getText());
				FADemo faDemo = new FADemo(bean);
				System.out.println("FADemo:Now, Let's go !!!");
			}
		}

	}
}