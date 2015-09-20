package org.baeldung.um.client.marshall;

import java.util.List;

import org.baeldung.client.marshall.IMarshaller;
import org.baeldung.um.persistence.model.Principal;
import org.baeldung.um.persistence.model.Privilege;
import org.baeldung.um.persistence.model.Role;
import org.baeldung.um.web.dto.UserDto;
import org.springframework.http.MediaType;

import com.google.common.base.Preconditions;
import com.thoughtworks.xstream.XStream;

public final class XStreamMarshaller implements IMarshaller {

    private XStream xstream;

    public XStreamMarshaller() {
        super();

        xstream = new XStream();
        xstream.autodetectAnnotations(true);
        xstream.processAnnotations(UserDto.class);
        xstream.processAnnotations(Principal.class);
        xstream.processAnnotations(Role.class);
        xstream.processAnnotations(Privilege.class);
    }

    // API

    @Override
    public final <T> String encode(final T resource) {
        Preconditions.checkNotNull(resource);
        return xstream.toXML(resource);
    }

    @SuppressWarnings("unchecked")
    @Override
    public final <T> T decode(final String resourceAsString, final Class<T> clazz) {
        Preconditions.checkNotNull(resourceAsString);
        return (T) xstream.fromXML(resourceAsString);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> decodeList(final String resourcesAsString, final Class<T> clazz) {
        return this.decode(resourcesAsString, List.class);
    }

    @Override
    public final String getMime() {
        return MediaType.APPLICATION_XML.toString();
    }

}
