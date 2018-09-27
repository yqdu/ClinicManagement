package edu.stevens.cs548.clinic.domain;

import static javax.persistence.CascadeType.REMOVE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;

/**
 * Entity implementation class for Entity: Provider
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(
			name="SearchProviderByNameSpecialization",
			query="select prov from Provider prov where prov.name = :name and prov.specialization = :specialization"),
	@NamedQuery(
			name="SearchProviderByNPI",
			query="select prov from Provider prov where prov.npi = :npi"),
	@NamedQuery(
			name="RemoveAllProviders",
			query="delete from Provider prov")
})
@Table(name="PROVIDER")
public class Provider implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	private long npi;
	private String name;
	private String specialization;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getNpi() {
		return npi;
	}

	public void setNpi(long npi) {
		this.npi = npi;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	@OneToMany(cascade = REMOVE, mappedBy = "provider")
	@OrderBy
	private List<Treatment> treatments;

	public List<Treatment> getTreatments() {
		return treatments;
	}

	public void setTreatments(List<Treatment> treatments) {
		this.treatments = treatments;
	}
	
	
	@Transient
	private ITreatmentDAO treatmentDAO;
	
	public void setTreatmentDAO (ITreatmentDAO tdao) {
		this.treatmentDAO = tdao;
	}
	
	// 8 Provider.addTreatment
	public long addTreatment(Treatment t){
		
		if (t.getProvider() != this) {
			t.setProvider(this);
		}
		
		this.getTreatments().add(t);
		this.treatmentDAO.addTreatment(t);
		return t.getId();
	}
	
	// 9 Provider.getTreatmentIds
	public List<Long> getTreatmentIds(){
		List<Long> tids = new ArrayList<Long>();
		for (Treatment t : this.getTreatments()){
			tids.add(t.getId());
		}
		return tids;
	}
	
	//10 Provider.exportTreatment
	public <T> T exportTreatment(long tid, ITreatmentExporter<T> visitor) throws TreatmentExn {
		// Export a treatment without violated Aggregate pattern
		// Check that the exported treatment is a treatment for this provider.
		Treatment t = treatmentDAO.getTreatment(tid);
		if (t.getProvider() != this) {
			throw new TreatmentExn("Inappropriate treatment access: provider = " + id + ", treatment = " + tid);
		}
		return t.export(visitor);
	}
	
	public Provider() {
		super();
		treatments = new ArrayList<Treatment>();
	}
   
}
