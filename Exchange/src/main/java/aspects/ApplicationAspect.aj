package aspects;

import java.util.logging.Logger;

public aspect ApplicationAspect {
    private static Logger logger = Logger.getLogger(ApplicationAspect.class.getName());

    pointcut createInstances( ) : initialization(service.*.new());

    after() : createInstances() {
        logger.info("Instance created of " + thisJoinPoint.getSignature().getDeclaringTypeName());
    }
}