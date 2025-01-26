package com.logger.Model.Demo;

import com.logger.Logger;

public class ClassB {
    private final Logger logger;
    public ClassB(Logger logger) {
        this.logger = logger;
    }

    public void work() {
        logger.atDebug("I'm class B at debug");
        logger.atInfo("I'm class B at info");
        logger.atWarning("I'm class B at warning");
        logger.atError("I'm class B at error");
        logger.atFatal("I'm class B at fatal");
    }
}
