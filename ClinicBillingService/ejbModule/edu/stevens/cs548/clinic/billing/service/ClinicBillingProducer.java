package edu.stevens.cs548.clinic.billing.service;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class PatientProducer
 */
@Stateless
@LocalBean
public class ClinicBillingProducer {

    /**
     * Default constructor. 
     */
    public ClinicBillingProducer() {
    }
    
    @PersistenceContext(unitName="ClinicDomain")
    EntityManager em;
    
    @Produces @ClinicBilling
    public EntityManager clinicDomainProducer() {
    	return em;
    }
    
    public void clinicDomainDispose(@Disposes @ClinicBilling EntityManager em) {
    	// em.close();
    }

}
