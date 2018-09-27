package edu.stevens.cs548.clinic.billing.jms;

import java.util.Date;
import java.util.Random;
import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import edu.stevens.cs548.clinic.billing.service.IBillingService.BillingDTO;
import edu.stevens.cs548.clinic.billing.service.IBillingServiceLocal;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;

/**
 * Message-Driven Bean implementation class for: TreatmentListener
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "Treatment"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic") }, mappedName = "jms/Treatment")
public class TreatmentListener implements MessageListener {

	/**
	 * Default constructor.
	 */
	public TreatmentListener() {
		// TODO Auto-generated constructor stub
	}

	// should use inject billing service
	// @PersistenceContext(unitName = "ClinicDomain")
	// private EntityManager em;

	@Inject
	private IBillingServiceLocal billingService;
	
	Logger logger = Logger.getLogger("edu.stevens.cs548.clinic.billing.jms");

	/**
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message message) {
		ObjectMessage objMessage = (ObjectMessage) message;

		try {
			TreatmentDto treatmentDto = (TreatmentDto) objMessage.getObject();

			// BillingRecord billingRecord = new BillingRecord();
			// // billingRecord.setAmount((float)Math.random());
			// Random generator = new Random();
			// billingRecord.setAmount(generator.nextFloat() * 500);
			// billingRecord.setDate(new Date());
			// billingRecord.setDescription("Test Description");

			// TreatmentFactory treatmentFactory = new TreatmentFactory();
			// IProviderDAO providerDao = new ProviderDAO(em);
			// IPatientDAO patientDao = new PatientDAO(em);
			//
			// Treatment treatment = null;
			// if (treatmentDto.getDrugTreatment() != null) {
			// treatment =
			// treatmentFactory.createDrugTreatment(treatmentDto.getDiagnosis(),
			// treatmentDto.getDrugTreatment().getName(),
			// treatmentDto.getDrugTreatment().getDosage());
			// treatment.setPatient(patientDao.getPatient(treatmentDto.getPatient()));
			// treatment.setProvider(providerDao.getProvider(treatmentDto.getProvider()));
			// }
			// else if (treatmentDto.getSurgery() != null) {
			// treatment =
			// treatmentFactory.createSurgery(treatmentDto.getDiagnosis(),
			// treatmentDto.getSurgery().getDate());
			// treatment.setPatient(patientDao.getPatient(treatmentDto.getPatient()));
			// treatment.setProvider(providerDao.getProvider(treatmentDto.getProvider()));
			// }
			// else if (treatmentDto.getRadiology() != null) {
			// treatment =
			// treatmentFactory.createRadiology(treatmentDto.getDiagnosis(),
			// treatmentDto.getRadiology().getDate());
			// treatment.setPatient(patientDao.getPatient(treatmentDto.getPatient()));
			// treatment.setProvider(providerDao.getProvider(treatmentDto.getProvider()));
			// }

			// ITreatmentDAO treatmentDao = new TreatmentDAO(em);
			//
			// try {
			// Treatment treatment =
			// treatmentDao.getTreatment(treatmentDto.getId());
			// billingRecord.setTreatment(treatment);
			// } catch (TreatmentExn e) {
			// // TODO
			// }
			// BillingDTO billingDto = billingService.toDTO(billingRecord);

			// BillingRecordDAO billingRecordDao = new BillingRecordDAO(em);
			// billingRecordDao.addBillingRecord(billingRecord);

			BillingDTO billingDto = new BillingDTO();
			billingDto.setTreatmentId(treatmentDto.getId());
			billingDto.setDescription("Test Description");
			billingDto.setDate(new Date());
			Random generator = new Random();
			billingDto.setAmount(generator.nextFloat() * 500);

			billingService.addBillingRecord(billingDto);

		} catch (JMSException e) {
			logger.severe("JMS Error: " + e);
		}

	}

}
