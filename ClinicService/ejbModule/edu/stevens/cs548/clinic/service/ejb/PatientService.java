package edu.stevens.cs548.clinic.service.ejb;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import edu.stevens.cs548.clinic.domain.IPatientDAO;
import edu.stevens.cs548.clinic.domain.IPatientDAO.PatientExn;
import edu.stevens.cs548.clinic.domain.IPatientFactory;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;
import edu.stevens.cs548.clinic.domain.ITreatmentExporter;
import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.domain.PatientDAO;
import edu.stevens.cs548.clinic.domain.PatientFactory;
import edu.stevens.cs548.clinic.domain.Provider;
import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.ObjectFactory;
import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.RadiologyType;
import edu.stevens.cs548.clinic.service.dto.SurgeryType;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.PatientDtoFactory;

/**
 * Session Bean implementation class PatientService
 */
@Stateless(name="PatientServiceBean")
public class PatientService implements IPatientServiceLocal,
		IPatientServiceRemote {
	
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(PatientService.class.getCanonicalName());

	private IPatientFactory patientFactory;
	
	private PatientDtoFactory patientDtoFactory;

	private IPatientDAO patientDAO;

	/**
	 * Default constructor.
	 */
	public PatientService() {
		
		patientFactory = new PatientFactory();
	}
	
	@Inject 
	@ClinicDomain 
	EntityManager em;
	
	@PostConstruct
	private void initialize(){
		patientDAO = new PatientDAO(em);
	}

	/**
	 * @see IPatientService#addPatient(String, Date, long)
	 */
	@Override
	public long addPatient(PatientDto dto) throws PatientServiceExn {
		// Use factory to create patient entity, and persist with DAO
		try {
			Patient patient = patientFactory.createPatient(dto.getPatientId(), dto.getName(), dto.getDob(), dto.getAge());
			patientDAO.addPatient(patient);
			return patient.getId();
		} catch (PatientExn e) {
			throw new PatientServiceExn(e.toString());
		}
	}

	/**
	 * @see IPatientService#getPatient(long)
	 */
	@Override
	public PatientDto getPatient(long id) throws PatientServiceExn {
		try{
			Patient patient = patientDAO.getPatient(id);
			return new PatientDto(patient);
		} catch (PatientExn e) {
			throw new PatientServiceExn(e.toString());
		}
	}

	/**
	 * @see IPatientService#getPatientByPatId(long)
	 */
	@Override
	public PatientDto getPatientByPatId(long pid) throws PatientServiceExn {
		try{
			Patient patient = patientDAO.getPatientByPatientId(pid);
			return new PatientDto(patient);
		} catch (PatientExn e) {
			throw new PatientServiceExn(e.toString());
		} 
	}

	public class TreatmentExporter implements ITreatmentExporter<TreatmentDto> {
		
		private ObjectFactory factory = new ObjectFactory();

		@Override
		public TreatmentDto exportDrugTreatment(long tid, String diagnosis, String drug, float dosage, Provider prov, Patient pat) {
			TreatmentDto dto = factory.createTreatmentDto();
			dto.setId(tid);
			dto.setDiagnosis(diagnosis);
			dto.setProvider(prov.getId());
			dto.setPatient(pat.getId());
			DrugTreatmentType drugInfo = factory.createDrugTreatmentType();
			drugInfo.setDosage(dosage);
			drugInfo.setName(drug);
			dto.setDrugTreatment(drugInfo);
			return dto;
		}

		@Override
		public TreatmentDto exportRadiology(long tid, String diagnosis, List<Date> dates, Provider prov, Patient pat) {
			TreatmentDto dto = factory.createTreatmentDto();
			dto.setId(tid);
			dto.setDiagnosis(diagnosis);
			dto.setProvider(prov.getId());
			dto.setPatient(pat.getId());
			RadiologyType radiology = factory.createRadiologyType();
			radiology.setDate(dates);
			dto.setRadiology(radiology);
			return dto;
		}

		@Override
		public TreatmentDto exportSurgery(long tid, String diagnosis, Date date, Provider prov, Patient pat) {
			TreatmentDto dto = factory.createTreatmentDto();
			dto.setId(tid);
			dto.setDiagnosis(diagnosis);
			dto.setProvider(prov.getId());
			dto.setPatient(pat.getId());
			SurgeryType surgery = factory.createSurgeryType();
			surgery.setDate(date);
			dto.setSurgery(surgery);
			return dto;
		}
		
	}
	
	@Override
	public TreatmentDto getTreatment(long id, long tid)
			throws PatientNotFoundExn, TreatmentNotFoundExn, PatientServiceExn {
		// Export treatment DTO from patient aggregate
		try {
			Patient patient = patientDAO.getPatient(id);
			TreatmentExporter visitor = new TreatmentExporter();
			return patient.exportTreatment(tid, visitor);
		} catch (PatientExn e) {
			throw new PatientNotFoundExn(e.toString());
		} catch (TreatmentExn e) {
			throw new PatientServiceExn(e.toString());
		}
	}

	@Resource(name="SiteInfo")
	private String siteInformation;
	

	@Override
	public String siteInfo() {
		return siteInformation;
	}

}
