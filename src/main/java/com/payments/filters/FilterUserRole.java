package com.payments.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.payments.models.Role;
import com.payments.models.User;
import java.io.IOException;

@WebFilter(filterName = "FilterUserRole", urlPatterns = "/adminPage")
public class FilterUserRole implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        User user = (User) req.getSession().getAttribute("user");
        if (user == null || user.isBlocked() || user.getRole() != Role.ADMIN) {
            resp.sendError(403, "You do not have admin`s status");
        } else {
            chain.doFilter(request, response);
        }

    }
}
