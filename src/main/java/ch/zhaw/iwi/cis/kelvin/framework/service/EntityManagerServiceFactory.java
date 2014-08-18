package ch.zhaw.iwi.cis.kelvin.framework.service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class EntityManagerServiceFactory implements ServiceFactory
{
	private EntityManagerFactory emFactory;

	public EntityManagerServiceFactory( EntityManagerFactory emFactory )
	{
		super();
		this.emFactory = emFactory;
	}

	@SuppressWarnings( "unchecked" )
	@Override
	public EntityManager create()
	{
		EntityManager service = emFactory.createEntityManager();
		
		return service;
	}
}
