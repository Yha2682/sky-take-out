package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annoaction.AutoFile;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DishMapper {



    /**
     * 根据分类id查询菜品数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from dish where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);


    /**
     * 插入菜品数据
     * @param dish
     */

    @AutoFile(value = OperationType.INSERT)
    void insert(Dish dish);


    /**
     * 分类分页查询菜品数据
     * @param dishPageQueryDTO
     * @return
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 根据ID查询菜品状态
     * @param id
     * @return
     */
    @Select("SELECT * FROM dish WHERE id =#{id}")
    Dish getById(Long id);

    /**
     * 删除菜品
     * @param id
     */
    @Delete("DELETE FROM dish WHERE id = #{id}")
    void deleteById(Long id);

    /**
     * 根据ID动态修改菜品
     * @param dish
     */
    @AutoFile(value = OperationType.UPDATE)
    void update(Dish dish);
}
