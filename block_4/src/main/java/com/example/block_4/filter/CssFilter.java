package com.example.block_4.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Order(1)
public class CssFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;

        if(request.getRequestURI().startsWith("/css") && request.getRequestURI().endsWith(".css")){
            request.getSession()
                    .getServletContext()
                    .getRequestDispatcher(request.getRequestURI())
                    .forward(request, res);
            return;
        }
        chain.doFilter(request, res);
    }
}
