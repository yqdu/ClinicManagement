package edu.stevens.cs548.clinic.domain;

import edu.stevens.cs548.clinic.domain.Treatment;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Radiology
 *
 */
@Entity
@DiscriminatorValue("R")
@NamedQueries({
	@NamedQuery(
		name="RemoveAllRadiology",
		query="delete from Radiology r")
})

public class Radiology extends Treatment implements Serializable {

	
	private static final long serialVersionUID = 1L;

//	@OneToMany(cascade = REMOVE, mappedBy = "radiology")
//	@OrderBy
//	private List<RadiologyDates> dates;
	
//	@OneToMany(cascade = REMOVE)
//	private List<RadiologyDates> dates;
	
	@ElementCollection
	@Temporal(TemporalType.DATE)
	private List<Date> dates;
	
	public List<Date> getDates() {
		return dates;
	}

	public void setDates(List<Date> dates) {
		this.dates = dates;
	}


	public Radiology() {
		super();
		this.setTreatmentType("R");
	}

	public <T> T export(ITreatmentExporter<T> visitor) {
		return visitor.exportRadiology(this.getId(), 
		   		   this.getDiagnosis(),
		   		   this.dates,
		   		   this.getProvider(),
		   		   this.getPatient());
	}
   
}
