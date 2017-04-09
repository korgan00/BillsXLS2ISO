package es.ssj.view.component;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;

public class PaddingPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PaddingPanel(int hPadding, int vPadding, Component c) {
		super(new BorderLayout(hPadding, vPadding));

		super.add(c, BorderLayout.CENTER);
		this.add(new JPanel(), BorderLayout.SOUTH);
		this.add(new JPanel(), BorderLayout.NORTH);
		this.add(new JPanel(), BorderLayout.WEST);
		this.add(new JPanel(), BorderLayout.EAST);
	}
}
