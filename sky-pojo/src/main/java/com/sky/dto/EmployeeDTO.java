package com.sky.dto;

import com.sky.anno.ValidIdCard;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
public class EmployeeDTO implements Serializable {

    private Long id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String name;
    @NotEmpty
    @Pattern(regexp = "^1[3-9]\\d{9}$"
            ,message = "手机号必须是11位有效数字")
    private String phone;
    @Pattern(regexp = "^([10])$")
    private String sex;
    @NotNull
    @ValidIdCard(message = "请输入有效的身份证号码")
    private String idNumber;

}
