package org.rest.poc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import org.rest.common.IEntity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@Entity
@XmlRootElement
@XStreamAlias( "user" )
public class User implements IEntity{
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	@Column( unique = true,nullable = false )
	private String name;
	
	public User(){
		super();
	}
	public User( final String nameToSet ){
		super();
		
		name = nameToSet;
	}
	
	// API
	
	@Override
	public Long getId(){
		return id;
	}
	@Override
	public void setId( final Long idToSet ){
		id = idToSet;
	}
	
	public String getName(){
		return name;
	}
	public void setName( final String nameToSet ){
		name = nameToSet;
	}
	
	//
	
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( name == null ) ? 0 : name.hashCode() );
		return result;
	}
	@Override
	public boolean equals( final Object obj ){
		if( this == obj )
			return true;
		if( obj == null )
			return false;
		if( this.getClass() != obj.getClass() )
			return false;
		final User other = (User) obj;
		if( name == null ){
			if( other.name != null )
				return false;
		}
		else if( !name.equals( other.name ) )
			return false;
		return true;
	}
	
	@Override
	public String toString(){
		return "User [name=" + name + "]";
	}
	
}
