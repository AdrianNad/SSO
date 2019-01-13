package com.sec.client2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class TokenExpirationFilter implements Filter {

    @Autowired
    private OAuth2ClientContext oAuth2ClientContext;

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (oAuth2ClientContext != null) {
            OAuth2AccessToken accessToken = oAuth2ClientContext.getAccessToken();
            if (accessToken != null && accessToken.isExpired()) {
                SecurityContextHolder.getContext().setAuthentication(null);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
