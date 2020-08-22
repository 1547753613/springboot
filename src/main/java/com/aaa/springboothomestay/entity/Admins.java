package com.aaa.springboothomestay.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.*;

@Data
@Table(name = "admins")
public class Admins implements UserDetails {
    @Id
    private Integer id;//主键编号
    private String aname;//账号
    private String apass;//密码
    private String head;//	头像
    private String name;///	varchar	姓名
    private String idcard;//	varchar	身份证
    private Integer gender;//	int 	性别
    @Column(name = "nativePlave")
    private String nativePlave;//	varchar	籍贯
    @Column(name = "adress")
    private String address;//	varchar	地址
    private String phone;//	varchar	手机号
    private String email;//	varchar	邮箱
    @Column(name = "beginDate")
    private Date beginDate;//	date	入职日期
    @Column(name = "endDate")
    private Date endDate;//	date	离职日期
    @Column(name = "workState")
    private Integer workState;//	int	状态
    private Integer rid;//	int	外键角色表
    private Integer isexpired;//	int	是否过期
    private Integer islocked;//	int	是否上锁
    @Column(name = "isCreExpirEd")
    private Integer isCreExpirEd;//	int	是否认证过期
    private Integer isenble;//	int 	是否禁用

    private Role role;//获取角色

    private List<Menu> menus=new ArrayList<>();



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.commaSeparatedStringToAuthorityList(this.role.getRole());
    }

    @Override
    public String getPassword() {
        return this.apass;
    }

    @Override
    public String getUsername() {
        return this.aname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isexpired==1;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.islocked==1;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCreExpirEd==1;
    }

    @Override
    public boolean isEnabled() {
        return this.isenble==1;
    }
}
