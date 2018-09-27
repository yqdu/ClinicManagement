package edu.stevens.cs548.clinic.domain;

public class ProviderFactory implements IProviderFactory {

	// 4 Add a provider (ProviderFactory.createProvider and ProviderDAO.addProvider
	@Override
	public Provider createProvider(long npi, String name, String specilization) {
		Provider p = new Provider();
		p.setNpi(npi);
		p.setName(name);
		p.setSpecialization(specilization);
		return p;
	}

}
