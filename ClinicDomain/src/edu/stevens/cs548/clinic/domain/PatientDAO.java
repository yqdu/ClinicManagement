package edu.stevens.cs548.clinic.domain;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class PatientDAO implements IPatientDAO {

	private EntityManager em;
	private TreatmentDAO treatmentDAO;
	
	public PatientDAO(EntityManager em) {
		this.em = em;
		this.treatmentDAO = new TreatmentDAO(em);
	}

	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(PatientDAO.class.getCanonicalName());

	
	// 1 Add a patient
	@Override
	public long addPatient(Patient patient) throws PatientExn {
		long pid = patient.getPatientId();
		Query query = em.createNamedQuery("CountPatientByPatientID").setParameter("pid", pid);
		Long numExisting = (Long) query.getSingleResult();
		if (numExisting < 1) {
			// TODO add to database (and sync with database to generate primary key)
			// Don't forget to initialize the patient aggregate with a treatment DAO
			em.persist(patient);
			patient.setTreatmentDAO(this.treatmentDAO);
			return pid;
//			throw new IllegalStateException("Unimplemented");
			
		} else {
			throw new PatientExn("Insertion: Patient with patient id (" + pid + ") already exists.");
		}
	}

	//2 Get patient
	@Override
	public Patient getPatient(long id) throws PatientExn {
		// TODO retrieve patient using primary key
		Patient p = em.find(Patient.class, id);
		if (p == null){
			throw new PatientExn("Patient not found: primary key = " + id);
		} else {
			p.setTreatmentDAO(this.treatmentDAO);
			return p;
		}
	}

	//3 Get patient by patient id
	@Override
	public Patient getPatientByPatientId(long pid) throws PatientExn {
		// TODO retrieve patient using query on patient id (secondary key)
		TypedQuery<Patient> query = em.createNamedQuery("SearchPatientByPatientID", Patient.class).setParameter("pid", pid);
		List<Patient> patients = query.getResultList();
		if (patients.size() > 1) {
			throw new PatientExn("Duplicate patient records: patient id = " + pid);
			
		} 
		else if (patients.size() < 1){
			throw new PatientExn("Patient not found: patient id = " + pid);
		}
		else {
			Patient p = patients.get(0);
			p.setTreatmentDAO(this.treatmentDAO);
			return p; 
		}
	}
	
	@Override
	public void deletePatients() {
		/*
		 * If we ensure that deletion of patients cascades deletes of treatments,
		 * then we only need to delete patients.
		 */
		Query removeTreatment = em.createNamedQuery("RemoveAllTreatments", Treatment.class);
		removeTreatment.executeUpdate();
		Query removeDrugTreatment = em.createNamedQuery("RemoveAllDrugTreatments", DrugTreatment.class);
		removeDrugTreatment.executeUpdate();
		Query removeSurgery = em.createNamedQuery("RemoveAllSurgery", Surgery.class);
		removeSurgery.executeUpdate();
		Query removeRadiology = em.createNamedQuery("RemoveAllRadiology", Radiology.class);
		removeRadiology.executeUpdate();
		/*
		 * delete provider here,
		 * just for test of clear the database.
		 */
		Query removeProvider = em.createNamedQuery("RemoveAllProviders", Provider.class);
		removeProvider.executeUpdate();
		
		
		Query update = em.createNamedQuery("RemoveAllPatients");
		update.executeUpdate();
	}

}
