package es.ssj.data.book19.externalInfo;

//import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.*;

import es.ssj.data.book19.Book19;

import java.util.*;
import java.io.*;


public class ExcelManager {
    
    public static void readCreditorInfo(String pathToCreditorXLS, Book19 book, 
    		String fileName, Calendar payday, Calendar fileCreationDate) throws XLSReadException {
    	
    	XLSReadException xlsException = new XLSReadException();
    	
    	try {
    		xlsException.setErrorDetails("Abriendo archivo");
			FileInputStream fileInputStream = new FileInputStream(pathToCreditorXLS);
	        Workbook wb = new HSSFWorkbook(fileInputStream);

	        xlsException.setErrorDetails("Seleccionando primera pagina");
			HSSFSheet worksheet = (HSSFSheet) wb.getSheetAt(0);

			xlsException.setErrorDetails("Seleccionando fila 2");
			HSSFRow row = worksheet.getRow(1); // base 0

			xlsException.setErrorDetails("Leyendo columna A, esperaba campo de texto");
			String cif     = row.getCell(0).getStringCellValue();
			xlsException.setErrorDetails("Leyendo columna B, esperaba campo de texto");
			String name    = row.getCell(1).getStringCellValue();
			xlsException.setErrorDetails("Leyendo columna C, esperaba campo de texto");
			String bic     = row.getCell(2).getStringCellValue();
			xlsException.setErrorDetails("Leyendo columna D, esperaba campo de texto");
			String iban    = row.getCell(3).getStringCellValue();
			xlsException.setErrorDetails("Leyendo columna E, esperaba campo de texto");
			String ref     = row.getCell(4).getStringCellValue();
			xlsException.setErrorDetails("Leyendo columna F, esperaba campo numérico");
			int paymentRef = (int) row.getCell(5).getNumericCellValue();
			xlsException.setErrorDetails("Leyendo columna G, esperaba campo de texto");
			String bankId  = row.getCell(6).getStringCellValue();
			
			book.setInfo(paymentRef, ref, cif, bankId, name, fileName /*Nombre del archivo que se va a crear*/,
					bic, iban, payday, fileCreationDate); // preguntar por interfaz
			
			xlsException.setErrorDetails("Cerrando archivo");
			wb.close();
    	} catch(Exception e) {
    		throw xlsException;
    	}
    }
    
    public static void readDebtorsInfo(String pathToDebtorsXLS, Book19 book) throws XLSReadException {
    	XLSReadException xlsException = new XLSReadException();
    	
    	try {
    		xlsException.setErrorDetails("Abriendo archivo");
			FileInputStream fileInputStream = new FileInputStream(pathToDebtorsXLS);
	        Workbook wb = new HSSFWorkbook(fileInputStream);

	        xlsException.setErrorDetails("Seleccionando primera pagina");
			HSSFSheet worksheet = (HSSFSheet) wb.getSheetAt(0);
			
			for(int i = 1; i < worksheet.getLastRowNum(); i++) {
				xlsException.setErrorDetails("Seleccionando fila " + (i+1));
				HSSFRow row = worksheet.getRow(i); // base 0
				
				if (row != null) {
					xlsException.setErrorDetails("Fila " + (i+1) + " Columna G");
					HSSFCell conceptCellText1 = row.getCell(6);
					xlsException.setErrorDetails("Fila " + (i+1) + " Columna H");
					HSSFCell conceptCellAmount1 = row.getCell(7);
					xlsException.setErrorDetails("Fila " + (i+1) + " Columna I");
					HSSFCell conceptCellText2 = row.getCell(8);
					xlsException.setErrorDetails("Fila " + (i+1) + " Columna J");
					HSSFCell conceptCellAmount2 = row.getCell(9);
					xlsException.setErrorDetails("Fila " + (i+1) + " Columna K");
					HSSFCell conceptCellText3 = row.getCell(10);
					xlsException.setErrorDetails("Fila " + (i+1) + " Columna L");
					HSSFCell conceptCellAmount3 = row.getCell(11);
					xlsException.setErrorDetails("Fila " + (i+1) + " Columna M");
					HSSFCell conceptCellText4 = row.getCell(12);
					xlsException.setErrorDetails("Fila " + (i+1) + " Columna N");
					HSSFCell conceptCellAmount4 = row.getCell(13);
					
					if (   conceptCellText1 != null && conceptCellAmount1 != null
						|| conceptCellText2 != null && conceptCellAmount2 != null
						|| conceptCellText3 != null && conceptCellAmount3 != null 
						|| conceptCellText4 != null && conceptCellAmount4 != null ) {
					
						// TODO que hacer si hay algun registro vacio?
						xlsException.setErrorDetails("Fila " + (i+1) + " Columna A, esperaba campo numérico");
						int reference = (int)row.getCell(0).getNumericCellValue();
						xlsException.setErrorDetails("Fila " + (i+1) + " Columna B, esperaba campo de texto");
						String childName  = row.getCell(1).getStringCellValue();
						xlsException.setErrorDetails("Fila " + (i+1) + " Columna C, esperaba campo de texto");
						String level = row.getCell(2).getStringCellValue();
						xlsException.setErrorDetails("Fila " + (i+1) + " Columna D, esperaba campo de texto");
						String bic = row.getCell(3).getStringCellValue();
						xlsException.setErrorDetails("Fila " + (i+1) + " Columna E, esperaba campo de texto");
						String accountOwner = row.getCell(4).getStringCellValue();
						xlsException.setErrorDetails("Fila " + (i+1) + " Columna F, esperaba campo de texto");
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

						xlsException.setErrorDetails("Fila " + (i+1) + " Columna O, esperaba campo numérico");
						double chargeAmount = row.getCell(14).getNumericCellValue();
			
						book.addDebtor(chargeAmount, bic, accountOwner, iban, 
								childName, level, concepts.trim(), reference + "");
					
					}
				}
			}
			wb.close();
			book.processDebtorsData();
    	} catch(Exception e) {
    		throw xlsException;
    	}
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




