package com.logger.Model.Demo;

import com.logger.Logger;

public class ClassA {
    private final Logger logger;
    public ClassA(Logger logger) {
        this.logger = logger;
    }

    public void work() {
        logger.atDebug("I'm class A at debug");
        logger.atInfo("I'm class A at info");
        logger.atWarning("I'm class A at warning");
        logger.atError("I'm class A at error");
        logger.atFatal("I'm class A at fatal");
    }
}
