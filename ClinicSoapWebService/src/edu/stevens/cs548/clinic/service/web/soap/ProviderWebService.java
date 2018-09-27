package edu.stevens.cs548.clinic.service.web.soap;

import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.TreatmentNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceLocal;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceRemote;


@WebService(
		endpointInterface = "edu.stevens.cs548.clinic.service.web.soap.IProviderWebService", 
		targetNamespace = "http://cs548.stevens.edu/clinic/service/web/soap/provider", 
		serviceName = "ProviderWebService", portName = "ProviderWebPort")

@SOAPBinding(
		style=SOAPBinding.Style.DOCUMENT,
		use=SOAPBinding.Use.LITERAL,
		parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)

public class ProviderWebService implements IProviderWebService {

	@EJB(beanName="ProviderServiceBean")
	IProviderServiceLocal service;
	
	@Override
	public long addProvider(ProviderDto prov) throws ProviderServiceExn {
		return service.addProvider(prov);
	}

	@Override
	public ProviderDto getProvider(long id) throws ProviderServiceExn {
		return service.getProvider(id);
	}

	@Override
	public ProviderDto getProviderByNPI(long npi) throws ProviderServiceExn {
		return service.getProviderByNPI(npi);
	}

	@Override
	public long addTreatment(TreatmentDto treatment, long pid, long npi)
			throws TreatmentNotFoundExn, PatientNotFoundExn, ProviderServiceExn {
		return service.addTreatment(treatment, pid, npi);
	}

	@Override
	public TreatmentDto getTreatment(long id, long tid) throws TreatmentNotFoundExn, ProviderServiceExn {
		return service.getTreatment(id, tid);
	}

	@Override
	public String siteInfo() {
		return service.siteInfo();
	}

}
