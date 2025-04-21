package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;

import java.util.List;

public interface SetmealService {
    //更新套餐
    void insertSetmeal(SetmealDTO setmealDTO);
    //分页查询
    PageResult page(SetmealPageQueryDTO setmealPageQueryDTO);
    //批量删除
    void deleteBatch(List<Long> ids);
    //更新套餐
    void updateSetmeal(SetmealDTO setmealDTO);
    //修改套餐状态
    void status(Integer status, Long id);
}
