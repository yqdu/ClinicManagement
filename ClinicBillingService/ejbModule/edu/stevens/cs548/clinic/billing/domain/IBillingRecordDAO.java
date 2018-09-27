package edu.stevens.cs548.clinic.billing.domain;

import java.util.List;

import edu.stevens.cs548.clinic.billing.BillingRecord;

public interface IBillingRecordDAO {
	
	public static class BillingRecordExn extends Exception {
		private static final long serialVersionUID = 1L;
		public BillingRecordExn (String msg) {
			super(msg);
		}
	}
	
	public List<BillingRecord> getBillingRecords();
	
	public BillingRecord getBillingRecord (long id) throws BillingRecordExn;
	
	public void addBillingRecord (BillingRecord t);
	
	public void deleteBillingRecord (long id);
	
	public void deleteBillingRecords ();
	
}
