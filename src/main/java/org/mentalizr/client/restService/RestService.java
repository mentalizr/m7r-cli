package org.mentalizr.client.restService;

import de.arthurpicht.utils.core.strings.Strings;

public abstract class RestService {

    public abstract String getServiceName();

    public abstract HttpMethod getMethod();

    public abstract String getBody();

    public abstract String getContentType();

    public boolean hasContentType() {
        return Strings.isNotNullAndNotEmpty(this.getContentType());
    }

}
