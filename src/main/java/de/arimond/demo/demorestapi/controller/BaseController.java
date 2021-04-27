package de.arimond.demo.demorestapi.controller;

import org.springframework.context.MessageSource;

/**
 * @author Ben Arimond
 */

public class BaseController {

    protected MessageSource messageSource;

    public BaseController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
