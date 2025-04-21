package com.sky.controller.admin;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
@Api(tags = "菜品管理相关接口")
public class DishController {
    @Autowired
    private DishService dishService;

    /**
     * 新建菜品
     * @param dishDTO
     * @return
     */
    @PostMapping
    @ApiOperation(value = "新增菜品")
    public Result<Object> addDish(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品：{}",dishDTO);
        dishService.insertDish(dishDTO);
        return Result.success();
    }

    /**
     * 分类分页查询菜品
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "菜品分页查询")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        PageResult pageResult =dishService.page(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 批量删除菜品
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation(value = "批量删除菜品")
    public Result<Object> deleteDish(@RequestParam List<Long> ids) {
        dishService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 根据ID查询菜品
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询菜品")
    public Result<DishVO> getDishById(@PathVariable Long id) {
        DishVO dishVO =dishService.getDishById(id);
        return Result.success(dishVO);
    }

    /**
     * 根据ID返回菜品数组
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    public Result<List<Dish>> listDish(Long categoryId) {
        List<Dish> dish = dishService.list(categoryId);
        return Result.success(dish);
    }

    /**
     * 修改菜品
     * @param dishDTO
     * @return
     */
    @PutMapping
    @ApiOperation(value = "修改菜品")
    public Result<Object> updateDish(@Valid @RequestBody DishDTO dishDTO) {
        dishService.updateDish(dishDTO);
        return Result.success();
    }

}
