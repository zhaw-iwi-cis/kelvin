package ch.zhaw.iwi.cis.kelvin.model;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;

import ch.zhaw.iwi.cis.kelvin.framework.KelvinObjectIDResolver;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@JsonTypeInfo( use = com.fasterxml.jackson.annotation.JsonTypeInfo.Id.CLASS, include = As.PROPERTY, property = "class" )
@JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", resolver = KelvinObjectIDResolver.class )
@MappedSuperclass
public abstract class IdentifiableObject implements Serializable
{
	// TODO replace 1L with singleton constant.
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ObjectID id = new ObjectID();

	public IdentifiableObject()
	{
	}

	public ObjectID getId()
	{
		return id;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
		return result;
	}

	@Override
	public boolean equals( Object obj )
	{
		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		IdentifiableObject other = (IdentifiableObject)obj;
		if ( id == null )
		{
			if ( other.id != null )
				return false;
		}
		else if ( !id.equals( other.id ) )
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "IdentifiableObject [id=" + id + "]";
	}
}
