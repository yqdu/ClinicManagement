package edu.stevens.cs548.clinic.domain;

import java.util.Date;
import java.util.List;

public class TreatmentFactory implements ITreatmentFactory {

	@Override
	public Treatment createDrugTreatment(String diagnosis, String drug, float dosage) {
		DrugTreatment treatment = new DrugTreatment();
		treatment.setDiagnosis(diagnosis);
		treatment.setDrug(drug);
		treatment.setDosage(dosage);
		treatment.setTreatmentType(TreatmentType.DRUG_TREATMENT.getTag());
		return treatment;
	}

	// 7 TreatmentFactory methods for radiology and surgery
	@Override
	public Treatment createRadiology(String diagnosis, List<Date> dates) {
		Radiology treatment = new Radiology();
		treatment.setDiagnosis(diagnosis);
		treatment.setDates(dates);
		treatment.setTreatmentType(TreatmentType.RADIOLOGY.getTag());
		return treatment;
	}

	@Override
	public Treatment createSurgery(String diagnosis, Date date) {
		Surgery treatment = new Surgery();
		treatment.setDiagnosis(diagnosis);
		treatment.setDate(date);
		treatment.setTreatmentType(TreatmentType.SURGERY.getTag());
		return treatment;
	}

}
