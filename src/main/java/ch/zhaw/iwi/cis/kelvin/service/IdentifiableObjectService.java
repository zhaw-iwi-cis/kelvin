package ch.zhaw.iwi.cis.kelvin.service;

import ch.zhaw.iwi.cis.kelvin.model.IdentifiableObject;
import ch.zhaw.iwi.cis.kelvin.model.ObjectID;

public interface IdentifiableObjectService extends Service
{
	public < T extends IdentifiableObject > void persist( T object );
	public < T extends IdentifiableObject > void remove( T object );
	public < T extends IdentifiableObject > T findByID( ObjectID id );
}
