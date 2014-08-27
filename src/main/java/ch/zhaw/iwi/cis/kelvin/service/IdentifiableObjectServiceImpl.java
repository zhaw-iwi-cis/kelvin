package ch.zhaw.iwi.cis.kelvin.service;

import javax.transaction.Transactional;

import ch.zhaw.iwi.cis.kelvin.dao.IdentifiableObjectDao;
import ch.zhaw.iwi.cis.kelvin.model.IdentifiableObject;
import ch.zhaw.iwi.cis.kelvin.model.ObjectID;

@Transactional
public abstract class IdentifiableObjectServiceImpl implements IdentifiableObjectService
{
	@Override
	public < T extends IdentifiableObject > void persist( T object )
	{
		getIdentifiableObjectDao().persist( object );
	}

	@Override
	public < T extends IdentifiableObject > void remove( T object )
	{
		getIdentifiableObjectDao().remove( object );
	}

	@Override
	public < T extends IdentifiableObject > T findByID( ObjectID id )
	{
		return getIdentifiableObjectDao().findById( id );
	}
	
	protected abstract IdentifiableObjectDao getIdentifiableObjectDao();
}
