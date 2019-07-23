package com.baizhi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "c_album")
public class Album implements Serializable {
    @Id
    private String id;
    private String title;
    private String cover;//封面
    private Integer count;//章节总数
    private Double score;//评分
    private String author;//作者
    private String reader;//播音者
    private String brief;//专辑简介
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;//专辑上架时间
}
