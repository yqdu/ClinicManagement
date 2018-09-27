package edu.stevens.cs548.clinic.domain;

public interface IClinicGateway {
	
	public IPatientFactory gePatientFactory();
	
	public IPatientDAO getPatientDAO();
}
