package ch.zhaw.iwi.cis.kelvin.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Embeddable;

import ch.zhaw.iwi.cis.kelvin.framework.KelvinObjectIDResolver;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@JsonTypeInfo( use = com.fasterxml.jackson.annotation.JsonTypeInfo.Id.CLASS, include = As.PROPERTY, property = "class" )
@JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", resolver = KelvinObjectIDResolver.class )
@Embeddable
public class ObjectID implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String id;

	public ObjectID()
	{
		super();
		this.id = UUID.randomUUID().toString();
	}

	public String getID()
	{
		return id;
	}

	public void setID( String id )
	{
		this.id = id;
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
		ObjectID other = (ObjectID)obj;
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
		return id.toString();
	}
}
