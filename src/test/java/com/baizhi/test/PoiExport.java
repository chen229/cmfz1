package com.baizhi.test;

import com.baizhi.entity.Student;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.jar.JarEntry;

public class PoiExport {
    public static void main(String[] args) throws Exception {
        //从数据库中查出要导出的数据
        List<Student> list = new ArrayList<Student>();
        for (int i = 0; i < 10; i++) {
            Student student = new Student();
            student.setId(i+"");
            student.setName("张三"+i+"号");
            student.setSex("男");
            student.setBir(new Date());
            list.add(student);
        }

        //获得excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //获得sheet文档
        HSSFSheet sheet = workbook.createSheet("学生信息表");

        //表格第一行
        HSSFCell cell = sheet.createRow(0).createCell(0);
        //设置内容
        cell.setCellValue("学生详细信息");
        //合并单元格
        CellRangeAddress cellAddresses = new CellRangeAddress(0, 0, 0, 3);
        sheet.addMergedRegion(cellAddresses);
        //居中
       HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//居中
        cell.setCellStyle(cellStyle);
        //第二行
        HSSFRow row = sheet.createRow(1);
        String[] arr={"编号","姓名","性别","生日"};
        for(int i=0;i<arr.length;i++){
            HSSFCell cell1 = row.createCell(i);
            cell1.setCellValue(arr[i]);
        }
        //设置日期格式
//        HSSFDataFormat dataFormat = workbook.createDataFormat();
//        short format = dataFormat.getFormat("yyyy-MM-dd HH:mm:ss");
//        cellStyle.setDataFormat(format);
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        short format = dataFormat.getFormat("yyyy-MM-dd HH:mm:ss");
        cellStyle.setDataFormat(format);

        //导出表格数据
        for (int i=0;i<list.size();i++){
            //获得集合里的用户对象
            Student student = list.get(i);
            //创建行
            HSSFRow studentRow = sheet.createRow(2 + i);

            //为得到行的每个单元格填充数据
            studentRow.createCell(0).setCellValue(student.getId());
            studentRow.createCell(1).setCellValue(student.getName());
            studentRow.createCell(2).setCellValue(student.getSex());
            studentRow.createCell(3).setCellValue(student.getBir());
//            studentRow.createCell(3).setCellStyle(cellStyle);
        }


        //把此文档写入到本地磁盘
        workbook.write(new File("g:/student.xls"));


    }
}
