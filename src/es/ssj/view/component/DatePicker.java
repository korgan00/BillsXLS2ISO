package es.ssj.view.component;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

public class DatePicker extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JComboBox<Integer> daysCombo;
	JComboBox<String> monthCombo;
	JComboBox<Integer> yearCombo;

	int currentYear;
	int currentMonth;
	int currentDay;
	boolean isConfiguringDay;
	
	static final String[] MONTHS_NAME_ARR = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
	static ArrayList<String> MONTHS_NAME_ARRLIST = new ArrayList<String>(Arrays.asList(MONTHS_NAME_ARR));

	ArrayList<DateSelectedListener> listeners; 
	
	public DatePicker() {
		isConfiguringDay = false;
		listeners = new ArrayList<DateSelectedListener>();
		
		GregorianCalendar today = new GregorianCalendar();
		currentYear = (int)today.get(Calendar.YEAR);
		currentMonth = (int)today.get(Calendar.MONTH);
		currentDay = (int)today.get(Calendar.DAY_OF_MONTH);
		
		
		this.setLayout(new BorderLayout(10, 10));
		this.setVisible(true);
		
		daysCombo = new JComboBox<Integer>();
		monthCombo = new JComboBox<String>(MONTHS_NAME_ARR);
		yearCombo = new JComboBox<Integer>(generateYears());

		yearCombo.setSelectedItem(currentYear);
		monthCombo.setSelectedIndex(currentMonth);

		monthCombo.addActionListener(new MonthChanged(this));
		yearCombo.addActionListener(new YearChanged(this));
		daysCombo.addActionListener(new DayChanged(this));
			
		daysCombo.setPreferredSize(new Dimension(50, 20));
		yearCombo.setPreferredSize(new Dimension(75, 20));

		configDaysCombo();
		
		this.add(yearCombo, BorderLayout.WEST);
		this.add(monthCombo, BorderLayout.CENTER);
		this.add(daysCombo, BorderLayout.EAST);
	}
	
	public Calendar getDate() {
		return new GregorianCalendar(currentYear, currentMonth, currentDay);
	}
	
	public void addDateSelectedListener(DateSelectedListener listener) {
		listeners.add(listener);
	}
	public void removeDateSelectedListener(DateSelectedListener listener) {
		listeners.remove(listener);
	}
	
	protected void dateSelected() {
		Calendar selectedDate = getDate();
		for(DateSelectedListener list: listeners) {
			list.dateSelected(selectedDate);
		}
	}
	
	private Integer[] generateYears() {
		GregorianCalendar cal = new GregorianCalendar();
		int firstYear = cal.get(Calendar.YEAR);
		Integer[] years = new Integer[21];

		for(int i = 0; i < 21; i++){
			years[i] = i + firstYear;
		}
		return years;
	}

	protected void setYear(int year) {
		this.currentYear = year;
		configDaysCombo();
	}
	protected void setMonth(String month) {
		this.currentMonth = MONTHS_NAME_ARRLIST.indexOf(month);
		configDaysCombo();
	}
	protected void setDay(int day) {
		if (!isConfiguringDay) {
			this.currentDay = day;
			dateSelected();
		}
	}
	
	protected void configDaysCombo() {
		isConfiguringDay = true;
		DefaultComboBoxModel<Integer> model = (DefaultComboBoxModel<Integer>) this.daysCombo.getModel();
		
		model.removeAllElements();
		int maxDays = daysOnMonth(this.currentMonth);
		
		for (int i = 1; i <= maxDays; i++) {
			model.addElement(i);
		}

		this.daysCombo.setModel(model);

		this.currentDay = Math.min(maxDays, this.currentDay);
		this.daysCombo.setSelectedItem((Integer) this.currentDay);

		isConfiguringDay = false;
		
		dateSelected();
	}

	private Integer daysOnMonth(int month) {
		int days = 30;
		switch (month) {
			case 0:
			case 2:
			case 4:
			case 6:
			case 7:
			case 9:
			case 11:
				days = 31;
				break;
			case 3:
			case 5:
			case 8:
			case 10:
				days = 30;
				break;
			case 1:
				days = 28;
				if ( currentYear%4 == 0 && (currentYear%100 != 0 || currentYear%400 == 0) ) {
					days += 1;
				}
				break;
			default:
				days = 0;
		}
		
		return days;
	}
	
	private class YearChanged implements ActionListener {

		private DatePicker dp;
		
		public YearChanged(DatePicker dp) { 
			this.dp = dp;
		}

		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("comboBoxChanged")) {
				
				@SuppressWarnings("unchecked")
				JComboBox<Integer> src = (JComboBox<Integer>)e.getSource();
				
				dp.setYear((Integer)src.getSelectedItem());
				
			}
		}
		
	}
	private class MonthChanged implements ActionListener {

		private DatePicker dp;
		
		public MonthChanged(DatePicker dp) { 
			this.dp = dp;
		}
		
		
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("comboBoxChanged")) {
				
				@SuppressWarnings("unchecked")
				JComboBox<String> src = (JComboBox<String>)e.getSource();

				dp.setMonth((String)src.getSelectedItem());
				
			}
		}
		
	}

	private class DayChanged implements ActionListener {

		private DatePicker dp;
		
		public DayChanged(DatePicker dp) { 
			this.dp = dp;
		}

		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("comboBoxChanged")) {
				
				@SuppressWarnings("unchecked")
				JComboBox<Integer> src = (JComboBox<Integer>)e.getSource();
				
				if (src.getSelectedItem() instanceof Integer) {
					dp.setDay((Integer)src.getSelectedItem());
				}
			}
		}
		
	}
}







