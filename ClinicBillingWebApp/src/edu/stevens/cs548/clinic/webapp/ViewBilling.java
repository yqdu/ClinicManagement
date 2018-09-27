package edu.stevens.cs548.clinic.webapp;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import edu.stevens.cs548.clinic.billing.BillingRecord;
import edu.stevens.cs548.clinic.billing.service.IBillingServiceLocal;

@Named("billingBacking")
@ViewScoped
public class ViewBilling implements Serializable {

	private static final long serialVersionUID = -1983439889541606510L;
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ViewBilling.class.getCanonicalName());

	/*
	 * List of billing records. 
	 */
	private List<BillingRecord> bills;
	
	public List<BillingRecord> getBills() {
		return this.bills;
	}

	@Inject
	private IBillingServiceLocal billingService;
		
	/**
	 * Refresh the messages from the database.
	 */
	public void refreshBills() {
		bills = billingService.getBillingRecords();
	}
	
	@PostConstruct
	private void init() {
		refreshBills();
	}

}
