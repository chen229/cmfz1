package com.baizhi.test;

import com.baizhi.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class FuXiTest {
    @Test
    public void test(){
        for(int i=0;i<3;i++){
            System.out.println(" ");
            for (int j=0;j<5;j++){
                System.out.print("*");
            }
        }
    }
    @Test
    public void test1(){
        int[] a=new int[5];
        a[0]=1;
        a[1]=2;
        a[2]=3;
        a[3]=4;
        a[4]=5;
        System.out.println(a+"======");
        for (int i=0;i<a.length;i++){
            System.out.println(a[i]+"#######");//遍历数组
        }
        //数组扩容:方式一
        int[] aa = Arrays.copyOf(a, 10);
        System.out.println(aa+"********");
        for (int j=0;j<aa.length;j++){
            System.out.println(aa[j]+"<<<<<<<<<<<<<<<");
        }
        //数组扩容,方式二
        int[] aaa=new int[8];
        System.arraycopy(a,0,aaa,0,a.length);
        System.out.println(aaa+"PPPPPPPPPP");
        for (int i : aaa) {
            System.out.println(i+"?????????");
        }

    }
    @Test
    public void test2(){
        for (int i=0;i<10;i++){
            int random = (int) ((Math.random()*9+1)*100000);
            String s = String.valueOf(random);
            System.out.println(s+"#####");
        }

    }
    @Test
    public void test3(){
        //冒泡排序:相邻两个元素,两两比较,互换位置
        int[] a={2,4,9,1,8,4,43,23};
        for (int i=0;i<a.length-1;i++){
            for (int j=0;j<a.length-i-1;j++){
                if (a[j]>a[j+1]){
                    int temp=a[j];
                    a[j]=a[j+1];
                    a[j+1]=temp;
                }
            }
        }
        for (int i : a) {
            System.out.print(i+" < ");
        }



        //选择排序:固定一个元素,和每个值作比较,互换位置
        for (int i=0;i<a.length-1;i++){
            for (int j=i;j<a.length-1;j++){
                if (a[j]>a[j+1]){
                    int temp=a[j];
                    a[j]=a[j+1];
                    a[j+1]=temp;
                }
            }

        }
        for (int i : a) {
            System.out.print(i+" >");
        }


    }
    @Test
    public void test4(){
        int[] a={2,4,9,1,8,4,43,23};
        //快速排序
        java.util.Arrays.sort(a);
        for (int i : a) {
            System.out.println(i+"PP");
        }
    }
    @Test//二维数组
    public void test5(){
        int[][] b=new int[3][5];
        b[0][0]=1;
        b[0][1]=1;
        b[0][2]=1;
        b[0][3]=1;
        b[0][4]=1;

        b[1][0]=2;
        b[1][1]=2;
        b[1][2]=2;
        b[1][3]=2;
        b[1][4]=2;

        b[2][0]=3;
        b[2][1]=3;
        b[2][2]=3;
        b[2][3]=3;
        b[2][4]=3;
        //遍历二维数组
        for (int i=0;i<b.length;i++){
            for (int j=0;j<b[i].length;j++){
                System.out.print(b[i][j]);
            }
            System.out.println();
        }
    }
    @Test//二维数组
    public void test6(){
        int[][] c ={{33},{44},{55},{66}};
        for (int i=0; i<c.length;i++){
            for (int j=0;j<c[i].length;j++){
                System.out.print (c[i][j]);
            }
            System.out.println();
        }
    }
    @Test
    public void test7(){
        Character[] characters={'d','c','a','h','e','r'};
        String s = "string";
        char[] chars = s.toCharArray();


        System.out.println(chars+"********");
    }
    @Test
    public void test8(){
        String[] args3={"a","b","c"};//静态初始化
        String[] strings = new String[3];//动态初始化
        strings[0]="h";
        strings[1]="i";
        strings[2]="t";

        //遍历
        Arrays.asList(args3).stream().forEach(x -> System.out.println(x));
    }

}
