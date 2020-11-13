package org.mentalizr.client.restService;

import de.arthurpicht.utils.core.strings.Strings;
import org.mentalizr.client.RESTCallContext;

public abstract class RestService {

    protected RESTCallContext RESTCallContext;

    public abstract String getServiceName();

    public abstract HttpMethod getMethod();

    public abstract String getBody();

    public abstract String getContentType();

    public boolean hasContentType() {
        return Strings.isNotNullAndNotEmpty(this.getContentType());
    }

}
