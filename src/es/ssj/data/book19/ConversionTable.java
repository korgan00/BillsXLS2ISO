package es.ssj.data.book19;

import java.util.HashMap;

public class ConversionTable {
	private static HashMap<Character, Integer>  conversionTable = null;
	
	public static HashMap<Character, Integer> instance() {
		if (conversionTable == null) {
			conversionTable = new HashMap<Character, Integer>();

			conversionTable.put('A', 10);
			conversionTable.put('B', 11);
			conversionTable.put('C', 12);
			conversionTable.put('D', 13);
			conversionTable.put('E', 14);
			conversionTable.put('F', 15);
			conversionTable.put('G', 16);
			conversionTable.put('H', 17);
			conversionTable.put('I', 18);
			conversionTable.put('J', 19);
			conversionTable.put('K', 20);
			conversionTable.put('L', 21);
			conversionTable.put('M', 22);
			conversionTable.put('N', 23);
			conversionTable.put('O', 24);
			conversionTable.put('P', 25);
			conversionTable.put('Q', 26);
			conversionTable.put('R', 27);
			conversionTable.put('S', 28);
			conversionTable.put('T', 29);
			conversionTable.put('U', 30);
			conversionTable.put('V', 31);
			conversionTable.put('W', 32);
			conversionTable.put('X', 33);
			conversionTable.put('Y', 34);
			conversionTable.put('Z', 35);
		}
		return conversionTable;
	}
}
