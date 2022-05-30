package aspects;

import java.util.Arrays;
import java.util.logging.Logger;

public aspect MonitoringAspect {
    private static Logger logger = Logger.getLogger(MonitoringAspect.class.getName());

    pointcut methodExec(): execution(* service.*Service.*(*)) || execution(* dao.daoimpl.*Impl.*(*));

    before(): methodExec() {
        String packageName = thisJoinPoint.getSignature().getDeclaringTypeName();
        String methodName = thisJoinPoint.getSignature().getName();

        String args = Arrays.toString(thisJoinPoint.getArgs());

        logger.info("Method [" + packageName + "." + methodName + "] called with args " + args + "...");
    }

}
