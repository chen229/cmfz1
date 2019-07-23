package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.formula.functions.Value;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ExcelTarget(value = "Student")
public class Student implements Serializable {
    @ExcelIgnore//表格显示的时候,可以把该字段忽略掉
    private String id;
    @Excel(name = "学生姓名")
    private String name;
    @Excel(name = "性别")
    private String sex;
    @Excel(name = "生日",format = "yyyy-MM-dd HH:mm:ss")
    private Date bir;
}
