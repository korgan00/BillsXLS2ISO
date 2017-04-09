package es.ssj.data.book19.xml;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import es.ssj.data.book19.Book19;
import es.ssj.data.book19.CreditorInfo;
import es.ssj.data.book19.DebtorInfo;
import es.ssj.data.book19.PresenterInfo;
import es.ssj.data.book19.common.NumericDataUnit;

public class Book19ToXML {

	public static String translate(Book19 book) {
		String xml = GroupHeader(book).toString() + PaymentInfoTag(book).toString();
		
		return Book19XMLWrap(xml);
	}
	
	private static String Book19XMLWrap (String content) {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + Tag.endl
				+ "<Document xmlns=\"urn:iso:std:iso:20022:tech:xsd:pain.008.001.02\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" +  Tag.endl
				+ "<CstmrDrctDbtInitn>" +  Tag.endl
				+ content
				+ "</CstmrDrctDbtInitn>" +  Tag.endl
				+ "</Document>";
	}
	
	private static Tag PaymentInfoTag(Book19 book) {
		ArrayList<Tag> tags = PaymentInfoHeader(book); 
		tags.addAll(DebtorsInfoXML(book.getDebtorsInfo()));
		Tag[] tagsArr = new Tag[tags.size()];
		tagsArr = tags.toArray(tagsArr);
		return new Tag("PmtInf" , tagsArr);
	}

	// From presenter
	private static Tag GroupHeader(Book19 book) {
		PresenterInfo p = book.getPresenterInfo();
		
		return new Tag("GrpHdr",
						new Tag("MsgId", p.getFileId()),
						new Tag("CreDtTm", getISO8601StringForDate(p.getFileCreationDateGregorianCalendar().getTime())),
						new Tag("NbOfTxs", book.getDebtorsInfo().size()),
						new Tag("CtrlSum", book.getTotalsByDate().getTotalAmounts().get()/100.0),
						new Tag("InitgPty", 
								new Tag("Nm", p.getPresenterName()),
								new Tag("Id", new Tag("OrgId", new Tag("Othr",
										new Tag("Id", p.getPresenterId()),
										new Tag("SchmeNm", new Tag("Cd", "COR1")/*, Tag("Prtry", "SEPA")*/)
					  		))) 
						)
				    );
	}

	private static ArrayList<Tag> PaymentInfoHeader(Book19 book) {
		CreditorInfo c = book.getCreditorInfo();
		PresenterInfo p = book.getPresenterInfo();
		String paymentInfoId = (c.getCif().toString() + c.getPayDay() + p.getFileId()).substring(0, 35);
		
		return  new ArrayList<Tag>(Arrays.asList(
					new Tag("PmtInfId", paymentInfoId),
							new Tag("PmtMtd", "DD"),
							new Tag("NbOfTxs", book.getDebtorsInfo().size()),
							new Tag("CtrlSum", book.getTotalsByDate().getTotalAmounts().get()/100.0),
							new Tag("PmtTpInf", 
									new Tag("SvcLvl", new Tag("Cd", "SEPA")),
									new Tag("LclInstrm", new Tag("Cd", "COR1")),
									new Tag("SeqTp", "RCUR")
							),
							new Tag("ReqdColltnDt", getISODate(c.getPayDayCalendar().getTime())),
							new Tag("Cdtr", 
								new Tag("Nm", c.getCreditorName()),
								new Tag("PstlAdr",
									new Tag("Ctry", "ES")/*,
									new Tag("AdrLine", c.getCreditorDirection_1()),
									new Tag("AdrLine", c.getCreditorDirection_2()),
									new Tag("AdrLine", c.getCreditorDirection_3())*/
								)
							),
							new Tag("CdtrAcct", 
									new Tag("Id", new Tag("IBAN", c.getCreditorAccount())),
									new Tag("Ccy", "EUR")
							),
							new Tag("CdtrAgt", new Tag("FinInstnId", new Tag("BIC", c.getCreditorEntity()))),
							new Tag("ChrgBr", "SLEV"),
							new Tag("CdtrSchmeId",
									new Tag("Id", new Tag("PrvtId", new Tag("Othr", 
											new Tag("Id", c.getCreditorId()), 
											new Tag("SchmeNm", new Tag("Prtry", "SEPA"))
									)))
							)
							));
		
		/*
	      <PmtInfId>R4800660E20161112201611101305006894</PmtInfId>
	      <PmtMtd>DD</PmtMtd>
	      <NbOfTxs>204</NbOfTxs>
	      <CtrlSum>12822.19</CtrlSum>
	      <PmtTpInf>
	        <SvcLvl>
	          <Cd>SEPA</Cd>
	        </SvcLvl>
	        <LclInstrm>
	          <Cd>COR1</Cd>
	        </LclInstrm>
	        <SeqTp>RCUR</SeqTp>
	      </PmtTpInf>
	      <ReqdColltnDt>2016-11-12</ReqdColltnDt>
	      <Cdtr>
	        <Nm>COLEGIO NUESTRA SEÑORA DE BEGOÑA</Nm>
	        <PstlAdr>
	          <Ctry>ES</Ctry>
	          <AdrLine>ARTEAGABEITIA, 33</AdrLine>
	          <AdrLine>BARAKALDO</AdrLine>
	        </PstlAdr>
	      </Cdtr>
	      <CdtrAcct>
	        <Id>
	          <IBAN>ES2100750296380600002677</IBAN>
	        </Id>
	        <Ccy>EUR</Ccy>
	      </CdtrAcct>
	      <CdtrAgt>
	        <FinInstnId>
	          <BIC>POPUESMM</BIC>
	        </FinInstnId>
	      </CdtrAgt>
	      <ChrgBr>SLEV</ChrgBr>
	      <CdtrSchmeId>
	        <Id>
	          <PrvtId>
	            <Othr>
	              <Id>ES84500R4800660E</Id>
	              <SchmeNm>
	                <Prtry>SEPA</Prtry>
	              </SchmeNm>
	            </Othr>
	          </PrvtId>
	        </Id>
	      </CdtrSchmeId>
		*/
		// return null;
	}
	
