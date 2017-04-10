package es.ssj.view;

import javax.swing.*;

import es.ssj.view.component.*;
import es.ssj.data.book19.*;
import es.ssj.data.book19.externalInfo.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;


public class MainView extends JFrame implements DateSelectedListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel mainPanel;
	JPanel root;
	FileChooser creditorsFileChooser;
	FileChooser debtorsFileChooser;
	JPanel fileNamePanel;
	JTextField fileNameTxtBox;
	JCheckBox isXMLCheckBox;
	DatePicker paydayPicker;
	JButton aceptBtt;
	JLabel infoLabel;

	File creditorsFile;
	File debtorsFile;
	File debtorsDir;
	String newFileName;
	Calendar payday;
	
	
	public MainView() {
		super("XLS a 'Cuaderno 19'");

		creditorsFile = null;
		debtorsFile = null;
		debtorsDir = null;
		newFileName = null;
		payday = null;
	}

    public void initialize() {
        this.setBounds(100, 100, 400, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		mainPanel = new JPanel(new GridLayout(7, 1, 20, 20));
		fileNamePanel = new JPanel(new BorderLayout(5, 20));
		root = new PaddingPanel(20, 20, mainPanel);

		debtorsFileChooser = new FileChooser("...", "Elige fichero de adeudos");
		creditorsFileChooser = new FileChooser("...", "Elige fichero de cobros");
		fileNameTxtBox = new JTextField("");
		paydayPicker = new DatePicker();
		isXMLCheckBox = new JCheckBox("XML");
		aceptBtt = new JButton("Generar");
		infoLabel = new JLabel();		

		isXMLCheckBox.setSelected(true);
		creditorsFileChooser.addFileSelectedListener(new OnCreditorsFileSelectionChange(this));
		debtorsFileChooser.addFileSelectedListener(new OnDebtorsFileSelectionChange(this));
		paydayPicker.addDateSelectedListener(this);
		payday = paydayPicker.getDate();
		OnFileNameChange listener = new OnFileNameChange(this);
		fileNameTxtBox.addActionListener(listener);
		fileNameTxtBox.addKeyListener(listener);
		aceptBtt.setEnabled(false);
		aceptBtt.addMouseListener(new OnGenerateFile(this));

		fileNamePanel.add(new LabeledComponent("Nombre del archivo", fileNameTxtBox), BorderLayout.CENTER);
		fileNamePanel.add(new LabeledComponent("", isXMLCheckBox), BorderLayout.EAST);
		
		mainPanel.add(new LabeledComponent("Fichero de adeudos", debtorsFileChooser));
		mainPanel.add(new LabeledComponent("Fichero de cobros", creditorsFileChooser));
		mainPanel.add(fileNamePanel);	
		mainPanel.add(new LabeledComponent("Fecha de cobro", paydayPicker));
		mainPanel.add(infoLabel);
		mainPanel.add(aceptBtt);

		this.setMinimumSize(new Dimension(350, 500));
		this.add(root);
		this.setVisible(true);
    }
    
    private void updateState() {
    	if (creditorsFile != null && debtorsDir != null && debtorsFile != null && newFileName != null && !newFileName.equals("")) {
    		aceptBtt.setEnabled(true);
    	} else {
    		aceptBtt.setEnabled(false);
    	}
    }

    protected void creditorsFileSelected(File file, File dir) {
    	this.creditorsFile = file;
    	updateState();
    }
    
    protected void debtorsFileSelected(File file, File dir) {
    	this.debtorsFile = file;
    	this.debtorsDir = dir;
    	updateState();
    }

    public void dateSelected(Calendar c) {
    	this.payday = c;
    	updateState();
	}

    public void fileNameSelected(String name) {
    	if(!name.equals("")) {
    		this.newFileName = name;
    	} else {
    		this.newFileName = null;
    	}
    	updateState();
	}
    
	public void generateFile() {
		Book19 book = new Book19(Book19.BOOK_19_15);
		GregorianCalendar cal;
		File outputFile;
		String extension = isXMLCheckBox.isSelected() ? ".xml" : ".txt";
		
		try {
			
			outputFile = new File(this.debtorsDir.getAbsolutePath() + "/" + this.newFileName + extension);
			outputFile.createNewFile();
			cal = new GregorianCalendar();
			cal.setTime(new Date(outputFile.lastModified()));
			
		} catch (IOException ex) {
			error("Error al intentar crear el achivo de datos.");
			
			//ex.printStackTrace();
			return;
		}

		try {
			ExcelManager.readCreditorInfo(this.creditorsFile.getAbsolutePath(), book, this.newFileName, this.payday, cal);
		} catch (XLSReadException ex) {
			error("Error al leer el xls de cobros.\n\r " + ex.getErrorDetails());
			//ex.printStackTrace();
			return;
		}
			
		
		try {
			ExcelManager.readDebtorsInfo(this.debtorsFile.getAbsolutePath(), book);
		} catch (XLSReadException ex) {
			error("Error al leer el xls de adeudos.\n\r " + ex.getErrorDetails());
			//ex.printStackTrace();
			return;
		}

		try {
			PrintWriter out = new PrintWriter(outputFile, "UTF-8");
			if (isXMLCheckBox.isSelected()) {
				out.print(book.toXML());
			} else {
				out.print(book.toString());
			}
			//out.print(book.toString());
			out.flush();
			out.close();
		} catch (IOException ex) {
			error("Error en la escritura del archivo de datos.");
			//ex.printStackTrace();
			return;
		}

		try {
			ExcelManager.writeDebtorsReference(this.creditorsFile.getAbsolutePath(), book.getDebtorsInfo().size());
		} catch (IOException ex) {
			warn("Exito! Pero no se ha guardado la referencia de cobro.");
			//ex.printStackTrace();
			return;
		}
		fine("El archivo se ha creado con exito.");
	}

	private void fine(String msg) {
		infoLabel.setVisible(false);
		infoLabel.setForeground(Color.decode("0x009900"));
		infoLabel.setText(msg);
		infoLabel.setVisible(true);
	}
	
	private void warn(String msg) {
		infoLabel.setVisible(false);
		infoLabel.setForeground(Color.decode("0xBB9900"));
		infoLabel.setText(msg);
		infoLabel.setVisible(true);
	}
	
	private void error(String msg) {
		infoLabel.setVisible(false);
		infoLabel.setForeground(Color.decode("0xDD0000"));
		infoLabel.setText(msg);
		infoLabel.setVisible(true);
	}
	
    private class OnCreditorsFileSelectionChange implements FileSelectedListener {

    	private MainView mainView;
    	
    	public OnCreditorsFileSelectionChange(MainView mainView) {
    		this.mainView = mainView;
    	}
    	
		public void fileSelected(File file, File dir) {
			this.mainView.creditorsFileSelected(file, dir);
		}
    	
    }

    private class OnDebtorsFileSelectionChange implements FileSelectedListener {

    	private MainView mainView;
    	
    	public OnDebtorsFileSelectionChange(MainView mainView) {
    		this.mainView = mainView;
    	}
    	
		public void fileSelected(File file, File dir) {
			this.mainView.debtorsFileSelected(file, dir);
		}
    	
    }
    

    private class OnFileNameChange implements ActionListener, KeyListener {

    	private MainView mainView;
    	
    	public OnFileNameChange(MainView mainView) {
    		this.mainView = mainView;
    	}
    	
    	private void genericAction(JTextField src) {
			mainView.fileNameSelected(src.getText());
    	}
    	
		public void actionPerformed(ActionEvent ev) {
			genericAction( (JTextField)ev.getSource());
		}

		public void keyPressed(KeyEvent ev) {
			genericAction( (JTextField)ev.getSource());
		}

		public void keyReleased(KeyEvent ev) {
			genericAction( (JTextField)ev.getSource());
		}

		public void keyTyped(KeyEvent ev) {
			genericAction( (JTextField)ev.getSource());
		}
    	
    }
    

    private class OnGenerateFile implements MouseListener {

    	private MainView mainView;
    	
    	public OnGenerateFile(MainView mainView) {
    		this.mainView = mainView;
    	}
    	
		public void mouseReleased(MouseEvent e) {}			
		public void mousePressed(MouseEvent e) {}			
		public void mouseExited(MouseEvent e) {}			
		public void mouseEntered(MouseEvent e) {}
		
		public void mouseClicked(MouseEvent ev) {
			this.mainView.generateFile();
		}
    }
}