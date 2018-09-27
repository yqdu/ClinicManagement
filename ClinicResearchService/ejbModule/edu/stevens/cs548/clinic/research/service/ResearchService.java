package edu.stevens.cs548.clinic.research.service;

import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import edu.stevens.cs548.clinic.billing.service.ClinicResearch;
import edu.stevens.cs548.clinic.billing.service.IResearchServiceLocal;
import edu.stevens.cs548.clinic.billing.service.IResearchServiceRemote;
import edu.stevens.cs548.clinic.research.DrugTreatmentRecord;
import edu.stevens.cs548.clinic.research.Subject;
import edu.stevens.cs548.clinic.research.domain.IResearchDAO;
import edu.stevens.cs548.clinic.research.domain.IResearchDAO.ResearchExn;
import edu.stevens.cs548.clinic.research.domain.IResearchFactory;
import edu.stevens.cs548.clinic.research.domain.ResearchDAO;
import edu.stevens.cs548.clinic.research.domain.ResearchFactory;

/**
 * Session Bean implementation class UserService
 */
@Stateless(name="ResearchServiceBean")
public class ResearchService implements IResearchServiceLocal, IResearchServiceRemote {
	
	public static final String CHARSET = "UTF-8";
	
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(ResearchService.class.getCanonicalName());

	private IResearchDAO researchDAO;
	
	private IResearchFactory researchFactory;
	
	private DrugTreatmentDtoFactory drugTreatmentDtoFactory;
	
	private Random rand;
	
	/**
	 * Default constructor.
	 */
	public ResearchService() {
		researchFactory = new ResearchFactory();
		drugTreatmentDtoFactory = new DrugTreatmentDtoFactory();
		rand = new Random();
	}

	@Inject @ClinicResearch
	private EntityManager em;

	@PostConstruct
	private void initialize() {
		researchDAO = new ResearchDAO(em);
	}
	
	@SuppressWarnings("unused")
	private DrugTreatmentDTO toDTO(DrugTreatmentRecord treatment) {
		DrugTreatmentDTO dto = drugTreatmentDtoFactory.createDrugTreatmentDTO();
		dto.setPatientId(treatment.getSubject().getId());
		dto.setTreatmentId(treatment.getId());
		dto.setDrugName(treatment.getDrugName());
		dto.setDate(treatment.getDate());
		dto.setDosage(treatment.getDosage());
		return dto;
	}

	@Override
	public List<DrugTreatmentRecord> getDrugTreatmentRecords() {
		List<DrugTreatmentRecord> records = researchDAO.getDrugTreatmentRecords();
		return records;
	}

	private Subject getSubject(long patientId) {
		try {
			return researchDAO.getSubject(patientId);
		} catch (ResearchExn e) {
			Subject subject = new Subject();
			subject.setId(patientId);
			subject.setSubjectId(rand.nextLong());
			researchDAO.addSubject(subject);
			return subject;
		}
	}
	
	@Override
	public void addDrugTreatmentRecord(DrugTreatmentDTO dto) {
		DrugTreatmentRecord treatment = researchFactory.createDrugTreatmentRecord();
		treatment.setId(dto.getTreatmentId());
		Subject subject = getSubject(dto.getPatientId());
		treatment.setSubject(subject);
		treatment.setDate(dto.getDate());
		treatment.setDrugName(dto.getDrugName());
		treatment.setDosage(dto.getDosage());
		researchDAO.addDrugTreatmentRecord(treatment);
	}
	
	@Override
	public void deleteDrugTreatmentRecord(long id) {
		researchDAO.deleteDrugTreatmentRecord(id);
	}

}
