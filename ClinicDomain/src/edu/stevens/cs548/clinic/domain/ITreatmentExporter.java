package edu.stevens.cs548.clinic.domain;

import java.util.Date;
import java.util.List;

public interface ITreatmentExporter<T> {
	
	public T exportDrugTreatment (long tid,
									 String diagnosis,
							   		 String drug,
							   		 float dosage,
							   		 Provider prov,
							   		 Patient pat);
	
	public T exportRadiology (long tid,
								 String diagnosis,
								 List<Date> dates,
								 Provider prov,
								 Patient pat);
	
	public T exportSurgery (long tid,
						 	   String diagnosis,
			                   Date date,
			                   Provider prov,
			                   Patient pat);

}
