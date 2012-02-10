package org.rest.web.marshalling;

import org.hibernate.collection.internal.PersistentMap;

import com.thoughtworks.xstream.converters.collections.MapConverter;
import com.thoughtworks.xstream.mapper.Mapper;

public class HibernateMapConverter extends MapConverter{
	
	public HibernateMapConverter( final Mapper mapper ){
		super( mapper );
	}
	
	@Override
	public boolean canConvert( @SuppressWarnings( "rawtypes" ) final Class type ){
		return super.canConvert( type ) || PersistentMap.class.equals( type );
	}
	
}
