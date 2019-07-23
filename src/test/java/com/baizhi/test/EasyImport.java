package com.baizhi.test;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.baizhi.entity.Course;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

public class EasyImport {
    public static void main(String[] args) throws Exception {
        //获得导入时需要的参数
        ImportParams params = new ImportParams();
        //设置导入表格时开始的行数
        params.setTitleRows(1);
        params.setHeadRows(2);

        //计算导入需要的时间
        long start = new Date().getTime();

        //导入表格需要的工具类  需要传三个参数
        List<Course> list = ExcelImportUtil.importExcel(new FileInputStream(
                new File("g:/course.xls")),Course.class,params);

        System.out.println(new Date().getTime()-start);
        System.out.println(list.size());
        for (Course course : list) {
            System.out.println(course);
        }
    }
}
