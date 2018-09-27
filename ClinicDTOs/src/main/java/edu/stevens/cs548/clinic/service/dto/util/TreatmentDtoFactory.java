package edu.stevens.cs548.clinic.service.dto.util;

import edu.stevens.cs548.clinic.domain.DrugTreatment;
import edu.stevens.cs548.clinic.domain.Radiology;
import edu.stevens.cs548.clinic.domain.Surgery;
import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.RadiologyType;
import edu.stevens.cs548.clinic.service.dto.SurgeryType;
import edu.stevens.cs548.clinic.service.dto.ObjectFactory;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;;;

public class TreatmentDtoFactory {
	
	ObjectFactory factory;
	
	public TreatmentDtoFactory() {
		factory = new ObjectFactory();
	}
	
	public TreatmentDto createDrugTreatmentDto () {
		TreatmentDto dto = factory.createTreatmentDto();
		DrugTreatmentType drug = factory.createDrugTreatmentType();
		dto.setDrugTreatment(drug);
		return dto;
	}
	
	public TreatmentDto createSurgeryDto () {
		TreatmentDto dto = factory.createTreatmentDto();
		SurgeryType surgery = factory.createSurgeryType();
		dto.setSurgery(surgery);
		return dto;
	}
	
	public TreatmentDto createRadiologyDto () {
		TreatmentDto dto = factory.createTreatmentDto();
		RadiologyType radiology = factory.createRadiologyType();
		dto.setRadiology(radiology);
		return dto;
	}
	
	public TreatmentDto createTreatmentDto (DrugTreatment t) {
		TreatmentDto dto = factory.createTreatmentDto();
		DrugTreatmentType drug = factory.createDrugTreatmentType();
		drug.setName(t.getDrug());
		drug.setDosage(t.getDosage());
		dto.setId(t.getId());
		dto.setDiagnosis(t.getDiagnosis());
		dto.setDrugTreatment(drug);
		return dto;
	}
	
	public TreatmentDto createTreatmentDto (Surgery t) {
		TreatmentDto dto = factory.createTreatmentDto();
		SurgeryType surgery = factory.createSurgeryType();
		surgery.setDate(t.getDate());
		dto.setId(t.getId());
		dto.setDiagnosis(t.getDiagnosis());
		dto.setSurgery(surgery);
		return dto;
	}
	
	public TreatmentDto createTreatmentDto (Radiology t) {
		TreatmentDto dto = factory.createTreatmentDto();
		RadiologyType radio = factory.createRadiologyType();
		radio.setDate(t.getDates());
		dto.setId(t.getId());
		dto.setDiagnosis(t.getDiagnosis());
		dto.setRadiology(radio);
		return dto;
	}
	
	/*
	 * TODO: Repeat for other treatments.
	 */

}
