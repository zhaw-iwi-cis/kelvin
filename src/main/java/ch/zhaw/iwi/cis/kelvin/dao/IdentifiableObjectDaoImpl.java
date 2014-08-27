package ch.zhaw.iwi.cis.kelvin.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ch.zhaw.iwi.cis.kelvin.framework.KelvinConfig;
import ch.zhaw.iwi.cis.kelvin.framework.service.ServiceRegistry;
import ch.zhaw.iwi.cis.kelvin.model.IdentifiableObject;
import ch.zhaw.iwi.cis.kelvin.model.ObjectID;

@Transactional
public abstract class IdentifiableObjectDaoImpl implements IdentifiableObjectDao
{
	private EntityManager entityManager = ServiceRegistry.getRegistry().getService( KelvinConfig.getConfig().getPersistenceUnitName() );

	public < T extends IdentifiableObject > void persist( T object )
	{
		IdentifiableObject objectMerged = merge( object );
		entityManager.persist( objectMerged );
	}

	public < T extends IdentifiableObject > void remove( T object )
	{
		IdentifiableObject objectMerged = entityManager.merge( object );
		entityManager.remove( objectMerged );
	}

	public < T extends IdentifiableObject > T merge( T object )
	{
		return entityManager.merge( object );
	}

	@SuppressWarnings( "unchecked" )
	public < T extends IdentifiableObject > T findById( ObjectID id )
	{
		return (T)entityManager.find( getPersistentObjectClass(), id );
	}

	@SuppressWarnings( "unchecked" )
	public < T extends IdentifiableObject > List< T > findByAll( Class< T > clazz )
	{
		return entityManager.createQuery( "from " + clazz.getName() ).getResultList();
	}

	protected abstract Class< ? extends IdentifiableObject > getPersistentObjectClass();

	protected EntityManager getEntityManager()
	{
		return entityManager;
	}
}
