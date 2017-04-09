package es.ssj.data.book19.externalInfo;

//import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.*;

import es.ssj.data.book19.Book19;

import java.util.*;
import java.io.*;


public class ExcelManager {
    
    public static void readCreditorInfo(String pathToCreditorXLS, Book19 book, String fileName, Calendar payday, Calendar fileCreationDate) throws IOException {
    	
		FileInputStream fileInputStream = new FileInputStream(pathToCreditorXLS);
        Workbook wb = new HSSFWorkbook(fileInputStream);

		HSSFSheet worksheet = (HSSFSheet) wb.getSheetAt(0);

		HSSFRow row = worksheet.getRow(1); // base 0
		
		String cif  = row.getCell(0).getStringCellValue();
		String name = row.getCell(1).getStringCellValue();
		String bic  = row.getCell(2).getStringCellValue();
		String iban = row.getCell(3).getStringCellValue();
		String ref  = row.getCell(4).getStringCellValue();
		int paymentRef = (int) row.getCell(5).getNumericCellValue();
		
		book.setInfo(paymentRef, ref, cif, name, fileName /*Nombre del archivo que se va a crear*/,
				bic, iban, payday, fileCreationDate); // preguntar por interfaz
		
		wb.close();
    }
    
    public static void readDebtorsInfo(String pathToDebtorsXLS, Book19 book) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(pathToDebtorsXLS);
        Workbook wb = new HSSFWorkbook(fileInputStream);

		HSSFSheet worksheet = (HSSFSheet) wb.getSheetAt(0);
		
		for(int i = 1; i < worksheet.getLastRowNum(); i++) {
			HSSFRow row = worksheet.getRow(i); // base 0
			
			if (row != null) {
				HSSFCell conceptCellText1 = row.getCell(6);
				HSSFCell conceptCellAmount1 = row.getCell(7);
				HSSFCell conceptCellText2 = row.getCell(8);
				HSSFCell conceptCellAmount2 = row.getCell(9);
				HSSFCell conceptCellText3 = row.getCell(10);
				HSSFCell conceptCellAmount3 = row.getCell(11);
				HSSFCell conceptCellText4 = row.getCell(12);
				HSSFCell conceptCellAmount4 = row.getCell(13);
				
				if (   conceptCellText1 != null && conceptCellAmount1 != null
					|| conceptCellText2 != null && conceptCellAmount2 != null
					|| conceptCellText3 != null && conceptCellAmount3 != null 
					|| conceptCellText4 != null && conceptCellAmount4 != null ) {
				
					// TODO que hacer si hay algun registro vacio?
					int reference = (int)row.getCell(0).getNumericCellValue();
					String childName  = row.getCell(1).getStringCellValue();
					String level = row.getCell(2).getStringCellValue();
					String bic = row.getCell(3).getStringCellValue();
					String accountOwner = row.getCell(4).getStringCellValue();
					String iban = row.getCell(5).getStringCellValue();

					String concepts = "";
					if (conceptCellText1 != null && conceptCellAmount1 != null) {
						concepts += conceptFormat(conceptCellText1, conceptCellAmount1);
					}
					if (conceptCellText2 != null && conceptCellAmount2 != null) {
						concepts += conceptFormat(conceptCellText2, conceptCellAmount2);
					}
					if (conceptCellText3 != null && conceptCellAmount3 != null) {
						concepts += conceptFormat(conceptCellText3, conceptCellAmount3);
					}
					if (conceptCellText4 != null && conceptCellAmount4 != null) {
						concepts += conceptFormat(conceptCellText4, conceptCellAmount4);
					}
					
					double chargeAmount = row.getCell(14).getNumericCellValue();
		
					book.addDebtor(chargeAmount, bic, accountOwner, iban, 
							childName, level, concepts.trim(), reference + "");
				
				}
			}
		}
		wb.close();
		book.processDebtorsData();
    }
    
    public static void writeDebtorsReference(String pathToCreditorXLS, int increment) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(pathToCreditorXLS);
        Workbook wb = new HSSFWorkbook(fileInputStream);
	
		HSSFCell cell =  (HSSFCell) wb.getSheetAt(0).getRow(1).getCell(5);
		
		int paymentRef  = (int) cell.getNumericCellValue();
		cell.setCellValue(paymentRef + increment);

		fileInputStream.close();
        FileOutputStream out = new FileOutputStream(pathToCreditorXLS);
        wb.write(out);
        out.close();
        
		try { wb.close(); } catch (Exception e) {};
    }
    
    private static String conceptFormat(HSSFCell text, HSSFCell amount) {
    	return String.format("%s %.2f  ", text.getStringCellValue(), amount.getNumericCellValue());
    }
    
}




