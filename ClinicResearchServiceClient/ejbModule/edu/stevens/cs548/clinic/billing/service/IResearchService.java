package edu.stevens.cs548.clinic.billing.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import edu.stevens.cs548.clinic.research.DrugTreatmentRecord;

public interface IResearchService {
	
	public class ResearchServiceExn extends Exception {
		private static final long serialVersionUID = 1L;
		private String messageCode;
		public String getMessageCode() {
			return messageCode;
		}
		public ResearchServiceExn (String m) {
			super();
			messageCode = m;
		}
	}
	
	public class DrugTreatmentDTO implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 670672998536764240L;
		private long treatmentId;
		private long patientId;
		private Date date;
		private String drugName;
		private float dosage;
		
		public long getTreatmentId() {
			return treatmentId;
		}
		public void setTreatmentId(long treatmentId) {
			this.treatmentId = treatmentId;
		}
		public long getPatientId() {
			return patientId;
		}
		public void setPatientId(long patientId) {
			this.patientId = patientId;
		}
		public Date getDate() {
			return date;
		}
		public void setDate(Date date) {
			this.date = date;
		}
		public String getDrugName() {
			return drugName;
		}
		public void setDrugName(String drugName) {
			this.drugName = drugName;
		}
		public float getDosage() {
			return dosage;
		}
		public void setDosage(float dosage) {
			this.dosage = dosage;
		}
		
	}
	
	public List<DrugTreatmentRecord> getDrugTreatmentRecords();

	public void addDrugTreatmentRecord(DrugTreatmentDTO dto);

	public void deleteDrugTreatmentRecord(long id);

}
