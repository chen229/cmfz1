package com.baizhi.test;


import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.util.Date;

public class POITest {
    public static void main(String[] args) throws Exception {
        //获取合并单元格的对象
        //合并第一行的四列单元格
        CellRangeAddress rangeAddress = new CellRangeAddress(0, 0, 0, 4);


        //以导出为例,创建一个excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //获得字体对象
        HSSFFont font = workbook.createFont();

        //给字体设置样式
        font.setBold(true);//加粗
        font.setColor(Font.COLOR_RED);//字体为红色

        //获得单元格样式的对象
        HSSFCellStyle cellStyle = workbook.createCellStyle();

        //将字体的样式绑定到单元格的样式上
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//字体居中

        //创建当前的sheet文档
        HSSFSheet sheet = workbook.createSheet("我的文档");
        //设置列宽
        sheet.setColumnWidth(0,40*256);




        //让当前单元格进行合并
//        sheet.addMergedRegion(rangeAddress);


        //获取日期类型的对象
        HSSFDataFormat dataFormat = workbook.createDataFormat();

        //获得sheet的第一行
        HSSFRow row = sheet.createRow(0);
        //获得第一行的第一个单元格
        HSSFCell cell = row.createCell(0);
        //给第一行的第一个单元格写入内容
        cell.setCellValue("编号");
        //给此单元格加上样式
        cell.setCellStyle(cellStyle);



        short format = dataFormat.getFormat("yyyy-MM-dd HH:mm:ss");//指定日期格式
        cellStyle.setDataFormat(format);//给单元格设置日期格式

        //获得第一行的第二个单元格
//        HSSFCell cell1 = sheet.createRow(0).createCell(1);
        //给当前单元格加上样式
//        cell1.setCellStyle(cellStyle);
//        cell1.setCellValue(new Date());





        //将文档写入到本地磁盘
        workbook.write(new File("g:/test.xls"));//要放在最后执行

    }
}
