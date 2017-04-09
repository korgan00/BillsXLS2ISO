package es.ssj.view.component;

import java.awt.*;

import javax.swing.*;


public class LabeledComponent  extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LabeledComponent(String text, Component c) {
		this.setLayout(new GridLayout(2, 1));
		this.setVisible(true);

		this.add(new JLabel(text));
		this.add(c);
	}

}
