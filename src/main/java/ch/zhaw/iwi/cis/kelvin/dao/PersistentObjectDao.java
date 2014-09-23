package ch.zhaw.iwi.cis.kelvin.dao;

import java.util.List;

import ch.zhaw.iwi.cis.kelvin.model.ObjectID;
import ch.zhaw.iwi.cis.kelvin.model.PersistentObject;

public interface PersistentObjectDao
{
	public < T extends PersistentObject > void persist( T object );
	public < T extends PersistentObject > void remove( T object );
	public < T extends PersistentObject > T merge( T object );
	public < T extends PersistentObject > T findById( ObjectID id );
	public < T extends PersistentObject > List< T > findByAll(Class< T > clazz);
}
