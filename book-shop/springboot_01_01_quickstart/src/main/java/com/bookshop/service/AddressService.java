package com.bookshop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bookshop.domain.Address;

import java.util.List;

public interface AddressService extends IService<Address> {
    List<Address> getCustomerAddress(String customerId);

    Boolean updateAddress(Address address);

    Boolean addAddress(Address address);
}
