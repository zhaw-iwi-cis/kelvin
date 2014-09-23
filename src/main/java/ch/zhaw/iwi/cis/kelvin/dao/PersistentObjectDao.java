package ch.zhaw.iwi.cis.kelvin.dao;

import java.util.List;

import ch.zhaw.iwi.cis.kelvin.model.ObjectID;
import ch.zhaw.iwi.cis.kelvin.model.PersistentObject;

public interface PersistentObjectDao
{
	public < T extends PersistentObject > void persist( T object );
	public < T extends PersistentObject > void remove( T object );
	
    /**
     * <b>CAREFUL when overriding!</b> {@link javax.persistence.EntityManager}.merge does not add detached objects to
     * the persistence context, but either returns the existing object currently present in the persistence context or a
     * new one if it was not there at the time of the merge.
     * From <a href="http://www.objectdb.com/java/jpa/persistence/detach#Explicit_Merge_">objectdb.com</a>: <br>
     * The content of the specified detached entity object is copied into an existing managed entity object with the same
     * identity (i.e. same type and primary key). If the EntityManager does not manage such an entity object yet a new
     * managed entity object is constructed. The detached object itself, however, remains unchanged and detached.
     * <br><br>
     *
     * <b>Example overriding:</b><br>
     * public <T extends IdentifiableObject> T merge(T object) {
     *      Company company = (Company)object;
     *      List<RawObject> managed = new ArrayList<RawObject>();
     *      for (RawObject ro : company.getRawObjects()) {
     *          managed.add(getEntityManager().merge(ro));
     *      }
     *      company.getRawObjects().clear();
     *      company.getRawObjects().addAll(managed);
     *
     *      return (T)company;
     * }
     *
     * @param object
     * @param <T>
     * @return
     */
	public < T extends PersistentObject > T merge( T object );
	public < T extends PersistentObject > T findById( ObjectID id );
	public < T extends PersistentObject > List< T > findByAll(Class< T > clazz);
}
