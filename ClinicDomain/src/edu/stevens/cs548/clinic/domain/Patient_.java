package edu.stevens.cs548.clinic.domain;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-03-24T22:46:28.212-0400")
@StaticMetamodel(Patient.class)
public class Patient_ {
	public static volatile SingularAttribute<Patient, Long> id;
	public static volatile SingularAttribute<Patient, Long> patientId;
	public static volatile SingularAttribute<Patient, String> name;
	public static volatile SingularAttribute<Patient, Date> birthDate;
	public static volatile ListAttribute<Patient, Treatment> treatments;
}
