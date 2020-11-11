package org.mentalizr.client.restService;

import de.arthurpicht.utils.io.urlEncoding.URLEncoderUtil;
import org.mentalizr.cli.ContentType;

import java.util.HashMap;
import java.util.Map;

public class LoginService extends RestService {

    private final String user;
    private final String password;

    public LoginService(String user, String password) {
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
    public String getBody() {
        Map<String, String> keyValuePairs = new HashMap<>();
        keyValuePairs.put("user", this.user);
        keyValuePairs.put("password", this.password);
        keyValuePairs.put("rememberMe", "false");

        return URLEncoderUtil.xWWWFormEncode(keyValuePairs);
    }

    @Override
    public String getContentType() {
        return ContentType.X_WWW_FORM_URLENCODED;
    }
}
