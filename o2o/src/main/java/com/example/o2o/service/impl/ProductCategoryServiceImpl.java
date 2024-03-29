package com.example.o2o.service.impl;

import com.example.o2o.dao.ProductCategoryDao;
import com.example.o2o.dto.ProductCategoryExecution;
import com.example.o2o.entity.ProductCategory;
import com.example.o2o.enums.ProductCategoryStateEnum;
import com.example.o2o.exceptions.ProductCategoryOperationException;
import com.example.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Override
    public List<ProductCategory> getProductCategoryList(Long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }

    @Override
    @Transactional
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
            throws ProductCategoryOperationException {
        if (productCategoryList == null || productCategoryList.size() == 0) {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }

        try {
            int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
            if (effectedNum <= 0) {
                throw new ProductCategoryOperationException("Fail to create shop category");
            } else {
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        } catch (Exception e) {
            throw new ProductCategoryOperationException("batchAddProductCategory error:"
                    + e.getMessage());
        }
    }

    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOperationException {
        //TODO: set product_category_id of products in this product_category to be null
        try {
            int effectedNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
            if (effectedNum <= 0) {
                throw new ProductCategoryOperationException("Deleting shop category failed!");
            } else {
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            }
        } catch (Exception e) {
            throw new ProductCategoryOperationException("Delete shop category error:"
                    + e.getMessage());
        }
    }
}
