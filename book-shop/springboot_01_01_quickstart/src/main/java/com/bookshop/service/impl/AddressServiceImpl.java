package com.bookshop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookshop.dao.AddressDao;
import com.bookshop.domain.Address;
import com.bookshop.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl extends ServiceImpl<AddressDao, Address> implements AddressService {
    @Autowired
    private AddressDao addressDao;
    @Override
    public List<Address> getCustomerAddress(String id) {
        return addressDao.getCustomerAddress(id);
    }

    //更新默认地址
    @Override
    public Boolean updateAddress(Address address) {
        if (address.isFlagDefault()){
            modifyDefaultAddress(address.getCustomerId());
        }
        return addressDao.updateById(address) == 1;
    }

    void modifyDefaultAddress(String customerId){
        Address address = addressDao.selectDefaultAddress(customerId);
        if (address != null){
            addressDao.updateDefaultAddress(false, address.getId().intValue());
        }
    }

    //增加地址
    public Boolean addAddress(Address address){
        if (address.isFlagDefault()){
            modifyDefaultAddress(address.getCustomerId());
        }
        return addressDao.insert(address) == 1;
    }
}
