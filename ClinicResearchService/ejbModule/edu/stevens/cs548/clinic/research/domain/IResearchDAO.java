package edu.stevens.cs548.clinic.research.domain;

import java.util.List;

import edu.stevens.cs548.clinic.research.DrugTreatmentRecord;
import edu.stevens.cs548.clinic.research.Subject;

public interface IResearchDAO {
	
	public static class ResearchExn extends Exception {
		private static final long serialVersionUID = 1L;
		public ResearchExn (String msg) {
			super(msg);
		}
	}
	
	public List<DrugTreatmentRecord> getDrugTreatmentRecords();
	
	public DrugTreatmentRecord getDrugTreatmentRecord (long id) throws ResearchExn;
	
	public void addDrugTreatmentRecord (DrugTreatmentRecord t);
	
	public void deleteDrugTreatmentRecord (long id);
	
	public void deleteDrugTreatmentRecords ();
	
	public Subject getSubject(long id) throws ResearchExn;
	
	public void addSubject(Subject s);
	
}
