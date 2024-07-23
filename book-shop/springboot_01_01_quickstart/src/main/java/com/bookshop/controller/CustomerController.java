package com.bookshop.controller;


import com.bookshop.controller.utils.R;
import com.bookshop.domain.Customer;
import com.bookshop.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/getCustomerInfo")
    public R getInfo(@RequestBody Map<String, String> customerId){
        System.out.println(customerService.getById(customerId.get("id")));
        return new R(true, customerService.getById(customerId.get("id")));
    }

    @PostMapping("/customerLogin")
    public R userLogin(@RequestBody Customer customer){
        Customer customer1 = customerService.getById(customer.getId());

        if (customer1 == null){
            return new R(false);
        }else {
            String password = customerService.getById(customer.getId()).getPassword();
            System.out.println(customerService.getById(customer.getId()));
            if (customer.getPassword().equals(password))
                return new R(true, customerService.getById(customer.getId()));
            else return new R(false);
        }
    }

    @PostMapping("/register")
    public R register(@RequestBody Customer customer){
        customer.setProfilePhoto("defaultPhoto.jpg");
        if (customerService.getById(customer.getId()) != null){
            return new R(false);
        }else {
            customerService.save(customer);
            return new R(true);
        }
    }

    @PostMapping("/changeCustomerInfo")
    public R changeCustomerInfo(@RequestBody Customer customer){
        System.out.println(customer);
        if (customerService.updateById(customer))
            return new R(true);
        else return new R(false);
    }

    @PostMapping("/uploadProfilePhoto")
    public void uploadProfilePhoto(MultipartFile file){
        String fileName = file.getOriginalFilename();
        System.out.println(fileName);
        try {
            //将文件保存指定目录
            file.transferTo(new File("E:\\Mycode\\vue-smallFrog-bookMall-master\\static\\profilePhoto\\" + fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/becomeMember")
    public R becomeMember(@RequestBody Customer customer){
        customer.setMember("true");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        customer.setMemberDate(df.format(new Date()));
        return new R(customerService.updateById(customer));
    }
}
