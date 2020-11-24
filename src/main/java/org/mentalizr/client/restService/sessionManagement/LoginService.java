package org.mentalizr.client.restService.sessionManagement;

import de.arthurpicht.utils.io.urlEncoding.URLEncoderUtil;
import org.mentalizr.cli.ContentType;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.HttpMethod;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;

import java.util.HashMap;
import java.util.Map;

public class LoginService extends RestService {

    private final String user;
    private final String password;

    public LoginService(String user, String password, RESTCallContext restCallContext) {
        super(restCallContext);
        this.user = user;
        this.password = password;
    }

    @Override
    public String getServiceName() {
        return "login";
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.POST;
    }

    @Override
    public String getRequestBody() {
        Map<String, String> keyValuePairs = new HashMap<>();
        keyValuePairs.put("user", this.user);
        keyValuePairs.put("password", this.password);
        keyValuePairs.put("rememberMe", "false");

        return URLEncoderUtil.xWWWFormEncode(keyValuePairs);
    }

    @Override
    public String getRequestContentType() {
        return ContentType.X_WWW_FORM_URLENCODED;
    }

    @Override
    public String call() throws RestServiceHttpException, RestServiceConnectionException {
        return RestServiceCaller.call(this.restCallContext, this);
    }
}
