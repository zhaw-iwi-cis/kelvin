package ch.zhaw.iwi.cis.kelvin.service;

import ch.zhaw.iwi.cis.kelvin.model.IdentifiableObject;

public interface IdentifiableObjectService extends Service
{
	public < T extends IdentifiableObject > int persist( T object );
	public < T extends IdentifiableObject > void remove( T object );
	public < T extends IdentifiableObject > T findByID( int id );
}
