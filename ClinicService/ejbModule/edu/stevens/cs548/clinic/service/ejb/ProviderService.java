package edu.stevens.cs548.clinic.service.ejb;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.persistence.EntityManager;

import edu.stevens.cs548.clinic.domain.IPatientDAO;
import edu.stevens.cs548.clinic.domain.IPatientDAO.PatientExn;
import edu.stevens.cs548.clinic.domain.IProviderDAO;
import edu.stevens.cs548.clinic.domain.IProviderDAO.ProviderExn;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;
import edu.stevens.cs548.clinic.domain.ITreatmentExporter;
import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.domain.PatientDAO;
import edu.stevens.cs548.clinic.domain.Provider;
import edu.stevens.cs548.clinic.domain.ProviderDAO;
import edu.stevens.cs548.clinic.domain.ProviderFactory;
import edu.stevens.cs548.clinic.domain.Treatment;
import edu.stevens.cs548.clinic.domain.TreatmentFactory;
import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.ObjectFactory;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.RadiologyType;
import edu.stevens.cs548.clinic.service.dto.SurgeryType;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDtoFactory;

@Stateless(name = "ProviderServiceBean")
public class ProviderService implements IProviderServiceLocal {

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(PatientService.class.getCanonicalName());

	private ProviderFactory providerFactory;

	private TreatmentDtoFactory treatmentDtoFactory;

	private IProviderDAO providerDAO;

	private IPatientDAO patientDAO;

	public ProviderService() {
		providerFactory = new ProviderFactory();
		treatmentDtoFactory = new TreatmentDtoFactory();
	}

	@Inject
	@ClinicDomain
	EntityManager em;

	@PostConstruct
	private void initialize() {
		providerDAO = new ProviderDAO(em);
		patientDAO = new PatientDAO(em);
	}

	@Override
	public long addProvider(ProviderDto prov) throws ProviderServiceExn {
		try {
			Provider p = providerFactory.createProvider(prov.getNpi(), prov.getName(), prov.getSpecialization());
			providerDAO.addProvider(p);
			return p.getId();
		} catch (ProviderExn e) {
			throw new ProviderServiceExn(e.toString());
		}
	}

	@Override
	public ProviderDto getProvider(long id) throws ProviderServiceExn {
		try {
			Provider p = providerDAO.getProvider(id);
			return new ProviderDto(p);
		} catch (ProviderExn e) {
			throw new ProviderServiceExn(e.toString());
		}
	}

	@Override
	public ProviderDto getProviderByNPI(long npi) throws ProviderServiceExn {
		try {
			Provider p = providerDAO.getProviderByNPI(npi);
			return new ProviderDto(p);
		} catch (ProviderExn e) {
			throw new ProviderServiceExn(e.toString());
		}
	}

	@Resource(mappedName = "jms/TreatmentPool")
	private ConnectionFactory treatmentConnFactory;

	@Resource(mappedName = "jms/Treatment")
	private Topic treatmentTopic;

	Logger logger2 = Logger.getLogger("edu.stevens.cs548.chinic.service.ejb");

