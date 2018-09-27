package edu.stevens.cs548.clinic.research.domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import edu.stevens.cs548.clinic.research.DrugTreatmentRecord;
import edu.stevens.cs548.clinic.research.Subject;

public class ResearchDAO implements IResearchDAO {
	
	public ResearchDAO (EntityManager em) {
		this.em = em;
	}
	
	private EntityManager em;

	@Override
	public List<DrugTreatmentRecord> getDrugTreatmentRecords() {
		TypedQuery<DrugTreatmentRecord> query = em.createNamedQuery("SearchDrugTreatmentRecords", DrugTreatmentRecord.class);
		return query.getResultList();
	}
	
	@Override
	public DrugTreatmentRecord getDrugTreatmentRecord(long id) throws ResearchExn {
		DrugTreatmentRecord t = em.find(DrugTreatmentRecord.class, id);
		if (t == null) {
			throw new ResearchExn("Missing DrugTreatmentRecord: id = " + id);
		} else {
			return t;
		}
	}

	@Override
	public void addDrugTreatmentRecord(DrugTreatmentRecord t) {
		em.persist(t);
	}
	
	@Override
	public void deleteDrugTreatmentRecord(long id) {
		DrugTreatmentRecord m = em.find(DrugTreatmentRecord.class, id);
		if (m != null) {
			em.remove(m);
		} else {
			throw new IllegalArgumentException("No DrugTreatmentRecord with id "+id);
		}
	}

	@Override
	public void deleteDrugTreatmentRecords() {
		Query q = em.createNamedQuery("DeleteDrugTreatmentRecords");
		q.executeUpdate();
	}
	
	@Override
	public Subject getSubject(long id) throws ResearchExn {
		Subject subject = em.find(Subject.class,  id);
		if (subject == null) {
			throw new ResearchExn("No such subject: " + id); 
		} else {
			return subject;
		}
	}
	
	@Override
	public void addSubject(Subject s) {
		em.persist(s);
	}

}
