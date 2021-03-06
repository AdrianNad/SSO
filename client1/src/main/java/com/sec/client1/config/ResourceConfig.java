package com.sec.client1.config;

import com.sec.client1.config.filter.CsrfFilter;
import com.sec.client1.config.filter.TokenExpirationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableOAuth2Sso
public class ResourceConfig extends WebSecurityConfigurerAdapter {

    @Value("${auth.server.logout}")
    private String authServerLogout;

    @Autowired
    private TokenExpirationFilter tokenExpirationFilter;

    @Autowired
    private CsrfFilter csrfFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(tokenExpirationFilter, AnonymousAuthenticationFilter.class)
                .addFilterBefore(csrfFilter, org.springframework.security.web.csrf.CsrfFilter.class)
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/index**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .logoutSuccessUrl(authServerLogout)
                .and().csrf().csrfTokenRepository(csrfTokenRepository());
    }

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        return new HttpSessionCsrfTokenRepository();
    }
}
