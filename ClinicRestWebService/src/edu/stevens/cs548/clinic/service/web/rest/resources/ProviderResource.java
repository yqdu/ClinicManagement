package edu.stevens.cs548.clinic.service.web.rest.resources;

import java.net.URI;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.RadiologyType;
import edu.stevens.cs548.clinic.service.dto.SurgeryType;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.ProviderDtoFactory;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDtoFactory;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientServiceLocal;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceLocal;
import edu.stevens.cs548.clinic.service.representations.ProviderRepresentation;
import edu.stevens.cs548.clinic.service.representations.Representation;
import edu.stevens.cs548.clinic.service.representations.TreatmentRepresentation;

@Path("/provider")
@RequestScoped
public class ProviderResource {
	
	final static Logger logger = Logger.getLogger(ProviderResource.class.getCanonicalName());
	
	
    @SuppressWarnings("unused")
    @Context
    private UriInfo uriInfo;

    private ProviderDtoFactory providerDtoFactory;
    private TreatmentDtoFactory treatmentDtoFactory;
    
    /**
     * Default constructor. 
     */
    public ProviderResource() {
    	providerDtoFactory = new ProviderDtoFactory();
    	treatmentDtoFactory = new TreatmentDtoFactory();
    }
    
    @Inject
    private IProviderServiceLocal providerService;
    
	@Inject
	private IPatientServiceLocal patientService;

    
    @GET
    @Path("site")
    @Produces("text/plain")
    public String getSiteInfo() {
    	return providerService.siteInfo();
    }

    /**
     * Query methods for provider resources.
     */
    @GET
    @Path("{id}")
    @Produces("application/xml")
    public ProviderRepresentation getProvider(@PathParam("id") String id) {
    	try {
			long key = Long.parseLong(id);
			ProviderDto providerDTO = providerService.getProvider(key);
			ProviderRepresentation providerRep = new ProviderRepresentation(providerDTO, uriInfo);
			return providerRep;
		} catch (ProviderServiceExn e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
    }
    
    @GET
    @Path("/byNPI")
    @Produces("application/xml")
    public ProviderRepresentation getProviderByNpi(@QueryParam("id") String npi) {
    	try {
			long key = Long.parseLong(npi);
			ProviderDto providerDTO = providerService.getProviderByNPI(key);
			ProviderRepresentation providerRep = new ProviderRepresentation(providerDTO, uriInfo);
			return providerRep;
		} catch (ProviderServiceExn e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
    }
    
    @GET
    @Path("{id}/treatments/{tid}")
    @Produces("application/xml")
    public TreatmentRepresentation getProviderTreatment(@PathParam("id") String id, @PathParam("tid") String tid) {
    	try {
    		TreatmentDto treatment = providerService.getTreatment(Long.parseLong(id), Long.parseLong(tid)); 
    		TreatmentRepresentation treatmentRep = new TreatmentRepresentation(treatment, uriInfo);
    		return treatmentRep;
    	} catch (ProviderServiceExn e) {
    		throw new WebApplicationException(Response.Status.NOT_FOUND);
    	}
    }

    /**
     * POST method for creating an instance of ProviderResource
     */
    @POST
    @Consumes("application/xml")
    public Response addProvider(ProviderRepresentation providerRep)  {
    	try {
    		ProviderDto dto = providerDtoFactory.createProviderDto();
    		dto.setNpi(providerRep.getNpi());
    		dto.setName(providerRep.getName());
    		dto.setSpecialization(providerRep.getSpecialization());
    		long id = providerService.addProvider(dto);
    		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path("{id}");
    		URI url = ub.build(Long.toString(id));
    		return Response.created(url).build();
    	} catch (ProviderServiceExn e) {
    		throw new WebApplicationException();
    	}
    }
    
    /**
     * POST method for adding a treatment for a patient treated by this provider
     */
    /*
    @POST
    @Path("{id}/treatments")
    @Consumes("application/xml")
    public Response addTreatment(@PathParam("id") String pid, TreatmentRepresentation treatmentRep)  {
    	try{
    		TreatmentDto treatment = treatmentRep.getTreatment();
    		long npi = this.getProvider(String.valueOf(treatment.getProvider())).getNpi();
    		long id = providerService.addTreatment(treatment, Long.parseLong(pid), npi);
    		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path("{id}");
    		URI url = ub.build(Long.toString(id));
    		return Response.created(url).build();
    	} catch (ProviderServiceExn e) {
    		throw new WebApplicationException();
    	}
    }
    */
    
    @POST
    @Path("{id}/treatments")
    @Consumes("application/xml")
    public Response addTreatment(@PathParam("id") String id, TreatmentRepresentation treatmentRep)  {
    	if (Long.parseLong(id) != Representation.getId(treatmentRep.getLinkProvider())) {
			throw new WebApplicationException();
		}
    	try{
    		TreatmentDto treatment = null;
    		if (treatmentRep.getDrugTreatment() != null) {
    			treatment = treatmentDtoFactory.createDrugTreatmentDto();
    			treatment.setPatient(Representation.getId(treatmentRep.getLinkPatient()));
    			treatment.setProvider(Representation.getId(treatmentRep.getLinkProvider()));
    			treatment.setDiagnosis(treatmentRep.getDiagnosis());
    			DrugTreatmentType drug = new DrugTreatmentType();
    			drug.setName(treatmentRep.getDrugTreatment().getName());
    			drug.setDosage(treatmentRep.getDrugTreatment().getDosage());
    			treatment.setDrugTreatment(drug);
    		} 
    		else if (treatmentRep.getSurgery() != null) {
    			treatment = treatmentDtoFactory.createSurgeryDto();
    			treatment.setPatient(Representation.getId(treatmentRep.getLinkPatient()));
    			treatment.setProvider(Representation.getId(treatmentRep.getLinkProvider()));
    			treatment.setDiagnosis(treatmentRep.getDiagnosis());
    			SurgeryType surgery = new SurgeryType();
    			surgery.setDate(treatmentRep.getSurgery().getDate());
    			treatment.setSurgery(surgery);
    		} 
    		else if (treatmentRep.getRadiology() != null) {
    			treatment = treatmentDtoFactory.createRadiologyDto();
    			treatment.setPatient(Representation.getId(treatmentRep.getLinkPatient()));
    			treatment.setProvider(Representation.getId(treatmentRep.getLinkProvider()));
    			treatment.setDiagnosis(treatmentRep.getDiagnosis());
    			RadiologyType radio = new RadiologyType();
    			radio.setDate(treatmentRep.getRadiology().getDate());
    			treatment.setRadiology(radio);
    		}
    		
    		long npi = this.getProvider(String.valueOf(treatment.getProvider())).getNpi();
    		long pid = patientService.getPatient(treatment.getPatient()).getPatientId();
    		long tid = providerService.addTreatment(treatment, pid, npi);
    		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path("{tid}");
    		URI url = ub.build(Long.toString(tid));
    		return Response.created(url).build();
    	} catch (ProviderServiceExn e) {
    		throw new WebApplicationException();
    	} catch (PatientServiceExn e) {
			throw new WebApplicationException();
		}

    }


}