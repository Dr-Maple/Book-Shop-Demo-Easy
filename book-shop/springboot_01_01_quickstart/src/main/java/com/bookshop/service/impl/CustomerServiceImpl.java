package com.bookshop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookshop.dao.CustomerDao;
import com.bookshop.domain.Customer;
import com.bookshop.service.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerDao, Customer> implements CustomerService {
}
