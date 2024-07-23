package com.bookshop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bookshop.domain.Cart;
import com.bookshop.domain.UserCart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
@Repository
public interface CartDao extends BaseMapper<Cart> {

    @Select("select cart.id id,book_id,customer_id,books.name bookName,new_price,img_Url,cart.num num from cart,books where cart.customer_id = #{customer_id} and cart.book_id = books.id")
    public List<Cart> getCart(String customer_id);
}
