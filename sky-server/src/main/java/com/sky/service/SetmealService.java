package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

public interface SetmealService {
    //更新套餐
    void insertSetmeal(SetmealDTO setmealDTO);
    //分页查询
    PageResult page(SetmealPageQueryDTO setmealPageQueryDTO);

}
