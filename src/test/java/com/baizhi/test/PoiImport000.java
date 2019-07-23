package com.baizhi.test;

import com.baizhi.entity.Student;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PoiImport000 {
    public static void main(String[] args) throws Exception {
        //获得将要导出的文件
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("g:/student.xls")));
        HSSFSheet sheet = workbook.getSheet("学生信息表");
        //准备一个集合接收导出的信息
        List<Student> list=new ArrayList<>();
            for (int i=2;i<sheet.getLastRowNum();i++){
                //获得sheet文档的row
                HSSFRow row = sheet.getRow(i);
                Student student = new Student();
                //通过反射拿到类对象
                Class<Student> studentClass = Student.class;
                //获得类对象的属性值
                Field[] fields = studentClass.getDeclaredFields();
                //通过for循环遍历这个属性数值,拿到每个属性值
                for (int j=0;j<fields.length;j++){
                    //得到每一行的每一个单元格
                    HSSFCell cell = row.getCell(j);
                    //获得属性数值里的每一个属性
                    Field field = fields[j];
                    //获得属性各自对应的属性值
                    String fieldName = field.getName();
                    //通过属性名与set拼接的方式获得该类的set方法
                    String setMethod = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    if ("bir".equals(fieldName)){
                        //通过set方法名获得当前set方法的对象
                        Method method = studentClass.getDeclaredMethod(setMethod, Date.class);
                        //获取单元格里的内容
                        Date value = cell.getDateCellValue();
                        method.invoke(student,value);
                    }else {
                        //通过set方法名获得当前set方法的对象
                        Method method = studentClass.getDeclaredMethod(setMethod,String.class);
                        //获取单元格里的内容
                        String value = cell.getStringCellValue();
                        //通过获得set方法的对象调用该set方法,并未对应的属性动态赋值
                        method.invoke(student,value);
                    }
                }
                list.add(student);
            }
        for (Student student : list) {
            System.out.println(student);
        }
    }
}
