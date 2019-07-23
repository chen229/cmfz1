package com.baizhi.test;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.elasticsearch.search.aggregations.metrics.ParsedSingleValueNumericMetricsAggregation;

public class StaticInnerClass {
    public static String name="小胡";//静态成员
    public  String clazz="java154班";//非静态成员  内部类不能访问外部类的非静态成员


//    静态方法
    public static void test1(){
        System.out.println("HO,Today is a beautiful day!");

    }

    //非静态方法
    public void test2(){

    }

    //静态内部类
    public static class TestStatic{
        //内部类中没有静态成员
        public String name="小兰";
        public void test3(){
            System.out.println("Everyday should be enjoyed !");
        }


    }

    public static void main(String[] args) {

        TestStatic testStatic = new TestStatic();
        String name = testStatic.name;
        System.out.println(name+"@@@@@@");//小兰@@@@@@:当外部类属性和内部类属性重名时,优先访问内部类属性
        testStatic.test3();
        Object o = new Object();
        Integer integer = new Integer(3);

    }
}