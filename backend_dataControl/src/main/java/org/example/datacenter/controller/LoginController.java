package org.example.datacenter.controller;

import org.example.datacenter.model.DataCenterAdmin;
import org.example.datacenter.model.UserVo;
import org.example.datacenter.service.DataCenterAdminService;
import org.example.datacenter.util.JWTUtils;
import org.example.datacenter.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private DataCenterAdminService dataCenterAdminService;

    @PostMapping("/login")
    public R login(@RequestBody DataCenterAdmin datacenteradmin){
        if(!dataCenterAdminService.checkUserExistence(datacenteradmin)){
            throw new IllegalArgumentException("没有该用户");

        }
        if(!dataCenterAdminService.varifyPassword(datacenteradmin)){
            throw new IllegalArgumentException("密码错误");
        }
        UserVo userVo = new UserVo();
        userVo.setUsername(datacenteradmin.getName());
        String token = JWTUtils.getToken(String.valueOf(datacenteradmin.getName()), datacenteradmin.getPassword());
        userVo.setToken(token);
        return R.ok("登录成功").setData(userVo);
    }
}
