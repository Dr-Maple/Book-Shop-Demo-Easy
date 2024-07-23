package com.bookshop.controller;

import com.bookshop.controller.utils.R;
import com.bookshop.domain.Admin;
import com.bookshop.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;


    @GetMapping
    public R getAll(){
        return new R(true, adminService.list());
    }

    @PostMapping("/updateAdmin")
    public R updateAdminName(@RequestBody Admin admin){
        System.out.println(admin);
        return new R(adminService.updateById(admin));
    }

    @PostMapping("/addAdminInfo")
    public R addAdminInfo(@RequestBody Admin admin){
        return new R(adminService.save(admin));
    }
}
