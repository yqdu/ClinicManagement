package edu.stevens.cs548.clinic.service.web.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.TreatmentNotFoundExn;

@WebService(
	name="IPatientWebPort",
	targetNamespace="http://cs548.stevens.edu/clinic/service/web/soap/patient")
        
@SOAPBinding(
		style=SOAPBinding.Style.DOCUMENT,
		use=SOAPBinding.Use.LITERAL,
		parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)

/*
 * Endpoint interface for the patient Web service.
 */
public interface IPatientWebService {
	
	@WebMethod
	public long addPatient (
			@WebParam(name="patient-dto",
			          targetNamespace="http://cs548.stevens.edu/clinic/dto")
			PatientDto dto) throws PatientServiceExn;

	@WebMethod
	@WebResult(name="patient-dto",
			   targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public PatientDto getPatient(long id) throws PatientServiceExn;
	
	@WebMethod
	@WebResult(name="patient-dto",
	   		   targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public PatientDto getPatientByPatId(long pid) throws PatientServiceExn;
	
	@WebMethod(operationName="patientGetTreatment")
	@WebResult(name="treatment-dto",
	           targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public TreatmentDto getTreatment(long id, long tid) throws PatientNotFoundExn, TreatmentNotFoundExn, PatientServiceExn;

	@WebMethod
	public String siteInfo();

}
