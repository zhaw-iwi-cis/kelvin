package ch.zhaw.iwi.cis.kelvin.service;

import javax.transaction.Transactional;

import ch.zhaw.iwi.cis.kelvin.dao.PersistentObjectDao;
import ch.zhaw.iwi.cis.kelvin.model.ObjectID;
import ch.zhaw.iwi.cis.kelvin.model.PersistentObject;

@Transactional
public abstract class PersistentObjectServiceImpl implements PersistentObjectService
{
	@Override
	public < T extends PersistentObject > void persist( T object )
	{
		getPersistentObjectObjectDao().persist( object );
	}

	@Override
	public < T extends PersistentObject > void remove( T object )
	{
		getPersistentObjectObjectDao().remove( object );
	}

	@Override
	public < T extends PersistentObject > T findByID( ObjectID id )
	{
		return getPersistentObjectObjectDao().findById( id );
	}
	
	protected abstract PersistentObjectDao getPersistentObjectObjectDao();
}
