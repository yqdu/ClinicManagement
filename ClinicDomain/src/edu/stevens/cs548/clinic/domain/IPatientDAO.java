package edu.stevens.cs548.clinic.domain;


public interface IPatientDAO {
	
	public static class PatientExn extends Exception {
		private static final long serialVersionUID = 1L;
		public PatientExn (String msg) {
			super(msg);
		}
	}

	public long addPatient (Patient pat) throws PatientExn;
	
	public Patient getPatientByPatientId (long pid) throws PatientExn;
	
	public Patient getPatient (long id) throws PatientExn;
	
	public void deletePatients();

}
