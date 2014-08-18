package ch.zhaw.iwi.cis.kelvin.framework.service;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class TransactionalAspect
{
	@Before( "within( @javax.transaction.Transactional * ) && execution( * *(..) ) || execution( @javax.transaction.Transactional * *(..) )" )
	public void beforeTransactionalAnnotation()
	{
		TransactionManagerService service = ServiceRegistry.getRegistry().getService( TransactionManagerService.SERVICE_NAME );
		service.beginTransactionalBoundary();
	}

	@After( "within( @javax.transaction.Transactional * ) && execution( * *(..) ) || execution( @javax.transaction.Transactional * *(..) )" )
	public void afterTransactionalAnnotation()
	{
		TransactionManagerService service = ServiceRegistry.getRegistry().getService( TransactionManagerService.SERVICE_NAME );
		service.endTransactionalBoundary();
	}
}
