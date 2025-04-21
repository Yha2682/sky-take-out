package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
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


}
