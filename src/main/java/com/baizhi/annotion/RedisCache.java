package com.baizhi.annotion;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)//声明注解使用的地方
@Retention(RetentionPolicy.RUNTIME)//声明注解使用的时机:运行时生效
public @interface RedisCache {

}
