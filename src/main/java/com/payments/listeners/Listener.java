package com.payments.listeners;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.apache.log4j.Logger;

@WebListener
public class Listener implements  HttpSessionListener, HttpSessionAttributeListener, ServletRequestListener {
    private final Logger logger = Logger.getLogger(Listener.class);

    public Listener() {
    }


    @Override
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
        logger.info("Session Created:: ID="+se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
        logger.info("Session Destroyed:: ID="+se.getSession().getId());
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        logger.info("ServletContext attribute added::{"+sbe.getName()+","+sbe.getValue()+"}");
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        logger.info("ServletContext attribute removed::{"+sbe.getName()+","+sbe.getValue()+"}");
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        logger.info("ServletContext attribute replaced::{"+sbe.getName()+","+sbe.getValue()+"}");
    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        ServletRequest servletRequest = sre.getServletRequest();
        logger.info("ServletRequest destroyed. Remote IP="+servletRequest.getRemoteAddr());

    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        ServletRequest servletRequest = sre.getServletRequest();
        logger.info("ServletRequest initialized. Remote IP="+servletRequest.getRemoteAddr());
    }
}
