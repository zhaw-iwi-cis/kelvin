package ch.zhaw.iwi.cis.kelvin.dao;

import java.util.List;

import ch.zhaw.iwi.cis.kelvin.model.IdentifiableObject;
import ch.zhaw.iwi.cis.kelvin.model.ObjectID;

public interface IdentifiableObjectDao
{
	public < T extends IdentifiableObject > void persist( T object );
	public < T extends IdentifiableObject > void remove( T object );
	public < T extends IdentifiableObject > T merge( T object );
	public < T extends IdentifiableObject > T findById( ObjectID id );
	public < T extends IdentifiableObject > List< T > findByAll(Class< T > clazz);
}
