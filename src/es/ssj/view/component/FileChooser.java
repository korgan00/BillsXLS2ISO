package es.ssj.view.component;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

public class FileChooser extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton openBtt;
	JTextField urlTextBox;
	OnClickEvent fileSelectorEvent;
	
	ArrayList<FileSelectedListener> listeners; 
	
	public FileChooser(String text, String dialogTitle) {
		listeners = new ArrayList<FileSelectedListener>();
		this.setLayout(new BorderLayout(10, 10));
		this.setVisible(true);
		openBtt = new JButton(text);
		urlTextBox = new JTextField(dialogTitle);
		
		urlTextBox.setEditable(false);
		
		fileSelectorEvent = new OnClickEvent(this, dialogTitle);
		openBtt.addMouseListener(fileSelectorEvent);
		urlTextBox.addMouseListener(fileSelectorEvent);

		this.add(urlTextBox, BorderLayout.CENTER);
		this.add(openBtt, BorderLayout.EAST);
	}

	public void addFileSelectedListener(FileSelectedListener fileSelListener) {
		listeners.add(fileSelListener);
	}
	public void removeFileSelectedListener(FileSelectedListener fileSelListener) {
		listeners.remove(fileSelListener);
	}
	
	protected void fileSelected(File file, File dir) {
		if (file != null) this.urlTextBox.setText(file.toString());
		
		for(FileSelectedListener list: listeners) {
			list.fileSelected(file, dir);
		}
	}
	
	private class OnClickEvent implements MouseListener {

		
		private FileChooser fc;
		private JFileChooser jfc;
		
		public OnClickEvent(FileChooser fc, String dialogTitle) {
			this.fc = fc;
		
			this.jfc = new JFileChooser();
			this.jfc.setCurrentDirectory(new java.io.File("."));
			this.jfc.setDialogTitle(dialogTitle);
			//this.urlTextBox = urlTB;
		}
		
		public void mouseClicked(MouseEvent e) {
			if (this.jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				fc.fileSelected(this.jfc.getSelectedFile(), this.jfc.getCurrentDirectory());
			} else {
				fc.fileSelected(null, null);
			}
		}

		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
