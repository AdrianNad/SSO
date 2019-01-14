package com.sec.client2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;

@Configuration
@EnableOAuth2Sso
public class ResourceConfig extends WebSecurityConfigurerAdapter {

    @Value("${auth.server.logout}")
    private String authServerLogout;

    @Autowired
    private TokenExpirationFilter tokenExpirationFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(tokenExpirationFilter, AnonymousAuthenticationFilter.class)
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/index**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .logoutSuccessUrl(authServerLogout);
    }
}
