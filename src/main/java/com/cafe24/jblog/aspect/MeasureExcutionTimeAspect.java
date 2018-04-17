package com.cafe24.jblog.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class MeasureExcutionTimeAspect {
	
	@Around(value="execution(* *..repository.*.*(..)) || execution(* *..service.*.*(..))")
	public Object roundAdvice(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch sw = new StopWatch();
		sw.start();
		
		Object result = pjp.proceed();
		
		sw.stop();
		Long totalTime = sw.getTotalTimeMillis();
		
		String className = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();
		String taskName = className + ", " + methodName;
		
		System.out.println("[ExecutionTime] " + " [" + taskName + "]" + " : " + totalTime + " millis");
		
		return result;
	}

}
