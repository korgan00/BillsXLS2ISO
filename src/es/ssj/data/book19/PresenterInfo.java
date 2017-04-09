package es.ssj.data.book19;

import java.util.Calendar;

import es.ssj.data.book19.common.AlphaDataUnit;
import es.ssj.data.book19.common.NumericDataUnit;

public class PresenterInfo {
	
	private NumericDataUnit registryCode;
	private NumericDataUnit bookVersion;
	private NumericDataUnit dataNumber;
	
	private AlphaDataUnit presenterId;
	private AlphaDataUnit presenterName;

	private NumericDataUnit fileCreationDate;
	private Calendar fileCreationDateGC;
	private AlphaDataUnit fileId;

	private NumericDataUnit receptorEntity;
	private NumericDataUnit receptorOffice;
	private AlphaDataUnit free;
	
	// public PresenterInfo() { this(BOOK_19_14); }

	public PresenterInfo(int book19) {
		registryCode = new NumericDataUnit(2);
		bookVersion = new NumericDataUnit(5);
		dataNumber = new NumericDataUnit(3);
		presenterId = new AlphaDataUnit(35);
		presenterName = new AlphaDataUnit(70);
		
		fileCreationDate = new NumericDataUnit(8);
		fileId = new AlphaDataUnit(35);
		

		receptorEntity = new NumericDataUnit(4);
		receptorOffice = new NumericDataUnit(4);
		free = new AlphaDataUnit(434);
		
		/* PREDEFINED VALUES */
		registryCode.set(1);
		bookVersion.set(book19);
		dataNumber.set(1);
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

	public AlphaDataUnit getPresenterId() {
		return presenterId;
	}

	public AlphaDataUnit getPresenterName() {
		return presenterName;
	}

	public void setFileCreationDateGregorianCalendar(Calendar cal) {
		this.fileCreationDateGC = cal;
	}
	
	public Calendar getFileCreationDateGregorianCalendar() {
		return this.fileCreationDateGC;
	}
	
	public NumericDataUnit getFileCreationDate() {
		return fileCreationDate;
	}

	public AlphaDataUnit getFileId() {
		return fileId;
	}

	public NumericDataUnit getReceptorEntity() {
		return receptorEntity;
	}

	public NumericDataUnit getReceptorOffice() {
		return receptorOffice;
	}

	public AlphaDataUnit getFree() {
		return free;
	}
	
	public String toString() {
		return registryCode.toString()
				+ bookVersion
				+ dataNumber
				+ presenterId
				+ presenterName
				+ fileCreationDate
				+ fileId
				+ receptorEntity
				+ receptorOffice
				+ free;
	}
}
