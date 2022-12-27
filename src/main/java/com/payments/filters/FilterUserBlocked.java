package com.payments.filters;

import com.payments.models.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "FilterUserBlocked", urlPatterns = "/*")
public class FilterUserBlocked implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        User user = (User) req.getSession().getAttribute("user");
        if (user!=null && user.isBlocked()) {
            req.getSession().setAttribute("user", null);
            resp.sendError(403, "Your user account is blocked, refer to this email for details:romanzak23@gmail.com");
        } else {
            chain.doFilter(request, response);
        }
    }
}
