package com.logger.Model.Demo;

import com.logger.Logger;

public class ClassC {
    private final Logger logger;
    public ClassC(Logger logger) {
        this.logger = logger;
    }

    public void work() {
        logger.atDebug("I'm class C at debug");
        logger.atInfo("I'm class C at info");
        logger.atWarning("I'm class C at warning");
        logger.atError("I'm class C at error");
        logger.atFatal("I'm class C at fatal");
    }
}
