package com.insurance.travel;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;

import java.util.Arrays;

@Aspect
@Configuration
public class AspectConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(AspectConfiguration.class);

    @Before("execution(* com.insurance.travel.service.UserServiceImpl.*(..))")
    public void loggingParametersForEachMethod(JoinPoint joinPoint){
        logger.info("SERVICE METHOD NAME : " + joinPoint.getSignature().getName());
        logger.info("ARGS PASSED IN METHOD : " + Arrays.toString(joinPoint.getArgs()));
    }

//    @After(value = "execution(* com.insurance.travel.service.UserServiceImpl.*(..))")
//    public void afterEveryMethod(){
//
//    }

    @AfterReturning(pointcut = "execution(* com.insurance.travel.service.UserServiceImpl.*(..))", returning = "result")
    public void getReturnResultForAllMethods(JoinPoint joinPoint, Object result){
        logger.info("METHOD RESULT RETURNS = " + result);
        logger.info("------------------------------------------------------------------");
    }



    @Around("@annotation(com.insurance.travel.TrackTime))")
    public Object getTimeToExecuteMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        final StopWatch stopWatch = new StopWatch();

        //Measure method execution time
        stopWatch.start();
        Object result = proceedingJoinPoint.proceed();
        stopWatch.stop();
        logger.info("METHOD EXECUTION TIME : " + stopWatch.getTotalTimeSeconds() + " SECONDS");
        return result;

    }

}
