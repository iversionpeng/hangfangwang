package com.okcoin.house.aop;

/**
 * @Auther: liupeng
 * @Date: 2019/4/10 23:51
 * @Description(功能描述):
 */

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 耗时记录aop
 */
@Slf4j(topic = "time")
@Aspect
@Component
public class ElapsedTimeAspect {

    @Pointcut(
            "execution(public * com.okcoin.house.service.*.*(..)))")
    public void elapsedtime() {
    }

    /**
     * 环绕通知
     *
     * @param joinPoint
     * @return
     */
    @Around("elapsedtime()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        //去掉try catch了，有问题，回头研究一下
        //很危险
        Object result = joinPoint.proceed();
        long elapsed = System.currentTimeMillis() - start;
        if (elapsed >= 200) {
            log.info( "method：{} ,param：{} ,elapsed time：{} ms",
                    joinPoint.getSignature().toString(), Arrays.toString( joinPoint.getArgs() ), elapsed );
        }
        return result;

    }

}
