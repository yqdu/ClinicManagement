package edu.stevens.cs548.clinic.research.service;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.stevens.cs548.clinic.billing.service.ClinicResearch;

/**
 * Session Bean implementation class PatientProducer
 */
@Stateless
@LocalBean
public class ClinicResearchProducer {

    /**
     * Default constructor. 
     */
    public ClinicResearchProducer() {
    }
    
    @PersistenceContext(unitName="ClinicDomain")
    EntityManager em;
    
    @Produces @ClinicResearch
    public EntityManager clinicDomainProducer() {
    	return em;
    }
    
    public void clinicDomainDispose(@Disposes @ClinicResearch EntityManager em) {
    	// em.close();
    }

}
