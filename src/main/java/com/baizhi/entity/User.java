package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "c_user")
public class User implements Serializable {
    @Id
    @Excel(name = "用户编号")
    private String id;
    @Excel(name = "手机号")
    private String phone;
    @Excel(name = "用户名")
    private String username;
    @Excel(name = "用户密码")
    private String password;
    @Excel(name = "用户的盐")
    private String salt;//盐
    @Excel(name = "法名")
    private String guruId;//法名
    @Excel(name = "所在省份")
    private String province;
    @Excel(name = "所在城市")
    private String city;
    @Excel(name = "签名")
    private String sign;//签名
    @Excel(name = "头像",type = 2)
    private String photo;//头像
    @Excel(name = "性别")
    private String sex;
    @Excel(name = "添加时间",format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
}
