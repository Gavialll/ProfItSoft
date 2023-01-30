package com.example.block_4.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@Order(2)
public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;

        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute("login") == null || session.getAttribute("password") == null){
            request.getSession()
                    .getServletContext()
                    .getRequestDispatcher("/login")
                    .forward(request, res);
            return;
        }
        chain.doFilter(request, res);
    }
}
