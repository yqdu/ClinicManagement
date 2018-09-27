package edu.stevens.cs548.clinic.research.jms;

import java.util.Date;
import java.util.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import edu.stevens.cs548.clinic.billing.service.IResearchService.DrugTreatmentDTO;
import edu.stevens.cs548.clinic.billing.service.IResearchServiceLocal;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;

/**
 * Message-Driven Bean implementation class for: DrugTreatmentListener
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "Treatment"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
		@ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "treatmentType='DT'") }, mappedName = "jms/Treatment")
public class DrugTreatmentListener implements MessageListener {

	/**
	 * Default constructor.
	 */
	public DrugTreatmentListener() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Inject
	IResearchServiceLocal researchService;

	Logger logger = Logger.getLogger("edu.stevens.cs548.clinic.research.jms");
	
	/**
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message message) {
		ObjectMessage objMessage = (ObjectMessage) message;
		try {
			TreatmentDto treatmentDto = (TreatmentDto) objMessage.getObject();
			if (treatmentDto.getDrugTreatment() == null) {
				logger.severe("It's not a drug treatment!");
			} else{
				
				//create new subject is already done in researchService.getSubject
				// if researchService.getSubject(patientId) == null
				
				//can use researchService.addDrugTreatmentRecord directly to set the relationship of subject and record
				
				DrugTreatmentDTO drugTreatmentDto = new DrugTreatmentDTO();
				drugTreatmentDto.setTreatmentId(treatmentDto.getId());
				drugTreatmentDto.setPatientId(treatmentDto.getPatient());
				drugTreatmentDto.setDate(new Date());
				drugTreatmentDto.setDrugName(treatmentDto.getDrugTreatment().getName());
				drugTreatmentDto.setDosage(treatmentDto.getDrugTreatment().getDosage());
				
				researchService.addDrugTreatmentRecord(drugTreatmentDto);
				
			}
		} catch (JMSException e) {
			logger.severe("JMS Error: " + e);
		}

	}

}
