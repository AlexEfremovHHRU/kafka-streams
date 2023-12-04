package com.example.kafkastream.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.temporal.ChronoUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Logging {
    String value() default "INFO";
    ChronoUnit unit() default ChronoUnit.SECONDS;
    boolean showArgs() default false;
    boolean showResult() default false;
    boolean showExecutionTime() default true;
}
