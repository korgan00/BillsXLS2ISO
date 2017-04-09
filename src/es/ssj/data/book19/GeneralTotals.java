package es.ssj.data.book19;

import es.ssj.data.book19.common.AlphaDataUnit;
import es.ssj.data.book19.common.NumericDataUnit;

public class GeneralTotals {

	private NumericDataUnit registryCode;


	private NumericDataUnit totalAmounts;
	private NumericDataUnit chargesCount;
	private NumericDataUnit registersTotal;

	private AlphaDataUnit free;
	
	
	public GeneralTotals() {

		registryCode = new NumericDataUnit(2);
		
		
		totalAmounts = new NumericDataUnit(17);
		chargesCount = new NumericDataUnit(8);
		registersTotal = new NumericDataUnit(10);
		
		free = new AlphaDataUnit(563);
		
		/* PREDEFINED VALUES */
		registryCode.set(99);
	}


	public NumericDataUnit getRegistryCode() {
		return registryCode;
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
				+ totalAmounts
				+ chargesCount
				+ registersTotal
				+ free;
	}
	
}
