package es.ssj.data.book19;

import java.util.Calendar;
import java.util.GregorianCalendar;

import es.ssj.data.book19.common.AlphaDataUnit;
import es.ssj.data.book19.common.NumericDataUnit;

public class DebtorInfo {

	private NumericDataUnit registryCode;
	private NumericDataUnit bookVersion;
	private NumericDataUnit dataNumber;
	
	private AlphaDataUnit chargeRef;
	private String uniqueRefPrefix;
	private String uniqueRefCore;
	
	private AlphaDataUnit chargeSequence;
	private AlphaDataUnit purposeCategory;
	
	private NumericDataUnit chargeAmount;
	private NumericDataUnit orderSignDate;
	private Calendar orderSignCalendar;
	
	private AlphaDataUnit debtorEntity;
	private AlphaDataUnit debtorName;
	private AlphaDataUnit debtorDirection_1;
	private AlphaDataUnit debtorDirection_2;
	private AlphaDataUnit debtorDirection_3;
	private AlphaDataUnit debtorCountry;
	private AlphaDataUnit debtorIdType;
	private AlphaDataUnit debtorId;
	private AlphaDataUnit debtorId_CodeEmitter;
	private AlphaDataUnit debtorAccountId;
	private AlphaDataUnit debtorAccount;
	
	private AlphaDataUnit chargePurpose;
	private AlphaDataUnit concept;
	private AlphaDataUnit free;
	
	
	public DebtorInfo(int book19) {

		registryCode = new NumericDataUnit(2);
		bookVersion = new NumericDataUnit(5);
		dataNumber = new NumericDataUnit(3);

		chargeRef = new AlphaDataUnit(35);
		uniqueRefPrefix = "";
		uniqueRefCore = "";
		
		chargeSequence = new AlphaDataUnit(4);
		purposeCategory = new AlphaDataUnit(4);
		
		chargeAmount = new NumericDataUnit(11);
		orderSignDate = new NumericDataUnit(8);
		
		debtorEntity = new AlphaDataUnit(11);
		debtorName = new AlphaDataUnit(70);
		debtorDirection_1 = new AlphaDataUnit(50);
		debtorDirection_2 = new AlphaDataUnit(50);
		debtorDirection_3 = new AlphaDataUnit(40);
		debtorCountry = new AlphaDataUnit(2);
		debtorIdType = new AlphaDataUnit(1);
		debtorId = new AlphaDataUnit(36);
		debtorId_CodeEmitter = new AlphaDataUnit(35);
		debtorAccountId = new AlphaDataUnit(1);
		debtorAccount = new AlphaDataUnit(34);
		
		chargePurpose = new AlphaDataUnit(4);
		concept = new AlphaDataUnit(140);
		free = new AlphaDataUnit(19);
		
		/* PREDEFINED VALUES */
		registryCode.set(3);
		bookVersion.set(book19);
		dataNumber.set(3);
		

		chargeSequence.set("RCUR");
		purposeCategory.set("TRAD");
		orderSignCalendar = new GregorianCalendar(2014, GregorianCalendar.JANUARY, 1, 12, 0, 0);
		orderSignDate.set(calendarToNumber(orderSignCalendar));
	}
	
	private static long calendarToNumber(Calendar cal) {
		return cal.get(Calendar.YEAR) * 10000 + (cal.get(Calendar.MONTH) + 1) * 100 + cal.get(Calendar.DATE);
	}
	
	
	public NumericDataUnit getRegistryCode() {
		return registryCode;
	}


	public NumericDataUnit getBookVersion() {
		return bookVersion;
	}


	public NumericDataUnit getDataNumber() {
		return dataNumber;
	}


	public AlphaDataUnit getChargeRef() {
		return chargeRef;
	}

	public void setUniqueRefPrefix(String pre) {
		this.uniqueRefPrefix = pre;
	}
	public void setUniqueRefCore(String pre) {
		this.uniqueRefCore = pre;
	}

	public String getUniqueRefPrefix() {
		return uniqueRefPrefix;
	}
	public String getUniqueRefCore() {
		return uniqueRefCore;
	}


	public AlphaDataUnit getChargeSequence() {
		return chargeSequence;
	}


	public AlphaDataUnit getPurposeCategory() {
		return purposeCategory;
	}


	public NumericDataUnit getChargeAmount() {
		return chargeAmount;
	}


	public NumericDataUnit getOrderSignDate() {
		return orderSignDate;
	}


	public Calendar getOrderSignCalendar() {
		return orderSignCalendar;
	}


	public AlphaDataUnit getDebtorEntity() {
		return debtorEntity;
	}


	public AlphaDataUnit getDebtorName() {
		return debtorName;
	}


	public AlphaDataUnit getDebtorDirection_1() {
		return debtorDirection_1;
	}


	public AlphaDataUnit getDebtorDirection_2() {
		return debtorDirection_2;
	}


	public AlphaDataUnit getDebtorDirection_3() {
		return debtorDirection_3;
	}


	public AlphaDataUnit getDebtorCountry() {
		return debtorCountry;
	}


	public AlphaDataUnit getDebtorIdType() {
		return debtorIdType;
	}


	public AlphaDataUnit getDebtorId() {
		return debtorId;
	}


	public AlphaDataUnit getDebtorId_CodeEmitter() {
		return debtorId_CodeEmitter;
	}


	public AlphaDataUnit getDebtorAccountId() {
		return debtorAccountId;
	}


	public AlphaDataUnit getDebtorAccount() {
		return debtorAccount;
	}


	public AlphaDataUnit getChargePurpose() {
		return chargePurpose;
	}


	public AlphaDataUnit getConcept() {
		return concept;
	}


	public AlphaDataUnit getFree() {
		return free;
	}


	public String toString() {
		AlphaDataUnit uniqueRef = new AlphaDataUnit(35, uniqueRefPrefix + uniqueRefCore);
		
		String s = registryCode.toString()
				+ bookVersion
				+ dataNumber
				+ chargeRef
				+ uniqueRef
				+ chargeSequence
				+ purposeCategory
				+ chargeAmount
				+ orderSignDate
				+ debtorEntity
				+ debtorName
				+ debtorDirection_1
				+ debtorDirection_2
				+ debtorDirection_3
				+ debtorCountry
				+ debtorIdType
				+ debtorId
				+ debtorId_CodeEmitter
				+ debtorAccountId
				+ debtorAccount
				+ chargePurpose
				+ concept
				+ free;
	
		return s;
	}
	
}
