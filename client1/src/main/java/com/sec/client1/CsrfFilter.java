package com.sec.client1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CsrfFilter extends OncePerRequestFilter {

    @Autowired
    private CsrfTokenRepository csrfTokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if(request.getRequestURI().equals("/logout") && this.csrfTokenRepository.loadToken(request) == null) {
            request.setAttribute(HttpServletResponse.class.getName(), response);
            response.sendRedirect("/index");
        } else {
            filterChain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
