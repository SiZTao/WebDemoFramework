package com.siztao.framework.common.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataFilter {
    /**
     * sql中数据创建用户
     * @return
     */
    String userAlias() default "";

    String deptAlias() default "";


    boolean self() default true;

}
