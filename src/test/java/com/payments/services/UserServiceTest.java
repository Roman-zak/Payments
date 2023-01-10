package com.payments.services;

import com.payments.data.DBException;
import com.payments.models.Role;
import com.payments.models.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UserServiceTest {
     static UserService instance;
//    static {
//        instance = UserService.getInstance();
//        instance.userDAO = new UserDAO();
//    }

   // private HibernateTemplate hibernateTemplateMock;

    @BeforeAll
    public static void before(){
        instance = UserService.getInstance();
     //   this.hibernateTemplateMock = mock( HibernateTemplate.class );
     //   this.instance.dao.setHibernateTemplate( this.hibernateTemplateMock );
    }
    @AfterAll
    public static void after() throws DBException {
        User user = new User( "email@mail.com", "password", "name", "surname", Role.USER, false );
        User user1 = new User( "email1@mail.com", "password", "name", "surname", Role.USER, false );

        instance.delete( user );
        instance.delete( user1 );
    }

    @Test
    public void whenCreateIsTriggered_thenNoException() throws DBException {
        User user = new User( "email@mail.com", "password", "name", "surname", Role.USER, false );
        instance.save( user );

    }

    @Test
    public void whenCreateIsTriggeredForNullEntity_thenException() throws DBException {
        Assertions.assertThrows(NullPointerException.class, () -> {
            this.instance.save( null );
        });
    }

    @Test
    public void whenCreateIsTriggered_thenEntityIsCreated() throws DBException {
        User user = new User( "email1@mail.com", "password", "name", "surname", Role.USER, false );
        instance.save( user );
        Assertions.assertEquals(instance.get("email1@mail.com"), user);
    }
}