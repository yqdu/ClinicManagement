package edu.stevens.cs548.clinic.billing.domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import edu.stevens.cs548.clinic.billing.BillingRecord;

public class BillingRecordDAO implements IBillingRecordDAO {
	
	public BillingRecordDAO (EntityManager em) {
		this.em = em;
	}
	
	private EntityManager em;

	@Override
	public List<BillingRecord> getBillingRecords() {
		TypedQuery<BillingRecord> query = em.createNamedQuery("SearchBillingRecords", BillingRecord.class);
		return query.getResultList();
	}
	
	@Override
	public BillingRecord getBillingRecord(long id) throws BillingRecordExn {
		BillingRecord t = em.find(BillingRecord.class, id);
		if (t == null) {
			throw new BillingRecordExn("Missing BillingRecord: id = " + id);
		} else {
			return t;
		}
	}

	@Override
	public void addBillingRecord(BillingRecord t) {
		em.persist(t);
	}
	
	@Override
	public void deleteBillingRecord(long id) {
		BillingRecord m = em.find(BillingRecord.class, id);
		if (m != null) {
			em.remove(m);
		} else {
			throw new IllegalArgumentException("No BillingRecord with id "+id);
		}
	}

	@Override
	public void deleteBillingRecords() {
		Query q = em.createNamedQuery("DeleteBillingRecords");
		q.executeUpdate();
	}

}
