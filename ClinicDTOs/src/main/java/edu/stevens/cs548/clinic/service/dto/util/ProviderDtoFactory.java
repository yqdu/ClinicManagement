package edu.stevens.cs548.clinic.service.dto.util;

import edu.stevens.cs548.clinic.domain.Provider;
import edu.stevens.cs548.clinic.service.dto.ObjectFactory;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;


public class ProviderDtoFactory {

	ObjectFactory factory;
	
	public ProviderDtoFactory(){
		factory = new ObjectFactory();
	}
	
	public ProviderDto createProviderDto(){
		return factory.createProviderDto();
	}
	
	public ProviderDto createProviderDto(Provider prov){
		ProviderDto p = factory.createProviderDto();
		p.setId(prov.getId());
		p.setNpi(prov.getNpi());
		p.setName(prov.getName());
		p.setSpecialization(prov.getSpecialization());
		return p;
	}
}
