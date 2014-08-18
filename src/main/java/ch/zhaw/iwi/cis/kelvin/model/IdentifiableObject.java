package ch.zhaw.iwi.cis.kelvin.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@JsonTypeInfo( use = com.fasterxml.jackson.annotation.JsonTypeInfo.Id.CLASS, include = As.PROPERTY, property = "class" )
@MappedSuperclass
public abstract class IdentifiableObject implements Serializable
{
	// TODO replace 1L with singleton constant.
	private static final long serialVersionUID = 1L;

	// TODO replace int with special UID class.
	@Id
	@GeneratedValue
	private int id;

	public IdentifiableObject()
	{
	}

	public int getID()
	{
		return id;
	}

	@Override
	public final int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public final boolean equals( Object obj )
	{
		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		IdentifiableObject other = (IdentifiableObject)obj;
		if ( id != other.id )
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "IdentifiableObject [id=" + id + "]";
	}
}
