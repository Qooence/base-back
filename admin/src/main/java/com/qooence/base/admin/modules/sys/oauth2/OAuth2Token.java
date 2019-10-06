package com.qooence.base.admin.modules.sys.oauth2;


import org.apache.shiro.authc.AuthenticationToken;

/**
 * token
 * @author jyq
 * @email qooence@qooence.uu.me
 */
public class OAuth2Token implements AuthenticationToken {
    private String token;

    public OAuth2Token(String token){
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
