package edu.stevens.cs548.clinic.billing.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import edu.stevens.cs548.clinic.billing.BillingRecord;

public interface IBillingService {
	
	public class BillingServiceExn extends Exception {
		private static final long serialVersionUID = 1L;
		private String messageCode;
		public String getMessageCode() {
			return messageCode;
		}
		public BillingServiceExn (String m) {
			super();
			messageCode = m;
		}
	}
	
	public class BillingDTO implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 670672998536764240L;
		private long treatmentId;
		private String description;
		private Date date;
		private float amount;
		
		public long getTreatmentId() {
			return treatmentId;
		}
		public void setTreatmentId(long treatmentId) {
			this.treatmentId = treatmentId;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public Date getDate() {
			return date;
		}
		public void setDate(Date date) {
			this.date = date;
		}
		public float getAmount() {
			return amount;
		}
		public void setAmount(float amount) {
			this.amount = amount;
		}
		
	}
	
	public List<BillingRecord> getBillingRecords();

	public void addBillingRecord(BillingDTO dto);

	public void deleteBillingRecord(long id);

}
