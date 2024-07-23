package com.bookshop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bookshop.dao.AdminDao;
import com.bookshop.domain.Admin;
import com.bookshop.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminDao, Admin> implements AdminService {


}
