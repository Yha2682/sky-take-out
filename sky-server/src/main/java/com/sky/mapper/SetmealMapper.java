package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annoaction.AutoFile;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param id
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long id);

    /**
     * 更新菜品
     * @param setmealDTO
     */
    @AutoFile(OperationType.UPDATE)
    void update(SetmealDTO setmealDTO);

    /**
     * 新增菜品
     * @param setmeal
     */
    @AutoFile(OperationType.INSERT)
    void insert(Setmeal setmeal);

    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    Page<SetmealVO> page(SetmealPageQueryDTO setmealPageQueryDTO);
}
