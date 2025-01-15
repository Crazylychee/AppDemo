package com.crazylychee.domain.base;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * @desc 基础Domain类
 * @author Crazylychee
 */
public class BaseDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "创建人名称",
    required = false,
    example = "",
    allowableValues = "",
    position = 1,
    hidden = true,
    accessMode = ApiModelProperty.AccessMode.READ_WRITE)
    @TableField(exist = false)
    private String sysCreateUserName;

    @ApiModelProperty(value = "更新人名称",
    required = false,
    example = "",
    allowableValues = "",
    position = 1,
    hidden = true,
    accessMode = ApiModelProperty.AccessMode.READ_WRITE)
    @TableField(exist = false)
    private String sysUpdateUserName;


    @ApiModelProperty(value = "创建时间",
    required = false,
    example = "",
    allowableValues = "",
    position = 1,
    hidden = true,
    accessMode = ApiModelProperty.AccessMode.READ_WRITE)
    @TableField(exist = false)
    private LocalDateTime sysCreateTime;


    @ApiModelProperty(value = "更新时间",
    required = false,
    example = "",
    allowableValues = "",
    position = 1,
    hidden = true,
    accessMode = ApiModelProperty.AccessMode.READ_WRITE)
    @TableField(exist = false)
    private LocalDateTime sysUpdateTime;
    // 构造函数
    public BaseDomain() {
    }

    // getter 和 setter 方法
    public String getSysCreateUserName() {
        return sysCreateUserName;
    }

    public void setSysCreateUserName(String sysCreateUserName) {
        this.sysCreateUserName = sysCreateUserName;
    }

    public String getSysUpdateUserName() {
        return sysUpdateUserName;
    }

    public void setSysUpdateUserName(String sysUpdateUserName) {
        this.sysUpdateUserName = sysUpdateUserName;
    }

    public LocalDateTime getSysCreateTime() {
        return sysCreateTime;
    }

    public void setSysCreateTime(LocalDateTime sysCreateTime) {
        this.sysCreateTime = sysCreateTime;
    }

    public LocalDateTime getSysUpdateTime() {
    return sysUpdateTime;
    }

    public void setSysUpdateTime(LocalDateTime sysUpdateTime) {
    this.sysUpdateTime = sysUpdateTime;
    }

    // equals 方法
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BaseDomain baseDomain = (BaseDomain) obj;
        return sysCreateUserName.equals(baseDomain.sysCreateUserName) &&
               sysUpdateUserName.equals(baseDomain.sysUpdateUserName) &&
               sysCreateTime.equals(baseDomain.sysCreateTime);
    }

    // hashCode 方法
    @Override
    public int hashCode() {
        int result = sysCreateUserName != null ? sysCreateUserName.hashCode() : 0;
        result = 31 * result + (sysUpdateUserName != null ? sysUpdateUserName.hashCode() : 0);
        result = 31 * result + (sysCreateTime != null ? sysCreateTime.hashCode() : 0);
        return result;
    }

    // toString 方法
    @Override
    public String toString() {
        return "BaseDomain{" +
               "sysCreateUserName='" + sysCreateUserName + '\'' +
               ", sysUpdateUserName='" + sysUpdateUserName + '\'' +
               ", sysCreateTime='" + sysCreateTime + '\'' +
               '}';
    }
}
