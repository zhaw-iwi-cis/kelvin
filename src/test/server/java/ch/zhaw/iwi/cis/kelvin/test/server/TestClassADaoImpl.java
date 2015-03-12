package ch.zhaw.iwi.cis.kelvin.test.server;

import javax.transaction.Transactional;

import ch.zhaw.iwi.cis.kelvin.dao.PersistentObjectDaoImpl;
import ch.zhaw.iwi.cis.kelvin.framework.service.Service;
import ch.zhaw.iwi.cis.kelvin.model.PersistentObject;

@Transactional
@Service( name = "TestClassADao" )
public class TestClassADaoImpl extends PersistentObjectDaoImpl implements TestClassADao
{

	@Override
	protected Class< ? extends PersistentObject > getPersistentObjectClass()
	{
		return TestClassA.class;
	}
}
