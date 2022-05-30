package aspects;

import java.util.logging.Logger;

public aspect ApplicationAspect {
    private static Logger logger = Logger.getLogger(ApplicationAspect.class.getName());

    pointcut createInstances(): initialization(service.*.new());

    pointcut startApp(String[] params): execution(void Ops.main(String[] )) && args(params);

    after() : createInstances() {
        logger.info("Instance created of " + thisJoinPoint.getSignature().getDeclaringTypeName());
    }

    void around(String[] params): startApp(params) {
        logger.info("Application started execution");
        proceed(params);
    }

}