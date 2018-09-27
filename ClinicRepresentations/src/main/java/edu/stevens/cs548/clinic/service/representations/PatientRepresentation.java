package edu.stevens.cs548.clinic.service.representations;

import java.util.List;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlRootElement;

import edu.stevens.cs548.clinic.domain.PatientFactory;
import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.util.PatientDtoFactory;
import edu.stevens.cs548.clinic.service.web.rest.data.PatientType;
import edu.stevens.cs548.clinic.service.web.rest.data.dap.LinkType;

@XmlRootElement
public class PatientRepresentation extends PatientType {

	public List<LinkType> getLinksTreatments() {
		return this.getTreatments();
	}
	
	public static LinkType getPatientLink(long id, UriInfo uriInfo) {
		UriBuilder ub = uriInfo.getBaseUriBuilder();
		ub.path("patient").path("{id}");
		String patientURI = ub.build(Long.toString(id)).toString();

		LinkType link = new LinkType();
		link.setUrl(patientURI);
		link.setRelation(Representation.RELATION_PATIENT);
		link.setMediaType(Representation.MEDIA_TYPE);
		return link;
	}
	
	private PatientDtoFactory patientDtoFactory;
	
	public PatientRepresentation () {
		super();
		this.patientDtoFactory = new PatientDtoFactory();
	}
	
	public PatientRepresentation (PatientDto dto, UriInfo uriInfo) {
		this();
		this.id = getPatientLink(dto.getId(), uriInfo);
		this.patientId =  dto.getPatientId();
		this.name = dto.getName();
		this.dob = dto.getDob();
		this.age = PatientFactory.computeAge(dto.getDob());
		/*
		 * Call getTreatments to initialize empty list.
		 */
		List<LinkType> links = this.getTreatments();
		for (long t : dto.getTreatments()) {
			links.add(TreatmentRepresentation.getTreatmentLink(t, uriInfo));
		}
	}
	
	public PatientDto getPatientDto() {
		PatientDto p = patientDtoFactory.createPatientDto();
		p.setId(Representation.getId(this.id));
		p.setPatientId(this.patientId);
		p.setName(this.name);
		p.setDob(this.dob);
		return p;
	}

}
