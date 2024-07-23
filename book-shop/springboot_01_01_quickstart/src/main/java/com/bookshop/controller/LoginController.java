package com.bookshop.controller;

import com.bookshop.controller.utils.R;
import com.bookshop.dao.AdminDao;
import com.bookshop.domain.Admin;
import com.bookshop.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@ResponseBody
public class LoginController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/adminLogin")
    public R userLogin(@RequestBody Admin user){
        Admin user1 = adminService.getById(user.getId());
        System.out.println("adminLogin");
        if (user1 == null){
            return new R(false);
        }else {
            String password = adminService.getById(user.getId()).getPassword();
            System.out.println(adminService.getById(user.getId()));
            if (user.getPassword().equals(password))
                return new R(true, adminService.getById(user.getId()));
            else return new R(false);
        }
    }

}
