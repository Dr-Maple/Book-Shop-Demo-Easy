package com.bookshop.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bookshop.domain.Address;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AddressDao extends BaseMapper<Address> {
    @Select("select * from address where customer_id = #{customerId}")
    List<Address> getCustomerAddress(String customerId);

    @Select("update address set flag_default = #{flagDefault} where id = #{id}")
    Boolean updateDefaultAddress(boolean flagDefault, Integer id);

    @Select("select * from address where flag_default = 1 and customer_id = #{customerId}")
    Address selectDefaultAddress(String customerId);
}
