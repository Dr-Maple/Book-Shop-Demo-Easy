package com.bookshop.controller;

import com.bookshop.controller.utils.R;
import com.bookshop.domain.Address;
import com.bookshop.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddressController {
    @Autowired
    private AddressService addressService;

    @PostMapping("/getCustomerAddress")
    public R getCustomerAddress(@RequestBody  Map<String, String> customerId){
        System.out.println(addressService.getCustomerAddress(customerId.get("id")));
        return new R(true, addressService.getCustomerAddress(customerId.get("id")));
    }

    @PostMapping("/addAddress")
    public R addAddress(@RequestBody Address address){
        System.out.println(address);
        return new R(addressService.addAddress(address));
    }

    @PostMapping("/deleteAddress")
    public R deleteAddress(@RequestBody Address address){
        return new R(addressService.removeById(address.getId()));
    }

    @PostMapping("/updateAddress")
    public R updateAddress(@RequestBody Address address){
        return new R(addressService.updateAddress(address));
    }
}
