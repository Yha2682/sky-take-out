package com.sky.annoaction;


import com.sky.validation.idNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target(FIELD) // 指定注解可以应用的位置
@Retention(RetentionPolicy.RUNTIME) // 指定注解保留到运行时
@Constraint(validatedBy = idNumberValidator.class) // 指定验证逻辑类
public @interface ValidIdCard {
    String message() default "无效的身份证号码"; // 错误消息

    Class<?>[] groups() default {}; // 验证分组

    Class<? extends Payload>[] payload() default {}; // 负载信息
}
