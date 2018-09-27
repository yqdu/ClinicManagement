package edu.stevens.cs548.clinic.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Treatment
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="TTYPE")
@Table(name = "TREATMENT")
@NamedQueries({
	@NamedQuery(
		name="RemoveAllTreatments",
		query="delete from Treatment t")
})

public abstract class Treatment implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long id;
	private String diagnosis;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="TTYPE", length=2)
	private String treatmentType;
	
	public String getTreatmentType() {
		return treatmentType;
	}

	public void setTreatmentType(String treatmentType) {
		this.treatmentType = treatmentType;
	}

	public String getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}

	@ManyToOne
	@JoinColumn(name = "patient_fk", referencedColumnName = "id")
	private Patient patient;

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
		if (!patient.getTreatments().contains(this))
			patient.addTreatment(this);
	}
	
	@ManyToOne
	@JoinColumn(name = "provider_fk", referencedColumnName = "id")
	private Provider provider;
	
	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}
	
	public abstract <T> T export(ITreatmentExporter<T> visitor);

	public Treatment() {
		super();
	}
   
}
