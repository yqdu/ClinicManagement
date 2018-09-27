package edu.stevens.cs548.clinic.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2017-03-24T22:46:28.216-0400")
@StaticMetamodel(Provider.class)
public class Provider_ {
	public static volatile SingularAttribute<Provider, Long> id;
	public static volatile SingularAttribute<Provider, Long> npi;
	public static volatile SingularAttribute<Provider, String> name;
	public static volatile SingularAttribute<Provider, String> specialization;
	public static volatile ListAttribute<Provider, Treatment> treatments;
}
