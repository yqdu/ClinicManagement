package edu.stevens.cs548.clinic.billing.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import edu.stevens.cs548.clinic.billing.BillingRecord;
import edu.stevens.cs548.clinic.billing.domain.BillingRecordDAO;
import edu.stevens.cs548.clinic.billing.domain.BillingRecordFactory;
import edu.stevens.cs548.clinic.billing.domain.IBillingRecordFactory;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;
import edu.stevens.cs548.clinic.domain.Treatment;
import edu.stevens.cs548.clinic.domain.TreatmentDAO;

/**
 * Session Bean implementation class UserService
 */
@Stateless(name="BillingRecordServiceBean")
public class BillingService implements IBillingServiceLocal, IBillingServiceRemote {
	
	public static final String CHARSET = "UTF-8";
	
	private Logger logger = Logger.getLogger(BillingService.class.getCanonicalName());

	private BillingRecordDAO billingRecordDAO;
	
	private TreatmentDAO treatmentDAO;
	
	private IBillingRecordFactory billingRecordFactory;
	
	private BillingDtoFactory billingDtoFactory;
	
	/**
	 * Default constructor.
	 */
	public BillingService() {
		billingRecordFactory = new BillingRecordFactory();
		billingDtoFactory = new BillingDtoFactory();
	}

	@Inject @ClinicBilling
	private EntityManager em;

	@PostConstruct
	private void initialize() {
		billingRecordDAO = new BillingRecordDAO(em);
		treatmentDAO = new TreatmentDAO(em);
	}
	
	@SuppressWarnings("unused")
	private BillingDTO toDTO(BillingRecord billing) {
		BillingDTO dto = billingDtoFactory.createBillingDTO();
		dto.setTreatmentId(billing.getTreatment().getId());
		dto.setDescription(billing.getDescription());
		dto.setDate(billing.getDate());
		dto.setAmount(billing.getAmount());
		return dto;
	}

	@Override
	public List<BillingRecord> getBillingRecords() {
		List<BillingRecord> records = billingRecordDAO.getBillingRecords();
//		List<BillingDTO> dtos = new ArrayList<BillingDTO>();
//		for (BillingRecord billing : records) {
//			dtos.add(toDTO(billing));
//		}
//		return dtos;
		return records;
	}

	@Override
	public void addBillingRecord(BillingDTO dto) {
		try {
			BillingRecord billing = billingRecordFactory.createBillingRecord();
			Treatment treatment = treatmentDAO.getTreatment(dto.getTreatmentId());
			billing.setId(dto.getTreatmentId());
			billing.setTreatment(treatment);
			billing.setDescription(dto.getDescription());
			billing.setDate(dto.getDate());
			billing.setAmount(dto.getAmount());
			billingRecordDAO.addBillingRecord(billing);
		} catch (TreatmentExn e) {
			String s = String.format("Failed to add billing record: no such treatment %l", dto.getTreatmentId());
			logger.log(Level.SEVERE, s, e);
		}
	}
	
	@Override
	public void deleteBillingRecord(long id) {
		billingRecordDAO.deleteBillingRecord(id);
	}

}
