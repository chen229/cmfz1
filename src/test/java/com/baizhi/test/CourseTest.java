package com.baizhi.test;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.Course;
import com.baizhi.entity.Student;
import com.baizhi.entity.Teacher;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CourseTest {
    public static void main(String[] args) throws Exception {
        //        模拟数据
        List<Course> list = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Course course = new Course();
            course.setId("c" + i);
            course.setName("java" + i + "班");
            course.setTeacher(new Teacher("t" + i, "李老师" + i + "号"));

            List<Student> list2 = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                Student student = new Student();
                student.setId("s"+j);
                student.setName("老王"+j+"号");
//                student.setPhoto("C:/Users/Public/Pictures/Sample Pictures/Desert.jpg");
                student.setSex("女");
                student.setBir(new Date());
                list2.add(student);
            }
            course.setStudentList(list2);
            list.add(course);
        }

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("计算机一班课程", "课程表"),
                Course.class, list);
        //写入到磁盘
        workbook.write(new FileOutputStream(new File("g:/course.xls")));
    }
}
