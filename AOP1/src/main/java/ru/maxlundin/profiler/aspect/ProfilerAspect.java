package ru.maxlundin.profiler.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import ru.maxlundin.profiler.service.ProfilerService;

@Aspect
public class ProfilerAspect {

    private static ProfilerService profilerService;

    public static void setProfilerService(ProfilerService ps) {
        profilerService = ps;
    }

    @Around("execution(* ru.maxlundin.application..*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();
        Object result = joinPoint.proceed(joinPoint.getArgs());
        long end = System.nanoTime();

        profilerService.registerCall(joinPoint.getSignature(), end - start);

        return result;
    }
}
