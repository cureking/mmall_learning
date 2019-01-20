package com.mmall.dao;

import com.mmall.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    //custom    pageHelper
    List<Product> selectList();

    //custom
    List<Product> selectByProductNameAndProductId(@Param("productName") String productName, @Param("productId") Integer productId);

    //custom
    List<Product> selectByProductNameAndCategoryIds(@Param("productName") String productName, @Param("categoryIdList") List<Integer> categoryIdList);

    //task
    //此处必须采用Integer，避免因为产品无法产找到而产生的异常。int不可为null
    Integer selectStockByProductId(Integer id);
}