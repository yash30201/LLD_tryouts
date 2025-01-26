package com.logger;

import com.Runner.Runner;
import com.logger.Model.Demo.ClassA;
import com.logger.Model.Demo.ClassB;
import com.logger.Model.Demo.ClassC;
import com.logger.Model.Level;

public class LoggerDemo implements Runner {

    @Override
    public void run() {
        System.out.println("Creating default logger 1 and using it at class A");
        Logger logger1 = Logger.getInstance();
        ClassA a = new ClassA(logger1);
        a.work();

        System.out.println("Update sink strategy to file with Level.ERROR and using it at class B");
        File file = new File(Level.ERROR);
        logger1.changeSink(file);

        ClassB b = new ClassB(logger1);
        b.work();

        System.out.println("Creating new logger instance and using at new class B, expecting same ERROR level");
        Logger logger2 = Logger.getInstance();
        ClassB b2 = new ClassB(logger2);
        b2.work();

        System.out.println("Creating new Database strategy, using old logger 2 to create class C, updating strategy in logger 2 and using at new class C, expecting same DEBUG level");
        Database db = new Database(Level.DEBUG);
        ClassC c = new ClassC(logger2);
        logger2.changeSink(db);
        c.work();
    }
}
