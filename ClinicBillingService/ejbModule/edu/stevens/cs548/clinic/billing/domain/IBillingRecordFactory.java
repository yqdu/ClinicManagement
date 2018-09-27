package edu.stevens.cs548.clinic.billing.domain;

import edu.stevens.cs548.clinic.billing.BillingRecord;

public interface IBillingRecordFactory {
	
	public BillingRecord createBillingRecord();
	
}
