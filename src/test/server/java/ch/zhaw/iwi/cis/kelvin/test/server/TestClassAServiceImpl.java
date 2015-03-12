package ch.zhaw.iwi.cis.kelvin.test.server;

import javax.transaction.Transactional;

import ch.zhaw.iwi.cis.kelvin.dao.PersistentObjectDao;
import ch.zhaw.iwi.cis.kelvin.framework.service.Service;
import ch.zhaw.iwi.cis.kelvin.framework.service.ServiceRegistry;
import ch.zhaw.iwi.cis.kelvin.service.PersistentObjectServiceImpl;

@Transactional
@Service( name = "TestClassAService" )
public class TestClassAServiceImpl extends PersistentObjectServiceImpl implements TestClassAService
{

	private TestClassADao	tcADao	= ServiceRegistry.getRegistry().getService( TestClassADao.class.getSimpleName() );

	@Override
	protected PersistentObjectDao getPersistentObjectObjectDao()
	{
		return tcADao;
	}

}
