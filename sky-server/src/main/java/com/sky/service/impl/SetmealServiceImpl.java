package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 新建套餐
     * @param setmealDTO
     * @return
     */
    @Override
    public void insertSetmeal(SetmealDTO setmealDTO) {
        //插入数据
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.insert(setmeal);

        //获取id
        Long id = setmeal.getId();

        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();

        if (setmealDishes != null && !setmealDishes.isEmpty()) {
            setmealDishes.forEach(setmealDish -> {
                setmealDish.setSetmealId(id);
            });
            setmealDishMapper.insertBeach(setmealDishes);
        }
    }

    /**
     * 分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    @Override
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(),setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page =setmealMapper.page(setmealPageQueryDTO);
        long total = page.getTotal();
        List<SetmealVO> setmeals = page.getResult();
        return new PageResult(total,setmeals);

    }

    /**
     * 删除套餐
     * @param ids
     */
    @Override
    public void deleteBatch(List<Long> ids) {
        for (Long id : ids) {
            Setmeal setmeal = setmealMapper.getById(id);
            //正在起售的套餐不能删
            if (setmeal.getStatus() == StatusConstant.ENABLE){
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }
        setmealMapper.deleteByIds(ids);
        setmealDishMapper.deleteByIds(ids);
    }

    /**
     * 修改
     * @param setmealDTO
     */
    @Override
    public void updateSetmeal(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        //copy后插入套餐表
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.update(setmeal);
        //先删除相关菜品表
        setmealDishMapper.deleteBySetmealId(setmeal.getId());

        if (setmealDTO.getSetmealDishes() != null && !setmealDTO.getSetmealDishes().isEmpty()) {
            setmealDTO.getSetmealDishes().forEach(setmealDish -> {
                setmealDish.setSetmealId(setmeal.getId());
            });
            //向菜品表插入数据
            setmealDishMapper.insertBeach(setmealDTO.getSetmealDishes());
        }


    }

    /**
     * 修改状态
     * @param status
     * @param id
     */
    @Override
    public void status(Integer status, Long id) {
        Setmeal setmeal = setmealMapper.getById(id);
        setmeal.setStatus(status);
        setmealMapper.update(setmeal);

    }


}
