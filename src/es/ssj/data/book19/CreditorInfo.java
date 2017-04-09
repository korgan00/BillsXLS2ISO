package es.ssj.data.book19;

import java.util.Calendar;

import es.ssj.data.book19.common.AlphaDataUnit;
import es.ssj.data.book19.common.NumericDataUnit;

public class CreditorInfo {

	private NumericDataUnit registryCode;
	private NumericDataUnit bookVersion;
	private NumericDataUnit dataNumber;

	private AlphaDataUnit creditorId;
	private AlphaDataUnit cif;

	private NumericDataUnit payDay;
	private Calendar payDayCalendar;
	
	private AlphaDataUnit creditorName;

	private AlphaDataUnit creditorDirection_1;
	private AlphaDataUnit creditorDirection_2;
	private AlphaDataUnit creditorDirection_3;
	private AlphaDataUnit creditorCountry;

	private AlphaDataUnit creditorAccount;
	private AlphaDataUnit creditorEntity;
	
	private AlphaDataUnit free;
	
	public CreditorInfo(int book19) {
		registryCode = new NumericDataUnit(2);
		bookVersion = new NumericDataUnit(5);
		dataNumber = new NumericDataUnit(3);

		creditorId = new AlphaDataUnit(35);
		cif = new AlphaDataUnit(9);
		payDay = new NumericDataUnit(8);
		
		creditorName = new AlphaDataUnit(70);
		creditorDirection_1 = new AlphaDataUnit(50);
		creditorDirection_2 = new AlphaDataUnit(50);
		creditorDirection_3 = new AlphaDataUnit(40);
		creditorCountry = new AlphaDataUnit(2);
		

		creditorAccount = new AlphaDataUnit(34);
		creditorEntity = new AlphaDataUnit(11);
		free = new AlphaDataUnit(301);
		
		/* PREDEFINED VALUES */
		registryCode.set(2);
		bookVersion.set(book19);
		dataNumber.set(2);
	}
	
	
	public NumericDataUnit getRegistryCode() {
		return registryCode;
	}

	public NumericDataUnit getDataNumber() {
		return dataNumber;
	}

	public AlphaDataUnit getCreditorId() {
		return creditorId;
	}

	public AlphaDataUnit getCif() {
		return cif;
	}

	public AlphaDataUnit getCreditorName() {
		return creditorName;
	}

	public AlphaDataUnit getCreditorDirection_1() {
		return creditorDirection_1;
	}

	public AlphaDataUnit getCreditorDirection_2() {
		return creditorDirection_2;
	}

	public AlphaDataUnit getCreditorDirection_3() {
		return creditorDirection_3;
	}

	public AlphaDataUnit getCreditorCountry() {
		return creditorCountry;
	}

	public AlphaDataUnit getFree() {
		return free;
	}

	public NumericDataUnit getBookVersion() {
		return bookVersion;
	}

	public NumericDataUnit getPayDay() {
		return payDay;
	}
	
	public void setPayDayCalendar(Calendar c) {
		payDayCalendar = c;
	}
	
	public Calendar getPayDayCalendar() {
		return payDayCalendar;
	}

	public AlphaDataUnit getCreditorAccount() {
		return creditorAccount;
	}

	public AlphaDataUnit getCreditorEntity() {
		return creditorEntity;
	}

	
	public String toString() {
		return registryCode.toString()
				+ bookVersion
				+ dataNumber
				+ creditorId
				+ payDay
				+ creditorName
				+ creditorDirection_1
				+ creditorDirection_2
				+ creditorDirection_3
				+ creditorCountry
				+ creditorAccount
				+ free;
	}
	
}

















