package com.payments.controllers;

import com.payments.data.DBException;
import com.payments.models.Role;
import com.payments.models.User;
import com.payments.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RegisterControllerTest extends Mockito {
@AfterAll
public static void after() throws DBException {
    UserService userService = UserService.getInstance();
    User user = new User("email@test.com","TOPsecret25","name","user surname", Role.USER,false);

    userService.delete(user);
}
    @Test
    public void testRegisterServlet() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession sessionMock = mock(HttpSession.class);
        when(request.getSession()).thenReturn(sessionMock);
        when(request.getParameter("email")).thenReturn("email@test.com");
        when(request.getParameter("password")).thenReturn("TOPsecret25");
        when(request.getParameter("name")).thenReturn("name");
        when(request.getParameter("surname")).thenReturn("user surname");
        Mockito.when(request.getSession()).thenReturn(sessionMock);

        new RegisterController().doPost(request, response);
    }
}