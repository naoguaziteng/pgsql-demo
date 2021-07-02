package com.palmap.pgsql_test;

import com.palmap.pgsql_test.bean.SysUser;
import com.palmap.pgsql_test.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hawk
 * @date 2021/7/2
 * @desc
 **/
@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private SysUserMapper sysUserMapper;


    @GetMapping
    public List<SysUser> getUsers() {
        return sysUserMapper.selectAll();
    }

    @PostMapping
    public void insertUser(@RequestBody SysUser sysUser) {
        sysUserMapper.insert(sysUser);
    }


}
