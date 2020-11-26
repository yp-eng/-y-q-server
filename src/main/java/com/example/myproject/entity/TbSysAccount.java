package com.example.myproject.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author yp
 * @since 2020-11-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TbSysAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 账号
     */
    private String account;

    /**
     * 昵称
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 角色权限ID
     */
    private Integer rolePermissionId;

    /**
     * 状态 0:删除 1：有效 2：禁用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 账号所属区域code
     */
    private String accountCode;

    /**
     * 所属区域名称
     */
    private String codeName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private String createAccountId;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否首次登陆  ，1首次 ，0不是首次
     */
    private Integer first;

    /**
     * 功能权限集
     */
    private String funcPermissions;

    /**
     * 摄像头权限
     */
    private String cameraPermissions;

    /**
     * 平台显示名称
     */
    private String systemName;


}
