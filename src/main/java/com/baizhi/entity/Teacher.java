package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ExcelTarget(value = "任课教师")
public class Teacher implements Serializable {
    @ExcelIgnore
    private String id;
    @Excel(name = "任课老师",needMerge = true)
    private String name;
}
