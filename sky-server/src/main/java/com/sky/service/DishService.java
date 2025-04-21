package com.sky.service;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import javax.validation.Valid;
import java.util.List;

public interface DishService {

    //新增菜品和口味数据
    void insertDish(DishDTO dishDTO);

    //分页查询菜品
    PageResult page(DishPageQueryDTO dishPageQueryDTO);

    //批量删除菜品
    void deleteBatch(List<Long> ids);

    //根据ID查询菜品
    DishVO getDishById(Long id);

    //修改菜品
    void updateDish(@Valid DishDTO dishDTO);
    //查询菜品
    List<Dish> list(Long categoryId);
}
