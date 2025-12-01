package com.isfa.fr.fraudDetection.controller;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/log")
public class LogController {

    private static final Logger LOG = LogManager.getLogger(LogController.class);

    @GetMapping(value = "/echo")
    public String echoMessage() {
        LOG.log(Level.INFO, "Echo Triggered");
        return "Echo Triggered";
    }
}
