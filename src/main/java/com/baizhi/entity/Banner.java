package com.baizhi.entity;

        import com.fasterxml.jackson.annotation.JsonFormat;
        import lombok.AllArgsConstructor;
        import lombok.Data;
        import lombok.NoArgsConstructor;
        import org.springframework.format.annotation.DateTimeFormat;

        import javax.persistence.Id;
        import javax.persistence.Table;
        import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "c_banner")
public class Banner {
    @Id
    private String id;
    private String name;
    private String cover;
    private String description;
    private String status;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createtime;
}
