package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Course implements Serializable {
    @Excel(name = "课程编号",needMerge = true)
    private String id;
    @Excel(name = "课程名字",needMerge = true)
    private String name;
    @ExcelEntity
    private Teacher teacher;//课程老师
    @ExcelCollection(name = "学生姓名")
private List<Student> studentList;//选该课程的学生

}
