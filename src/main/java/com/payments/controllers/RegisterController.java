package com.payments.controllers;

import com.payments.dao.DAO;
import com.payments.dao.UserDAO;
import com.payments.data.DBException;
import com.payments.models.Role;
import com.payments.models.User;
import com.payments.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Set;

@WebServlet(name = "register", value = "/register")
public class RegisterController extends HttpServlet {
    UserService userService = UserService.getInstance();
    private final Logger logger = Logger.getLogger(RegisterController.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = null;
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        DAO<User> userDAO = new UserDAO();
        user = new User(email, password, name, surname, Role.USER, false);

        Validator validator = null;
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            validator = validatorFactory.getValidator();
        }catch (Exception e){
            logger.error(e);
        }
        Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
        if (!constraintViolations.isEmpty()) {
            String errors = "<ul>";
            for (ConstraintViolation<User> constraintViolation : constraintViolations) {
                errors += "<li>" + constraintViolation.getPropertyPath() + " " + constraintViolation.getMessage()
                        + "</li>";
            }
            errors += "</ul>";
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("WEB-INF/register.jsp").forward(request, response);
        } else {
            try {
                userService.save(user);
            } catch (DBException e) {
                response.sendError(403, "Was unable to register this user.");
            }
            request.getSession().setAttribute("user", user);
            response.sendRedirect("/");
        }
    }
}
