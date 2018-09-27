package edu.stevens.cs548.clinic.service.ejb;


import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;


public interface IPatientService {

	public class PatientServiceExn extends Exception{
		private static final long serialVersionUID = 1L;

		public PatientServiceExn(String m){
			super(m);
		}
	}
	
	public class PatientNotFoundExn extends PatientServiceExn {
		private static final long serialVersionUID = 1L;

		public PatientNotFoundExn(String m){
			super(m);
		}
	}
	
	public class TreatmentNotFoundExn extends PatientServiceExn {
		private static final long serialVersionUID = 1L;

		public TreatmentNotFoundExn(String m){
			super(m);
		}
	}
	
	public long addPatient(PatientDto patientDto) throws PatientServiceExn;
	
	public PatientDto getPatient (long id) throws PatientServiceExn;
	
	public PatientDto getPatientByPatId (long pid) throws PatientServiceExn;
	
	public TreatmentDto getTreatment(long id, long tid) throws PatientNotFoundExn, TreatmentNotFoundExn, PatientServiceExn;
	
	public String siteInfo();
//
//	public void deletePatients() ;
	
}
