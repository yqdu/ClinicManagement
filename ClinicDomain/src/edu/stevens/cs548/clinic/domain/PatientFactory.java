package edu.stevens.cs548.clinic.domain;

import java.util.Calendar;
import java.util.Date;

public class PatientFactory implements IPatientFactory {

	@Override
	public Patient createPatient(long pid, String name, Date dob, int age) throws IPatientDAO.PatientExn {
		if (age != computeAge(dob)) {
			throw new IPatientDAO.PatientExn("Age and date of birth do not match.");
		} else {
			Patient p = new Patient();
			p.setPatientId(pid);
			p.setName(name);
			p.setBirthDate(dob);
			return p;
		}
	}
	
	public static int computeAge(Date dateOfBirth) {
		Calendar dob = Calendar.getInstance();  
		dob.setTime(dateOfBirth);  
		Calendar today = Calendar.getInstance();  
		int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);  
		if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
		  age--;  
		} else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
		    && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
		  age--;  
		}
		return age;
	}

}
