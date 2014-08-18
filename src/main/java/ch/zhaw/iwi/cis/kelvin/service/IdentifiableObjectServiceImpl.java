package ch.zhaw.iwi.cis.kelvin.service;

import javax.transaction.Transactional;

import ch.zhaw.iwi.cis.kelvin.dao.IdentifiableObjectDao;
import ch.zhaw.iwi.cis.kelvin.model.IdentifiableObject;

@Transactional
public abstract class IdentifiableObjectServiceImpl implements IdentifiableObjectService
{
	@Override
	public < T extends IdentifiableObject > int persist( T object )
	{
		return getIdentifiableObjectDao().persist( object );
	}

	@Override
	public < T extends IdentifiableObject > void remove( T object )
	{
		getIdentifiableObjectDao().remove( object );
	}

	@Override
	public < T extends IdentifiableObject > T findByID( int id )
	{
		return getIdentifiableObjectDao().findById( id );
	}
	
	public < T extends IdentifiableObject > T findByIDDelegate( Integer id ) {
		return findByID( id );
	}
	
	protected abstract IdentifiableObjectDao getIdentifiableObjectDao();
}