	private static ArrayList<Tag> DebtorsInfoXML(ArrayList<DebtorInfo> debtorsInfo) {
		ArrayList<Tag> tags = new ArrayList<Tag>();
		for(DebtorInfo d : debtorsInfo) { tags.add(DebtorInfoXML(d)); }
		return tags;
	}
	

	private static Tag DebtorInfoXML(DebtorInfo debtorInfo) {
		NumericDataUnit chargeRefZeroFilled = new NumericDataUnit(12);
		Tag instdAmt = new Tag("InstdAmt", debtorInfo.getChargeAmount().get()/100.0);
		instdAmt.attributes().put("Ccy", "EUR");
		
		chargeRefZeroFilled.set(Integer.parseInt(debtorInfo.getChargeRef().toString().trim()));
		return  new Tag("DrctDbtTxInf", 
					new Tag("PmtId", new Tag("EndToEndId", debtorInfo.getChargeRef())),
							instdAmt,
							new Tag("DrctDbtTx", new Tag("MndtRltdInf", 
									new Tag("MndtId", debtorInfo.getChargeRef()), 
									new Tag("DtOfSgntr", getISODate(debtorInfo.getOrderSignCalendar().getTime()))
							)),
							new Tag("DbtrAgt", new Tag("FinInstnId", 
									new Tag("BIC", debtorInfo.getDebtorEntity())
							)),
							new Tag("Dbtr", 
									new Tag("Nm", debtorInfo.getDebtorName()), 
									new Tag("Id", new Tag("PrvtId", new Tag("Othr", 
											new Tag("Id", chargeRefZeroFilled)
									)))
							),
							new Tag("DbtrAcct", new Tag("Id", 
									new Tag("IBAN", debtorInfo.getDebtorAccount())
							)),
							new Tag("RmtInf", 
									new Tag("Ustrd", debtorInfo.getConcept())
							)
						);
		/*
		  <DrctDbtTxInf>
	        <PmtId>
	          <EndToEndId>151608</EndToEndId>
	        </PmtId>
	        <InstdAmt Ccy="EUR">298.00</InstdAmt>
	        <DrctDbtTx>
	          <MndtRltdInf>
	            <MndtId>151608</MndtId>
	            <DtOfSgntr>2016-09-30</DtOfSgntr>
	          </MndtRltdInf>
	        </DrctDbtTx>
	        <DbtrAgt>
	          <FinInstnId>
	            <BIC>BASKES2B</BIC>
	          </FinInstnId>
	        </DbtrAgt>
	        <Dbtr>
	          <Nm>AITANA CALVO GUTIÉRREZ</Nm>
	          <Id>
	            <PrvtId>
	              <Othr>
	                <Id>000000151608</Id>
	              </Othr>
	            </PrvtId>
	          </Id>
	        </Dbtr>
	        <DbtrAcct>
	          <Id>
	            <IBAN>ES7320950436019107133221</IBAN>
	          </Id>
	        </DbtrAcct>
	        <RmtInf>
	          <Ustrd>CUOTA GUARDERÍA</Ustrd>
	        </RmtInf>
	      </DrctDbtTxInf>
		 */
		// return null;
	}

	/*
	private static String Tag(int tabs, String tagName, String content, boolean trim) {
		String tabStr = "";
		for (int t = 0; t < tabs; t++) { tabStr += "\t"; };
		return tabStr + "<" + tagName + ">" + content.replace(" ", "") + "</" + tagName + ">" + endl;
	}
	
	private static String Tag(int tabs, String tagName, String content) {
		return Tag(tabs,tagName, content, true);
	}
	private static String Tag(int tabs, String tagName, String content1, String content2, String... contents) {
		String newContent = endl + content1 + content2;
		for (String content : contents) { newContent = newContent + content; }
		return Tag(tabs, tagName, newContent, false);
	}
	private static String Tag(int tabs, String tagName, AlphaDataUnit content) {
		return Tag(tabs, tagName, content.toString());
	}
	private static String Tag(int tabs, String tagName, double content) {
		return Tag(tabs, tagName, content + "");
	}
	private static String Tag(int tabs, String tagName, NumericDataUnit content) {
		return Tag(tabs, tagName, content.toString());
	}
	*/

	private static String getISO8601StringForDate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		return dateFormat.format(date);
	}
	private static String getISODate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		return dateFormat.format(date);
	}
}
