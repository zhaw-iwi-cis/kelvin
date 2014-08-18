package ch.zhaw.iwi.cis.kelvin.framework.service;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

@Service( name=TransactionManagerService.SERVICE_NAME )
public class TransactionManagerService
{
	public static final String SERVICE_NAME = "__TX_MANAGER";
	
	private int transactionalBoundaryCounter = 0;
	
	public void beginTransactionalBoundary()
	{
		if ( transactionalBoundaryCounter == 0 )
			beginTransaction();

		++transactionalBoundaryCounter;
	}
	
	public void endTransactionalBoundary()
	{
		--transactionalBoundaryCounter;

		if ( transactionalBoundaryCounter == 0 )
			endTransaction();
	}
	
	private void beginTransaction()
	{
		for ( String serviceName : getEntityManagerServiceNames() )
		{
			EntityManager entityManager = ServiceRegistry.getRegistry().getService( serviceName );
			EntityTransaction tx = entityManager.getTransaction();
			
			entityManager.clear();
			
			if ( tx.isActive() )
				throw new IllegalStateException( "Tried to begin transaction that was already open: " + tx );

			tx.begin();
		}
	}			
	
	private void endTransaction()
	{
		Set< EntityTransaction > txs = new HashSet< EntityTransaction >();
		boolean rollback = false;
		
		for ( String serviceName : getEntityManagerServiceNames() )
		{
			EntityManager entityManager = ServiceRegistry.getRegistry().getService( serviceName );
			EntityTransaction tx = entityManager.getTransaction();
			
			if ( !tx.isActive() )
				throw new IllegalStateException( "Tried to commit transaction that was not previosly opened: " + tx );
			
			txs.add( tx );

			if ( tx.getRollbackOnly() )
				rollback = true;
		}
		
		if ( rollback )
		{
			for ( EntityTransaction tx : txs )
				tx.rollback();
		}
		else
		{
			for ( EntityTransaction tx : txs )
				tx.commit();
		}
	}

	private static Set< String > entityManagerServiceNames;
	
	private static Set< String > getEntityManagerServiceNames()
	{
		if ( entityManagerServiceNames == null )
			entityManagerServiceNames = ServiceRegistry.getRegistry().getServiceNamesWithInterface( EntityManager.class );
		
		return entityManagerServiceNames;
	}
}
