package ch.zhaw.iwi.cis.kelvin.service;

import java.util.List;

import javax.transaction.Transactional;

import __java.lang.__Class;
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
	
	// NEW:
	@Override
	@SuppressWarnings("unchecked")
	public < T extends PersistentObject > List< T > findByAll( String className ) {
		Class< PersistentObject > theClass = (Class< PersistentObject >)__Class.forName( null, className );
		List< T > results = (List< T >)getPersistentObjectObjectDao().findByAll( theClass );
		
		return results;
	}
	
	protected abstract PersistentObjectDao getPersistentObjectObjectDao();
}
