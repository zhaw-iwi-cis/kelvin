package ch.zhaw.iwi.cis.kelvin.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import ch.zhaw.iwi.cis.kelvin.framework.service.ServiceRegistry;
import ch.zhaw.iwi.cis.kelvin.model.IdentifiableObject;

@Transactional
public abstract class IdentifiableObjectDaoImpl implements IdentifiableObjectDao
{
	public static final String CLEANTECH_PERSISTENCE_UNIT = "cleantech";
	
	private EntityManager entityManager = ServiceRegistry.getRegistry().getService( CLEANTECH_PERSISTENCE_UNIT );

	public < T extends IdentifiableObject > int persist( T object )
	{
		IdentifiableObject objectMerged = merge( object );
		entityManager.persist( objectMerged );

		return objectMerged.getID();
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
	public < T extends IdentifiableObject > T findById( Integer id )
	{
		return (T)entityManager.find( getPersistentObjectClass(), id );
	}

	@SuppressWarnings( "unchecked" )
	public < T extends IdentifiableObject > List< T > findByAll(Class< T > clazz)
	{
		return entityManager.createQuery( "from " + clazz.getName() ).getResultList();
	}

	protected abstract Class< ? extends IdentifiableObject > getPersistentObjectClass();

	protected EntityManager getEntityManager()
	{
		return entityManager;
	}
}
