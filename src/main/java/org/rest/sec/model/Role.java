package org.rest.sec.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.rest.common.IEntity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@Entity
@XmlRootElement
@XStreamAlias( "role" )
public class Role implements IEntity{
	
	@Id @GeneratedValue( strategy = GenerationType.AUTO ) @XStreamAsAttribute @Column( name = "ROLE_ID" ) private Long id;
	@Column( unique = true,nullable = false ) private String name;
	@OneToMany( fetch = FetchType.EAGER )/*@JoinColumn( name = "PRIV_ID" )*/@XStreamImplicit private Set< Privilege > privileges;
	
	public Role(){
		super();
	}
	public Role( final String nameToSet ){
		super();
		
		name = nameToSet;
	}
	public Role( final String nameToSet, final Set< Privilege > privilegesToSet ){
		super();
		
		name = nameToSet;
		privileges = privilegesToSet;
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
	
	public Set< Privilege > getPrivileges(){
		return privileges;
	}
	public void setPrivileges( final Set< Privilege > privilegesToSet ){
		privileges = privilegesToSet;
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
		final Role other = (Role) obj;
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
