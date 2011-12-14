package org.rest.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author eugenp
 */
@Entity
@XmlRootElement
public class Bar implements Serializable{
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	@Column( unique = true,nullable = false )
	private String name;
	
	public Bar(){
		super();
	}
	public Bar( final String nameToSet ){
		super();
		
		this.name = nameToSet;
	}
	
	// API
	
	public Long getId(){
		return this.id;
	}
	public void setId( final Long idToSet ){
		this.id = idToSet;
	}
	
	public String getName(){
		return this.name;
	}
	public void setName( final String nameToSet ){
		this.name = nameToSet;
	}
	
	//
	
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( this.name == null ) ? 0 : this.name.hashCode() );
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
		final Bar other = (Bar) obj;
		if( this.name == null ){
			if( other.name != null )
				return false;
		}
		else if( !this.name.equals( other.name ) )
			return false;
		return true;
	}
	
	@Override
	public String toString(){
		return "Foo [name=" + this.name + "]";
	}
}
