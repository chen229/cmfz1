package com.baizhi.aop;

import com.alibaba.fastjson.JSONObject;
import com.baizhi.annotion.RedisCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Method;
import java.util.Set;

@Configuration
@Aspect
public class RedisCacheHash {
    @Autowired
    private Jedis jedis;

    @Around("execution(* com.baizhi.service..*.find*(..))")//指定注解加入的位置
    public Object cache(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //获取将要执行的方法
        //判断将要执行的方法上是否含有自定义的缓存注解
        //如果含有注解,先去缓存中拿,如果缓存中有,则直接返回,如果没有,则查询数据库,并添加到缓存中
        //如果没有注解,则方法放行

        //获得执行的方法
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();//获得执行方法
        String methodName = method.getName();//方法名
        System.out.println("methodName  "+methodName);

        String className = proceedingJoinPoint.getTarget().getClass().getName();//类名
        Object[] args = proceedingJoinPoint.getArgs();//获得执行方法的实参
        StringBuilder builder = new StringBuilder();
        builder.append(methodName).append(":");
        for (int i=0;i<args.length;i++) {
            builder.append(args[i].toString());
            if (i==args.length-1){
                break;
            }
            builder.append(",");

        }
        String key = builder.toString();
        System.out.println("key  "+key);
        //判断要执行的方法上是否存在注解
        boolean b = method.isAnnotationPresent(RedisCache.class);
        if (b){
            //存在注解,判断缓存里是否存有此方法的缓存
            if (jedis.hexists(className,key)){//该缓存里存在相应方法的缓存
                //从缓存里拿数据
                String s = jedis.hget(className, key);
                Object result = JSONObject.parse(s);
                return result;
            }else {//该缓存里没有数据,需要先从数据库里查数据,然后再放入缓存里一份
                Object result=proceedingJoinPoint.proceed();
                String jsonString = JSONObject.toJSONString(result);
                jedis.hset(className,key,jsonString);
                return result;
            }
        }else {
            //不存在注解,不需要从缓存里拿数据,方法放行,可以直接访问数据库
            Object result = proceedingJoinPoint.proceed();
            return result;
        }
    }
    //执行增删改之后,清除当前当前业务层的查询缓存缓存
    @After("execution(* com.baizhi.service..*.*(..)) && !execution(* com.baizhi.service..*.find*(..))")
    public void after(JoinPoint joinPoint){
        String className = joinPoint.getTarget().getClass().getName();
        //是以Hash的数据类型存入到redis缓存中的,其key是类名,value是一个map,
        // 该map的key是此时执行的方法和具体的实际参数,value是该方法的查询结果
        //删除缓存中的key,就是删除该缓存
        jedis.del(className);
        //关闭jedis的连接
            jedis.close();
    }
}
