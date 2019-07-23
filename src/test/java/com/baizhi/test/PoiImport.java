package com.baizhi.test;

import com.baizhi.entity.Student;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PoiImport {
    public static void main(String[] args) throws Exception {
        //先获得将要导入的文件
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("G:/student.xls")));
        //获得将要导出的sheet
        HSSFSheet sheet = workbook.getSheet("学生信息表");
        //把导入的这些信息存在一个list集合中
        List<Student> list=new ArrayList<>();


        for (int i=2;i<sheet.getLastRowNum();i++){//标题占一行,中文字段占一行,所以实际的导出对象从第三行开始

            //获得sheet中的每一行
            HSSFRow row = sheet.getRow(i);
            Student student = new Student();
            //获得每一行的每一个单元格,并把对应的属性值set进去
            HSSFCell cell = row.getCell(0);
            String id = cell.getStringCellValue();
            student.setId(id);

            HSSFCell cell1 = row.getCell(1);
            String name = cell1.getStringCellValue();
            student.setName(name);

            HSSFCell cell2 = row.getCell(2);
            String sex = cell2.getStringCellValue();
            student.setSex(sex);

            HSSFCell cell3 = row.getCell(3);
            Date bir = cell3.getDateCellValue();
            student.setBir(bir);

            list.add(student);
        }
        for (Student student : list) {
            System.out.println(student);
        }
    }
}
