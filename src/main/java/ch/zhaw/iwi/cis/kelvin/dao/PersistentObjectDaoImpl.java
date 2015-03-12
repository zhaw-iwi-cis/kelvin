package ch.zhaw.iwi.cis.kelvin.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ch.zhaw.iwi.cis.kelvin.framework.KelvinConfig;
import ch.zhaw.iwi.cis.kelvin.framework.service.ServiceRegistry;
import ch.zhaw.iwi.cis.kelvin.model.ObjectID;
import ch.zhaw.iwi.cis.kelvin.model.PersistentObject;

@Transactional
public abstract class PersistentObjectDaoImpl implements PersistentObjectDao
{
	private EntityManager entityManager = ServiceRegistry.getRegistry().getService( KelvinConfig.getConfig().getPersistenceUnitName() );
	
	public < T extends PersistentObject > void persist( T object )
	{
		PersistentObject objectMerged = merge( object );
		entityManager.persist( objectMerged );
	}

	public < T extends PersistentObject > void remove( T object )
	{
		PersistentObject objectMerged = entityManager.merge( object );
		entityManager.remove( objectMerged );
	}

	public < T extends PersistentObject > T merge( T object )
	{
		return entityManager.merge( object );
	}

	@SuppressWarnings( "unchecked" )
	public < T extends PersistentObject > T findById( ObjectID id )
	{
		return (T)entityManager.find( getPersistentObjectClass(), id );
	}

	@SuppressWarnings( "unchecked" )
	public < T extends PersistentObject > List< T > findByAll( Class< T > clazz )
	{
		return entityManager.createQuery( "from " + clazz.getName() ).getResultList();
	}

	protected abstract Class< ? extends PersistentObject > getPersistentObjectClass();

	protected EntityManager getEntityManager()
	{
		return entityManager;
	}
}
