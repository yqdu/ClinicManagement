package edu.stevens.cs548.clinic.domain;

import java.util.Date;
import java.util.List;

public interface ITreatmentFactory {
	
	public Treatment createDrugTreatment (String diagnosis, String drug, float dosage);
	
	/*
	 * TODO add methods for Radiology, Surgery
	 */
	public Treatment createRadiology (String diagnosis, List<Date> dates);
	
	public Treatment createSurgery (String diagnosis, Date date);
}
