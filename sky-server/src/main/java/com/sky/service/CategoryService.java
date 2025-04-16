package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;

import javax.validation.Valid;
import java.math.BigInteger;

public interface CategoryService {
    /**
     * 新增分类
     * @param categoryDTO
     */
    void insert(@Valid CategoryDTO categoryDTO);

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    PageResult page(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 根据id删除分类
     * @param id
     */
    void delete(BigInteger id);

    /**
     * 修改分类
     * @param categoryDTO
     */
    void update(@Valid CategoryDTO categoryDTO);

    /**
     * 修改状态
     *
     * @param status
     * @param id
     */
    void updateStatus(Integer status, Long id);
}
