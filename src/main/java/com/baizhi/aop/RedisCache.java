package com.baizhi.aop;

import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Method;
import java.util.Set;

//@Configuration
//@Aspect
public class RedisCache {
    @Autowired
    private Jedis jedis;

    @Around("execution(* com.baizhi.service..*.find*(..))")//指定注解加入的位置
    public Object cache(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //获取将要执行的方法
        //判断将要执行的方法上是否含有自定义的缓存注解
        //如果含有注解,先去缓存中拿,如果缓存中有,则直接返回,如果没有,则查询数据库,并添加到缓存中
        //如果没有注解,则方法放行
//        获取要执行的方法
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
//        String name = method.getName();//获得要执行的方法的名字
                    //判断方法上是否有需要缓存的注解
        boolean b = method.isAnnotationPresent(com.baizhi.annotion.RedisCache.class);
        if (b){
            //当前要执行的方法有该注解
            //判断redis中是否含有某个key
            //如果含有的话,直接从缓存里拿
            //如果缓存里没有的话,则从数据库里拿,,然后再存入缓存一份
            //拿key
            StringBuilder builder = new StringBuilder();
            String className = proceedingJoinPoint.getTarget().getClass().getName();
            String methodName = method.getName();
            StringBuilder builder1 = builder
                    .append(className)
                    .append(".")
                    .append(methodName)
                    .append(":");
            //获取实参
            Object[] args = proceedingJoinPoint.getArgs();
            for(int i=0;i<args.length;i++){
                builder1.append(args[i]).toString();
                if (i==args.length-1){
                    break;
                }
                builder1.append(",");
            }
            String key = builder1.toString();
            //判断redis中是否含有这个key
            if (jedis.exists(key)){
                //redis中含有这个key
                String s = jedis.get(key);//通过key获得value
                //把获的字符串转换成JSON对象
                Object result = JSONObject.parse(s);
                jedis.close();
                return result;//把得到的结果返回去
            }else {
                //redis中不含有这个key
                Object result=proceedingJoinPoint.proceed();//方法放行
                //把获取到的结果放入到Redis缓存中
                jedis.set(key, JSONObject.toJSONString(result));
                jedis.close();
                return result;
            }
        }else {
            //当前要执行的方法上没有该注解,和缓存无关,直接方法放行,从数据库里查数据
            Object result = proceedingJoinPoint.proceed();
            jedis.close();
            return result;
        }
    }
    //执行增删改之后,清除当前当前业务层的查询缓存缓存
    @After("execution(* com.baizhi.service..*.*(..)) && !execution(* com.baizhi.service..*.find*(..))")
    public void after(JoinPoint joinPoint){
        //获得当前执行目标对象的全名
        String className = joinPoint.getTarget().getClass().getName();
        //获得redis缓存中的所有key
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            //判断当前执行的目标对象,在缓存中是否存在,如果存在,删除这个key
            if (key.startsWith(className)){
                //删除缓存中的这个key
                jedis.del(key);
            }
        }
        //关闭jedis的连接
            jedis.close();
    }
}
