package edu.stevens.cs548.clinic.domain;

import java.util.Date;

public interface IPatientFactory {
	
	/**
	 * "age" field is sanity check: compare with "dob."
	 */
	public Patient createPatient (long pid, String name, Date dob, int age) throws IPatientDAO.PatientExn;

}
