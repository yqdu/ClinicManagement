package edu.stevens.cs548.clinic.research;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-04-08T14:51:19.829-0400")
@StaticMetamodel(DrugTreatmentRecord.class)
public class DrugTreatmentRecord_ {
	public static volatile SingularAttribute<DrugTreatmentRecord, Long> id;
	public static volatile SingularAttribute<DrugTreatmentRecord, Date> date;
	public static volatile SingularAttribute<DrugTreatmentRecord, String> drugName;
	public static volatile SingularAttribute<DrugTreatmentRecord, Float> dosage;
	public static volatile SingularAttribute<DrugTreatmentRecord, Subject> subject;
}
