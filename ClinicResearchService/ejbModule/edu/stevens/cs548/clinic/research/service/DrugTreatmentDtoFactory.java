package edu.stevens.cs548.clinic.research.service;

import edu.stevens.cs548.clinic.billing.service.IResearchService.DrugTreatmentDTO;

public class DrugTreatmentDtoFactory {
	
	public DrugTreatmentDTO createDrugTreatmentDTO() {
		return new DrugTreatmentDTO();
	}

}
