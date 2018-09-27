package edu.stevens.cs548.clinic.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.RadiologyType;
import edu.stevens.cs548.clinic.service.dto.SurgeryType;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.PatientDtoFactory;
import edu.stevens.cs548.clinic.service.dto.util.ProviderDtoFactory;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDtoFactory;
import edu.stevens.cs548.clinic.service.ejb.ClinicDomain;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientServiceLocal;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceLocal;

/**
 * Session Bean implementation class TestBean
 */
@Singleton
@LocalBean
@Startup
public class InitBean {

	private static Logger logger = Logger.getLogger(InitBean.class.getCanonicalName());

	/**
	 * Default constructor.
	 */
	public InitBean() {
	}
    
	@Inject
	IPatientServiceLocal patientService;
	
	@Inject 
	IProviderServiceLocal providerService;
	
//	// TODO inject an EM
	@Inject 
	@ClinicDomain 
	EntityManager em;

	@PostConstruct
	private void init() {
		/*
		 * Put your testing logic here. Use the logger to display testing output in the server logs.
		 */
		logger.info("Your name here: Yaoqi Du");

		try {
	
			Calendar calendar = Calendar.getInstance();
			calendar.set(1984, 9, 4);

			PatientDtoFactory patientDtoFactory = new PatientDtoFactory();
			ProviderDtoFactory providerDtoFactory = new ProviderDtoFactory();
			TreatmentDtoFactory treatmentDtoFactory = new TreatmentDtoFactory();
			
//			PatientFactory patientFactory = new PatientFactory();
//			TreatmentFactory treatmentFactory = new TreatmentFactory();
//			ProviderFactory providerFactory = new ProviderFactory();

			PatientDto johnDto = patientDtoFactory.createPatientDto();
			johnDto.setPatientId(12345678L);
			johnDto.setName("John Doe");
			johnDto.setDob(calendar.getTime());
			johnDto.setAge(32);

			logger.info("Added " + johnDto.getName() + " with patient id " + johnDto.getPatientId());
			
			long id1 = patientService.addPatient(johnDto);
			String name1 = patientService.getPatient(id1).getName();
			long pid1 = patientService.getPatient(id1).getPatientId();
			logger.info("get patient "+ name1 +" with patient id "+pid1 + " by id " + id1);
			
			long id2 = patientService.getPatientByPatId(pid1).getId();
			String name2 = patientService.getPatientByPatId(pid1).getName();
			logger.info("get patient "+ name2 +" with id "+id2 + " by patient id " + pid1);
			
			
			ProviderDto davidDto = providerDtoFactory.createProviderDto();
			davidDto.setNpi(123);
			davidDto.setName("David Jackson");
			davidDto.setSpecialization("surgrery");

			logger.info("Added provider " + davidDto.getName() + " with NPI " + davidDto.getNpi());
			
			long id3 = providerService.addProvider(davidDto);
			String name3 = providerService.getProvider(id3).getName();
			long npi1 = providerService.getProvider(id3).getNpi();
			logger.info("get provider "+name3+" with NPI "+npi1 + " by id " + id3);
			
			long id4 = providerService.getProviderByNPI(npi1).getId();
			String name4 = providerService.getProviderByNPI(npi1).getName();
			logger.info("get provider "+name4+" with id "+id4 + " by NPI " + npi1);

			// test for creating treatments
//			Treatment drugTreatment = treatmentFactory.createDrugTreatment("flu", "drug1", 2);
			

			
			TreatmentDto drugDto = treatmentDtoFactory.createDrugTreatmentDto();
			drugDto.setDiagnosis("flu");
			drugDto.setPatient(johnDto.getId());
			drugDto.setProvider(davidDto.getId());
			DrugTreatmentType drug = new DrugTreatmentType();
			drug.setName("drug1");
			drug.setDosage(1);
			drugDto.setDrugTreatment(drug);
			
			long tid1 = providerService.addTreatment(drugDto, pid1, npi1);
			logger.info("Provider " + providerService.getProvider(id3).getNpi() + " added  drug treatment with id " + tid1 + "to patient " + patientService.getPatient(id1).getPatientId() + ", diagnosis is " + drugDto.getDiagnosis());
			
			TreatmentDto dto1 = providerService.getTreatment(id3, tid1);
			logger.info("provider service get treatment diagnosis " + dto1.getDiagnosis() + " by provider id " + id3 + " and treatment id " + tid1);
			
			TreatmentDto surgeryDto = treatmentDtoFactory.createSurgeryDto();
			surgeryDto.setDiagnosis("cancer");
			surgeryDto.setPatient(johnDto.getId());
			surgeryDto.setProvider(davidDto.getId());
			SurgeryType surgery = new SurgeryType();
			surgery.setDate(new Date());
			surgeryDto.setSurgery(surgery);
			
			long tid2 = providerService.addTreatment(surgeryDto, pid1, npi1);
			logger.info("Provider " + providerService.getProvider(id3).getNpi() + " added surgery with id " + tid2 + "to patient " + patientService.getPatient(id1).getPatientId() + ", diagnosis is " + surgeryDto.getDiagnosis());
			
			TreatmentDto dto2 = patientService.getTreatment(id1, tid2);
			logger.info("patient service get treatment diagnosis " + dto2.getDiagnosis() + " by patient id " + id1 + " and treatment id " + tid2);
			
			TreatmentDto radiologyDto = treatmentDtoFactory.createRadiologyDto();
			radiologyDto.setDiagnosis("headache");
			radiologyDto.setPatient(johnDto.getId());
			radiologyDto.setProvider(davidDto.getId());
			RadiologyType radio = new RadiologyType();
			List<Date> dates= new ArrayList<>();
			dates.add(calendar.getTime());
			radio.setDate(dates);
			radiologyDto.setRadiology(radio);
			
			long tid3 = providerService.addTreatment(radiologyDto, pid1, npi1);
			logger.info("Provider " + providerService.getProvider(id3).getNpi() + " added radiology with id " + tid3 + "to patient " + patientService.getPatient(id1).getPatientId() + ", diagnosis is " + radiologyDto.getDiagnosis());
			
			TreatmentDto dto3 = patientService.getTreatment(id1, tid3);
			logger.info("patient service get treatment diagnosis " + dto3.getDiagnosis() + " by patient id " + id1 + " and treatment id " + tid3);

		}catch (PatientServiceExn e) {
			IllegalStateException ex = new IllegalStateException("PatientServiceExn");
			ex.initCause(e);
			throw ex;
		} catch (ProviderServiceExn e) {
			IllegalStateException ex = new IllegalStateException("ProviderServiceExn");
			ex.initCause(e);
			throw ex;
		} 
			
	}

}
