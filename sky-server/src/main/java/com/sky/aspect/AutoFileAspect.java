package com.sky.aspect;

import com.sky.annoaction.AutoFile;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Slf4j
@Aspect
@Component
public class AutoFileAspect {
    /**
     * 切入点
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annoaction.AutoFile)")
    public void autoFilepointCut(){}

    /**
     * 前置通知 在通知中进行公共字段的赋值
     * @param joinPoint
     */
    @Before("autoFilepointCut()")
    public void before(JoinPoint joinPoint){
        //方法签名对象
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获得方法上的注解对象
        AutoFile annofile = signature.getMethod().getAnnotation(AutoFile.class);
        //获取数据库操作类型
        OperationType operationType = annofile.value();


        //获取到当前被拦截的方法的参数--实体对象
        Object[] args = joinPoint.getArgs();

        if (args == null || args.length == 0) {
            return;
        }

        Object entity = args[0];//约定第一个为实体对象

        //准备赋值的数据
        LocalDateTime time = LocalDateTime.now();
        Long id = BaseContext.getCurrentId();

        if (operationType == OperationType.INSERT) {
            try {
                //获取数据
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                // 赋值
                setCreateUser.invoke(entity,id);
                setUpdateUser.invoke(entity,id);
                setCreateTime.invoke(entity,time);
                setUpdateTime.invoke(entity,time);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (operationType == OperationType.UPDATE) {
            try {
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                setUpdateUser.invoke(entity,id);
                setUpdateTime.invoke(entity,time);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
