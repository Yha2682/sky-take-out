package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    /**
     * 批量插入口味数据
     * @param flavors
     */
    void insertBatch(List<DishFlavor> flavors);

    /**
     * 根据菜品ID删除对应口味数据
     * @param dishId
     */
    @Delete("DELETE FROM dish_flavor WHERE dish_id =#{dishId}")
    void deleteByDishId(Long dishId);

    /**
     * 根据菜品ID查找对应口味数据
     * @param id
     * @return
     */
    @Select("SELECT * FROM dish_flavor where dish_id = #{id}")
    List<DishFlavor> getByDishId(Long id);

    /**
     * 根据菜品ID集合删除对应口味数据
     * @param dishIds
     */
    void deleteByDishIds(List<Long> dishIds);
}
