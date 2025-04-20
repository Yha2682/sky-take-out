package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 新建菜品和对应口味
     * @param dishDTO
     */
    @Transactional//事务注解，要么全成功，要么全失败
    public void insertDish(DishDTO dishDTO) {
        //向菜品表插入一条数据
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.insert(dish);

        //获取insert语句产生的主键值
        Long dishId = dish.getId();
        //向口味表插入N条数据
        List<DishFlavor> flavors = dishDTO.getFlavors();

        if (flavors != null && flavors.size() > 0) {
            flavors.forEach(dishflavor -> {
                dishflavor.setDishId(dishId);
            });

            dishFlavorMapper.insertBatch(flavors);

        }

    }

    /**
     * 分类分页查询菜品
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult page(DishPageQueryDTO dishPageQueryDTO) {
        //自动拦截下一次 SQL 查询，并为其添加分页逻辑
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page =dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());

    }

    /**
     * 批量删除菜品
     * @param ids
     */
    @Override
    public void deleteBatch(List<Long> ids) {
       //正在起售的菜品不能删
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if (dish.getStatus() == StatusConstant.ENABLE) {
                //当前菜品正在起售中
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

       //被套餐关联的菜品不能删
        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if (setmealIds != null && setmealIds.size() > 0) {
            //关联了套餐，不能删除
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }


       //删除菜品
        /*for (Long id : ids) {
            dishMapper.deleteById(id);
            //删除关联的口味数据
            dishFlavorMapper.deleteByDishId(id);
        }*/
        //根据菜品ID集合批量删除菜品数据 sql: delete from dish where id in (?,?,?)
        dishMapper.deleteByIds(ids);

        //根据菜品ID集合批量删除关联的口味数据 sql: delete from dish_flavor where dishId in (?,?,?)
        dishFlavorMapper.deleteByDishIds(ids);
    }

    /**
     * 根据ID查询菜品
     * @return
     */
    @Override
    public DishVO getDishById(Long id) {
        Dish dish = dishMapper.getById(id);
        //查询口味
        List<DishFlavor> dishFlavors =dishFlavorMapper.getByDishId(id);

        //将查询数据封装到VO
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(dishFlavors);
        return dishVO;
    }

    /**
     * 修改菜品
     * @param dishDTO
     */
    @Override
    public void updateDish(@Valid DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        //修改菜品表基本信息
        dishMapper.update(dish);

        //删除原有口味数据
        dishFlavorMapper.deleteByDishId(dish.getId());

        //重新插入口味数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()) {
            flavors.forEach(dishflavor -> {
                dishflavor.setDishId(dishDTO.getId());
            });
            //向口味表插入数据
            dishFlavorMapper.insertBatch(flavors);
        }

    }

}
