package es.ssj.data.book19;

import java.util.*;

import es.ssj.data.book19.xml.Book19ToXML;

public class Book19 {

	public static final int BOOK_19_14 = 19143;
	public static final int BOOK_19_15 = 19154;
	
	// HEADER
	private PresenterInfo presenterInfo;
	private CreditorInfo creditorInfo;
	// BODY
	private ArrayList<DebtorInfo> debtorsInfo;
	// FOOTER
	private CreditorTotalsByDate totalsByDate;
	private CreditorTotals totals;
	private GeneralTotals generalTotals;
	private String referenceStart;
	private int paymentRef;
	private int book19_spec;
	
	public Book19(int book19spec) {
		this.book19_spec = book19spec;
		debtorsInfo = new ArrayList<DebtorInfo>();
		
		this.totalsByDate = new CreditorTotalsByDate();
		this.totals = new CreditorTotals();
		this.generalTotals = new GeneralTotals();
	}

	public PresenterInfo getPresenterInfo() {
		return presenterInfo;
	}

	public CreditorInfo getCreditorInfo() {
		return creditorInfo;
	}

	public ArrayList<DebtorInfo> getDebtorsInfo() {
		return debtorsInfo;
	}

	public CreditorTotalsByDate getTotalsByDate() {
		return totalsByDate;
	}

	public CreditorTotals getTotals() {
		return totals;
	}

	public GeneralTotals getGeneralTotals() {
		return generalTotals;
	}

	public String getReferenceStart() {
		return referenceStart;
	}

	public int getPaymentRef() {
		return paymentRef;
	}
	
	public void setInfo(int paymentRef, String refStart, String cif, String name, 
			String fileName, String bic, String iban, Calendar payDayCalendar, Calendar fileCreationDate) {

		this.referenceStart = refStart;
		this.paymentRef = paymentRef;
		String id = calculateId(cif);
		//Calendar cal = Calendar.getInstance();
		long date = calendarToInt(fileCreationDate);
		long payDay = calendarToInt(payDayCalendar);
		
		String hhmmss = "" + (fileCreationDate.get(Calendar.HOUR) * 10000l + 
						fileCreationDate.get(Calendar.MINUTE) * 100l + 
						fileCreationDate.get(Calendar.SECOND));
		if (fileCreationDate.get(Calendar.HOUR) < 10) {
			hhmmss = "0" + hhmmss;
		}
		String hourWithMillis = hhmmss + String.format("%05d", (fileCreationDate.get(Calendar.MILLISECOND)));
		// HEADER
		CreditorInfo ci = new CreditorInfo(this.book19_spec);

		ci.getCreditorId().set(id);
		ci.getCif().set(cif);
		ci.getPayDay().set(payDay); // TODO: Preguntar fecha de cobro
		ci.setPayDayCalendar(payDayCalendar);
		
		ci.getCreditorName().set(name);
		ci.getCreditorAccount().set(iban);
		ci.getCreditorEntity().set(bic);
		
		PresenterInfo pi = new PresenterInfo(this.book19_spec);
		pi.getPresenterId().set(id);
		pi.getPresenterName().set(name);
		pi.getFileCreationDate().set(date);
		pi.setFileCreationDateGregorianCalendar(fileCreationDate);
		pi.getFileId().set("PRE" + date + hourWithMillis + fileName);
		pi.getReceptorEntity().set(Integer.parseInt(iban.substring(4, 8))); // Variable
		pi.getReceptorOffice().set(Integer.parseInt(iban.substring(8, 12))); // Variable
		
		
		this.creditorInfo = ci;		
		this.presenterInfo = pi;
		
		// FOOTER
		this.totalsByDate.getCreditorId().set(id);
		this.totalsByDate.getPayDay().set(payDay); // TODO: Preguntar fecha de cobro
		
		this.totals.getCreditorId().set(id);
		
	}
	
	public void addDebtor(double chargeAmount, String bic, String accountOwner, 
			String iban, String childName, String level, String concept, String ref) {
		
		DebtorInfo di = new DebtorInfo(this.book19_spec);
		String[] splitedName = accountOwner.split(",");
		String orderedName = splitedName[1].trim() + " " + splitedName[0].trim();
		
		di.getChargeRef().set("123456789"); // TODO: 2000++
		di.setUniqueRefCore(ref);//getUniqueRef().set("123456789"); // TODO: CSJ + REF
		
		di.getChargeAmount().set(Math.round(chargeAmount*100 + 0.1));
		di.getDebtorEntity().set(bic);
		di.getDebtorName().set(orderedName);

		di.getDebtorAccountId().set("A");
		di.getDebtorAccount().set(iban);
		di.getConcept().set(String.format("%s  %s  %s", childName, level, concept));
		
		this.debtorsInfo.add(di);
	}
	
	public void processDebtorsData() {
		long totalAmounts = 0;
		
		for (DebtorInfo currDebtor : debtorsInfo) {
			paymentRef = (paymentRef + 1) % 10000;
			currDebtor.getChargeRef().set("" + paymentRef);
			currDebtor.setUniqueRefPrefix(referenceStart);
			totalAmounts += currDebtor.getChargeAmount().get();
		}
		
		totalsByDate.getTotalAmounts().set(totalAmounts);
		totalsByDate.getChargesCount().set(debtorsInfo.size());
		totalsByDate.getRegistersTotal().set(debtorsInfo.size() + 2);

		totals.getTotalAmounts().set(totalAmounts);
		totals.getChargesCount().set(debtorsInfo.size());
		totals.getRegistersTotal().set(debtorsInfo.size() + 3);
		
		generalTotals.getTotalAmounts().set(totalAmounts);
		generalTotals.getChargesCount().set(debtorsInfo.size());
		generalTotals.getRegistersTotal().set(debtorsInfo.size() + 5);
	}
	
	
	private String calculateId(String cif) {
		String controlDigitsAux = cif + "ES00";
		long controlDigits = 0;
		
		for(int i = 0; i < controlDigitsAux.length(); i++) {
			char ch = controlDigitsAux.charAt(i);
			
			if (Character.isDigit(ch)) {
				controlDigits *= 10;
				controlDigits += Integer.parseInt(ch + "");
			} else if (ConversionTable.instance().containsKey(ch)) {
				controlDigits *= 100;
				controlDigits += ConversionTable.instance().get(ch);
			}
			
		}
		
		controlDigits = 98 - (controlDigits % 97);
		
		return "ES" + controlDigits + "000" + cif;
	}
	
	public String toString() {
		String ln = "\r\n";//System.getProperty( "line.separator" );
		String s = this.presenterInfo + ln + this.creditorInfo + ln;

		for (DebtorInfo currDebtor : debtorsInfo) { s += currDebtor + ln; }
		
		s += totalsByDate + ln;
		s += totals + ln;
		s += generalTotals;
		
		return s;
	}
	

	public String toXML() {
		return Book19ToXML.translate(this);
	}
	
	private int calendarToInt(Calendar c) {
		return c.get(Calendar.YEAR) * 10000 + (c.get(Calendar.MONTH) + 1) * 100 + c.get(Calendar.DAY_OF_MONTH);
	}
}
