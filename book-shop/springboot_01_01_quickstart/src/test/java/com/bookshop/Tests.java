package com.bookshop;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bookshop.dao.AdminDao;
import com.bookshop.domain.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Tests {

	@Autowired
	private AdminDao userDao;

	@Test
	void contextLoads() {
		System.out.println(userDao.selectById("12111"));
	}

	@Test
	void testSave(){
		Admin user = new Admin();
		user.setUsername("11111");
		user.setPassword("111111");
		userDao.insert(user);
	}
	@Test
	void testUpdate(){
		Admin user = new Admin();
		user.setUsername("11111");
		user.setPassword("******");
		userDao.updateById(user);
	}

	@Test
	void testGetPage(){
		IPage page = new Page(2,2);
		userDao.selectPage(page,null);
		System.out.println(page.getCurrent());
		System.out.println(page.getPages());
		System.out.println(page.getRecords());
		System.out.println(page.getSize());
		System.out.println(page.getTotal());
		System.out.println(page.getClass());
	}

	@Test
	void testGetBy(){
		QueryWrapper<Admin> qw = new QueryWrapper<>();
		qw.like("username", "ad");
		System.out.println(userDao.selectList(qw));
	}

	@Test
	void testGetBy2(){
		String name = null;
		LambdaQueryWrapper<Admin> lqw = new LambdaQueryWrapper<>();
//		if (name !=null)lqw.like(Users::getUsername, name);

		lqw.like(name!=null, Admin::getUsername, name);
		System.out.println(userDao.selectList(lqw));

	}

}
