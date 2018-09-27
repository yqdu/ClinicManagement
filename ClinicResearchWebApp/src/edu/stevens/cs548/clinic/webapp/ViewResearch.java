package edu.stevens.cs548.clinic.webapp;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import edu.stevens.cs548.clinic.billing.service.IResearchServiceLocal;
import edu.stevens.cs548.clinic.research.DrugTreatmentRecord;

@Named("researchBacking")
@ViewScoped
public class ViewResearch implements Serializable {

	private static final long serialVersionUID = -1983439889541606510L;
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ViewResearch.class.getCanonicalName());

	/*
	 * List of treatment records. 
	 */
	private List<DrugTreatmentRecord> treatments;
	
	public List<DrugTreatmentRecord> getTreatments() {
		return this.treatments;
	}

	@Inject
	private IResearchServiceLocal researchService;
		
	/**
	 * Refresh the messages from the database.
	 */
	public void refreshTreatments() {
		treatments = researchService.getDrugTreatmentRecords();
	}
	
	@PostConstruct
	private void init() {
		refreshTreatments();
	}

}
