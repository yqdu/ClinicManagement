package edu.stevens.cs548.clinic.research.domain;

import edu.stevens.cs548.clinic.research.DrugTreatmentRecord;
import edu.stevens.cs548.clinic.research.Subject;

public class ResearchFactory implements IResearchFactory {

	@Override
	public DrugTreatmentRecord createDrugTreatmentRecord() {
		return new DrugTreatmentRecord();
	}

	@Override
	public Subject createSubject() {
		return new Subject();
	}

}
