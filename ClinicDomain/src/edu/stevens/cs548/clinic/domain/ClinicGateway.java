package edu.stevens.cs548.clinic.domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ClinicGateway implements IClinicGateway {

	@Override
	public IPatientFactory gePatientFactory() {
		return new PatientFactory();
	}

	private EntityManagerFactory emf;

	@Override
	public IPatientDAO getPatientDAO() {
		EntityManager em = emf.createEntityManager();
		return  new PatientDAO(em);
	}

	public ClinicGateway(){
		emf = Persistence.createEntityManagerFactory("ClinicDomain");
	}
}
