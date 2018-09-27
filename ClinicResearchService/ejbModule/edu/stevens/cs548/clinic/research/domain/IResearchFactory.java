package edu.stevens.cs548.clinic.research.domain;

import edu.stevens.cs548.clinic.research.DrugTreatmentRecord;
import edu.stevens.cs548.clinic.research.Subject;

public interface IResearchFactory {
	
	public DrugTreatmentRecord createDrugTreatmentRecord();
	
	public Subject createSubject();
	
}
