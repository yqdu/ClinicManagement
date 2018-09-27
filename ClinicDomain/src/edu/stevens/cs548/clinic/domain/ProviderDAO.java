package edu.stevens.cs548.clinic.domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class ProviderDAO implements IProviderDAO {

	private EntityManager em;
	private TreatmentDAO treatmentDAO;
	
	public ProviderDAO(EntityManager em) {
		this.em = em;
		this.treatmentDAO = new TreatmentDAO(em);
	}
	
	// 6 Get provider by NPI
	@Override
	public Provider getProviderByNPI(long npi) throws ProviderExn {
		TypedQuery<Provider> query = 
				em.createNamedQuery("SearchProviderByNPI", Provider.class)
				.setParameter("npi", npi);
		List<Provider> providers = query.getResultList();
		if (providers.size() > 1){
			throw new ProviderExn("Duplicate provider records: National Provider Identifier = " + npi);
		} else if (providers.size() < 1) {
			throw new ProviderExn("Provider not found: National Provider Identifier = " + npi);
		} else {
			Provider prov = providers.get(0);
			return prov;
		}
	}

	// 5 Get provider
	@Override
	public Provider getProvider(long id) throws ProviderExn {
		Provider prov = em.find(Provider.class, id);
		if (prov == null){
			throw new ProviderExn("Provider not found: primary key = " + id);
		} else {
			return prov;
		}
	}

	// not required
	@Override
	public List<Provider> getProviderByNameSpecialization(String name, String specialization) {
		TypedQuery<Provider> query = 
				em.createNamedQuery("SearchProviderByNameSpecialization", Provider.class)
				.setParameter("name", name)
				.setParameter("specialization", specialization);
		List<Provider> providers = query.getResultList();
		return providers;
	}

	// 4 Add a provider (ProviderFactory.createProvider and ProviderDAO.addProvider
	@Override
	public long addProvider(Provider prov) throws ProviderExn {
		long npi = prov.getNpi();
		TypedQuery<Provider> query = 
				em.createNamedQuery("SearchProviderByNPI", Provider.class)
				.setParameter("npi", npi);
		List<Provider> providers = query.getResultList();
		if (providers.size() < 1) {
			em.persist(prov);
			prov.setTreatmentDAO(this.treatmentDAO);
			return npi;
		} else {
			Provider prov2 = providers.get(0);
			throw new ProviderExn("Insertion: Provider with National Provider Identifier(" + npi
					+") already exists.\n** Note:"+prov2.getName());
		}

	}

	@Override
	public void deleteProvider(Provider prov) throws ProviderExn {
		em.remove(prov);
	}

}
