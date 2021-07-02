package com.palmap.pgsql_test.mapper;

import com.palmap.pgsql_test.bean.SysUser;

import java.util.List;

public interface SysUserMapper {

    int insert(SysUser record);

    List<SysUser> selectAll();
}