package edu.stevens.cs548.clinic.domain;

import java.util.Date;
import java.util.List;

public class TreatmentExporter<T> implements ITreatmentExporter<T> {

	@Override
	public T exportDrugTreatment(long tid, String diagnosis, String drug, float dosage, Provider prov, Patient pat) {
		// TODO Auto-generated method stub
		DrugTreatment treatment = new DrugTreatment();
		treatment.setId(tid);
		treatment.setDiagnosis(diagnosis);
		treatment.setDrug(drug);
		treatment.setDosage(dosage);
		treatment.setProvider(prov);
		treatment.setPatient(pat);
		return (T)treatment;
	}

	@Override
	public T exportRadiology(long tid, String diagnosis, List<Date> dates, Provider prov, Patient pat) {
		// TODO Auto-generated method stub
		Radiology treatment = new Radiology();
		treatment.setId(tid);
		treatment.setDiagnosis(diagnosis);
		treatment.setDates(dates);
		treatment.setProvider(prov);
		treatment.setPatient(pat);
		return (T)treatment;
	}

	@Override
	public T exportSurgery(long tid, String diagnosis, Date date, Provider prov, Patient pat) {
		// TODO Auto-generated method stub
		Surgery treatment = new Surgery();
		treatment.setId(tid);
		treatment.setDiagnosis(diagnosis);
		treatment.setDate(date);
		treatment.setProvider(prov);
		treatment.setPatient(pat);
		return (T)treatment;
	}

}
