package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 根据菜品ID查询对应的套餐ID
     * @param ids
     * @return
     */
    List<Long> getSetmealIdsByDishIds(List<Long> ids);

    /**
     * 插入菜品数据
     * @param setmealDishes
     */
    void insertBeach(List<SetmealDish> setmealDishes);

    /**
     * 根据套餐ID批量删除套餐
     * @param setmeal_ids
     */
    void deleteByIds(List<Long> setmeal_ids);

    /**
     * 根据ID删除套餐
     * @param id
     */
    void deleteBySetmealId(Long id);
}
