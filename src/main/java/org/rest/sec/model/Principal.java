package org.rest.sec.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.rest.common.IEntity;

@Entity
@XmlRootElement
public class Principal implements IEntity{
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;
	
	@Column( unique = true,nullable = false )
	private String name;
	
	@Column( nullable = false )
	private String password;
	
	/* Persistence */
	@OneToMany( fetch = FetchType.EAGER )
	@JoinColumn( name = "ROLE_ID" )
	@Column( nullable = false )
	private Set< Role > roles;
	
	public Principal(){
		super();
	}
	public Principal( final String nameToSet, final String passwordToSet, final Set< Role > rolesToSet ){
		super();
		
		name = nameToSet;
		password = passwordToSet;
		roles = rolesToSet;
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
	
	public String getPassword(){
		return password;
	}
	public void setPassword( final String passwordToSet ){
		password = passwordToSet;
	}
	
	public Set< Role > getRoles(){
		return roles;
	}
	public void setRoles( final Set< Role > rolesToSet ){
		roles = rolesToSet;
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
		if( getClass() != obj.getClass() )
			return false;
		final Principal other = (Principal) obj;
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
		return new ToStringBuilder( this ).append( "id", id ).append( "name", name ).toString();
	}
	
}