	@Override
	public long addTreatment(TreatmentDto treatment, long pid, long npi)
			throws TreatmentNotFoundExn, ProviderNotFoundExn, ProviderServiceExn {
		Connection treatmentConn = null;
		try {
			Patient pat = patientDAO.getPatientByPatientId(pid);
			Provider prov = providerDAO.getProviderByNPI(npi);
			TreatmentFactory treatmentFactory = new TreatmentFactory();
			Treatment t;

			if (treatment.getDrugTreatment() != null) {
				t = treatmentFactory.createDrugTreatment(treatment.getDiagnosis(),
						treatment.getDrugTreatment().getName(), treatment.getDrugTreatment().getDosage());
			} else if (treatment.getSurgery() != null) {
				t = treatmentFactory.createSurgery(treatment.getDiagnosis(), treatment.getSurgery().getDate());
			} else if (treatment.getRadiology() != null) {
				t = treatmentFactory.createRadiology(treatment.getDiagnosis(), treatment.getRadiology().getDate());
			} else {
				throw new TreatmentNotFoundExn("Invalid treatment!");
			}

			prov.addTreatment(t);
			long tid = pat.addTreatment(t);

			treatmentConn = treatmentConnFactory.createConnection();
			Session session = treatmentConn.createSession(true, Session.AUTO_ACKNOWLEDGE);
			MessageProducer producer = session.createProducer(treatmentTopic);

			TreatmentDto treat = null;
			if (treatment.getDrugTreatment() != null) {
				treat = treatmentDtoFactory.createDrugTreatmentDto();
				treat.setId(tid);
				treat.setPatient(treatment.getPatient());
				treat.setProvider(treatment.getProvider());
				treat.setDiagnosis(treatment.getDiagnosis());
				DrugTreatmentType drug = new DrugTreatmentType();
				drug.setName(treatment.getDrugTreatment().getName());
				drug.setDosage(treatment.getDrugTreatment().getDosage());
				treat.setDrugTreatment(drug);
			} else if (treatment.getSurgery() != null) {
				treat = treatmentDtoFactory.createSurgeryDto();
				treat.setId(tid);
				treat.setPatient(treatment.getPatient());
				treat.setProvider(treatment.getProvider());
				treat.setDiagnosis(treatment.getDiagnosis());
				SurgeryType surgery = new SurgeryType();
				surgery.setDate(treatment.getSurgery().getDate());
				treat.setSurgery(surgery);
			} else if (treatment.getRadiology() != null) {
				treat = treatmentDtoFactory.createRadiologyDto();
				treat.setId(tid);
				treat.setPatient(treatment.getPatient());
				treat.setProvider(treatment.getProvider());
				treat.setDiagnosis(treatment.getDiagnosis());
				RadiologyType radio = new RadiologyType();
				radio.setDate(treatment.getRadiology().getDate());
				treat.setRadiology(radio);
			}

			// treat.setId(tid);
			// treat.setPatient(treatment.getPatient());
			// treat.setProvider(treatment.getProvider());
			// treat.setDiagnosis(treatment.getDiagnosis());
			// if (treatment.getDrugTreatment() != null) {
			//// DrugTreatmentType drug = new DrugTreatmentType();
			//// drug.setName(treatment.getDrugTreatment().getName());
			//// drug.setDosage(treatment.getDrugTreatment().getDosage());
			//// treat.setDrugTreatment(drug);
			// treat.getDrugTreatment().setName(treatment.getDrugTreatment().getName());
			// treat.getDrugTreatment().setDosage(treatment.getDrugTreatment().getDosage());
			// } else if (treatment.getSurgery() != null) {
			//// SurgeryType surgery = new SurgeryType();
			//// surgery.setDate(treatment.getSurgery().getDate());
			//// treat.setSurgery(surgery);
			// treat.getSurgery().setDate(treatment.getSurgery().getDate());
			// } else if (treatment.getRadiology() != null) {
			//// RadiologyType radio = new RadiologyType();
			//// radio.setDate(treatment.getRadiology().getDate());
			//// treat.setRadiology(radio);
			// treat.getRadiology().setDate(treatment.getRadiology().getDate());
			// }

			ObjectMessage message = session.createObjectMessage();
			message.setObject(treat);

			message.setStringProperty("treatmentType", t.getTreatmentType());

			producer.send(message);

			return tid;

		} catch (ProviderExn e) {
			throw new ProviderServiceExn(e.toString());
		} catch (PatientExn e) {
			throw new ProviderServiceExn(e.toString());
		} catch (JMSException e) {
			// logger2.severe("JMS Error: " + e);
			throw new ProviderServiceExn("JMS Error: " + e);
		} finally {
			try {
				if (treatmentConn != null)
					treatmentConn.close();
			} catch (JMSException e) {
				logger2.severe("Error closing JMS connection: " + e);
			}
		}
	}

	public class TreatmentExporter implements ITreatmentExporter<TreatmentDto> {

		private ObjectFactory factory = new ObjectFactory();

		@Override
		public TreatmentDto exportDrugTreatment(long tid, String diagnosis, String drug, float dosage, Provider prov,
				Patient pat) {
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
			throws TreatmentNotFoundExn, ProviderNotFoundExn, ProviderServiceExn {
		try {
			Provider prov = providerDAO.getProvider(id);
			TreatmentExporter visitor = new TreatmentExporter();
			return prov.exportTreatment(tid, visitor);
		} catch (ProviderExn e) {
			throw new ProviderNotFoundExn(e.toString());
		} catch (TreatmentExn e) {
			throw new ProviderServiceExn(e.toString());
		}
	}

	@Resource(name = "SiteInfo")
	private String siteInformation;

	@Override
	public String siteInfo() {
		return siteInformation;
	}

}
