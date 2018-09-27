package edu.stevens.cs548.clinic.billing.service;

import edu.stevens.cs548.clinic.billing.service.IBillingService.BillingDTO;

public class BillingDtoFactory {
	
	public BillingDTO createBillingDTO() {
		return new BillingDTO();
	}

}
