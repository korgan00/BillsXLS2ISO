package es.ssj.data.book19;

import es.ssj.data.book19.common.AlphaDataUnit;
import es.ssj.data.book19.common.NumericDataUnit;

public class CreditorTotalsByDate {

	private NumericDataUnit registryCode;

	private AlphaDataUnit creditorId;

	private NumericDataUnit payDay;
	private NumericDataUnit totalAmounts;
	private NumericDataUnit chargesCount;
	private NumericDataUnit registersTotal;

	private AlphaDataUnit free;
	
	
	public CreditorTotalsByDate() {

		registryCode = new NumericDataUnit(2);
		
		creditorId = new AlphaDataUnit(35);
		
		payDay = new NumericDataUnit(8);
		totalAmounts = new NumericDataUnit(17);
		chargesCount = new NumericDataUnit(8);
		registersTotal = new NumericDataUnit(10);
		
		free = new AlphaDataUnit(520);
		
		/* PREDEFINED VALUES */
		registryCode.set(4);
	}


	public NumericDataUnit getRegistryCode() {
		return registryCode;
	}

	public AlphaDataUnit getCreditorId() {
		return creditorId;
	}

	public NumericDataUnit getPayDay() {
		return payDay;
	}

	public NumericDataUnit getTotalAmounts() {
		return totalAmounts;
	}

	public NumericDataUnit getChargesCount() {
		return chargesCount;
	}

	public NumericDataUnit getRegistersTotal() {
		return registersTotal;
	}

	public AlphaDataUnit getFree() {
		return free;
	}
	
	public String toString() {
		return registryCode.toString() 
				+ creditorId
				+ payDay
				+ totalAmounts
				+ chargesCount
				+ registersTotal
				+ free;
	}
	
}
