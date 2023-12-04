package com.example.kafkastream.aop;

import java.lang.reflect.Method;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class HttpAspect {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* *.*(..))")
    protected void allMethod() {
    }

    @Pointcut("@annotation(com.example.kafkastream.aop.Logging)")
    protected void annotation() {
    }

    /*  //before -> Any resource annotated with @Controller annotation
      //and all method and function taking HttpServletRequest as first parameter
      @Before(value = "annotation()")
      public void actionLogRequest (JoinPoint joinPoint) throws Throwable {

       //   MethodSignature signature = (MethodSignature) joinPoint.getSignature();
          log.info(joinPoint.getSignature().getName() + " called...");
          log.info(  joinPoint.getArgs().toString() + " =======================================");

          // Parameters of controller method
         // Object[] args = joinPoint.getArgs();

          // Parameter Name
       //   String[] parameterNames = signature.getParameterNames();

          // Handler Method
       //   Method targetMethod = signature.getMethod();
      }
      @Around(value = "annotation()")
      public void actionLogResponse (JoinPoint joinPoint) throws Throwable {

          log.info(joinPoint.getSignature().getName() + " called...");
          log.info(  joinPoint.getSignature() + " =======================================");
      }*/
    @Around(value = "annotation()")
    public Object log(ProceedingJoinPoint point) throws Throwable {
        var codeSignature = (CodeSignature) point.getSignature();
        var methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        Logger logger = LoggerFactory.getLogger(method.getDeclaringClass());
        var annotation = method.getAnnotation(Logging.class);
        String level = annotation.value();
        var start = Instant.now();
        ChronoUnit unit = annotation.unit();
        boolean showArgs = annotation.showArgs();
        boolean showResult = annotation.showResult();
        boolean showExecutionTime = annotation.showExecutionTime();
        String methodName = method.getName();
        Object[] methodArgs = point.getArgs();
        String[] methodParams = codeSignature.getParameterNames();
        logger(logger, level, entry(methodName, showArgs, methodParams, methodArgs));
        var response = point.proceed();
        var end = Instant.now();
        var duration = String.format("%s %s", unit.between(start, end), unit.name().toLowerCase());
        logger(logger, level, exit(methodName, duration, response, showResult, showExecutionTime));
        return response;
    }

    static void logger(Logger logger, String level, String message) {
        switch (level) {
            case "DEBUG" -> logger.debug(message);
            case "TRACE" -> logger.trace(message);
            case "WARN" -> logger.warn(message);
            case "ERROR" -> logger.error(message);
            default -> logger.info(message);
        }
    }

    static String entry(String methodName, boolean showArgs, String[] params, Object[] args) {
        StringJoiner message = new StringJoiner(" ")
                .add("Started").add(methodName).add("method");
        if (showArgs && Objects.nonNull(params) && Objects.nonNull(args) && params.length == args.length) {
            Map<String, Object> values = new HashMap<>(params.length);
            for (int i = 0; i < params.length; i++) {
                values.put(params[i], args[i]);
            }
            message.add("with args:")
                    .add(values.toString());
        }
        return message.toString();
    }

    String exit(String methodName, String duration, Object result, boolean showResult, boolean showExecutionTime) {
        StringJoiner message = new StringJoiner(" ")
                .add("Finished").add(methodName).add("method");
        if (showExecutionTime) {
            message.add("in").add(duration);
        }
        if (showResult) {
            if (result instanceof ResponseEntity) {
                String status = ((ResponseEntity<?>) result).getStatusCode().toString();
                String body = Objects.requireNonNull(Objects.requireNonNull((ResponseEntity<?>) result).getBody()).toString();
                Set<Map.Entry<String, List<String>>> headers = ((ResponseEntity<?>) result).getHeaders().entrySet();
                message.add("with return:").add("status").add(status).add("body").add(body).add("headers").add(headers.toString());
            } else message.add("with return:").add(result.toString());
        }
        return message.toString();
    }
/*
    //Around -> Any method within resource annotated with @Controller annotation
    @Around("annotation() && args(..)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            String className = joinPoint.getSignature().getDeclaringTypeName();
            String methodName = joinPoint.getSignature().getName();
            Object result = joinPoint.proceed();
            long elapsedTime = System.currentTimeMillis() - start;
            log.info("Method " + className + "." + methodName + " ()" + " execution time : "
                    + elapsedTime + " ms");

            return result;
        } catch (IllegalArgumentException e) {
            log.info("Illegal argument " + Arrays.toString(joinPoint.getArgs()) + " in "
                    + joinPoint.getSignature().getName() + "()");
            throw e;
        }
    }*/
}

