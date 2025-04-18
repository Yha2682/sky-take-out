package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;

@RestController
@Slf4j
@Api(tags ="分类管理模块")
@RequestMapping("/admin/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    @PostMapping
    @ApiOperation(value = "新增分类")
    public Result<T> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类：{}",categoryDTO.toString());
        categoryService.insert(categoryDTO);
        return Result.success();
    }

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "分类分页查询")
    public Result pageCategory(CategoryPageQueryDTO categoryPageQueryDTO/*,Integer type*/){
//        if(type==null){
            PageResult pageResult = categoryService.page(categoryPageQueryDTO);
            return Result.success(pageResult);
//        }
//        List<Category> categories = categoryService.selectForType(type);
//        return Result.success(categories);

    }

    /**
     * 根据id删除分类
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation(value = "根据id删除分类")
    public Result<Object> deleteCategory(Long id) {
        categoryService.delete(id);
        return Result.success();
    }

    /**
     * 修改分类
     * @param categoryDTO
     * @return
     */
    @PutMapping
    @ApiOperation(value = "修改分类")
    public Result<Object> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        categoryService.update(categoryDTO);
        return Result.success("修改完成");
    }

    /**
     * 启用、禁用分类
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation(value = "修改分类状态")
    public Result<Object> updateStatus(@PathVariable Integer status,Long id) {
        categoryService.updateStatus(status,id);
        return Result.success();
    }

    /**
     * 根据类型查询分类
     * @param type
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "根据类型查询分类")
    public Result<List<Category>> listCategory(Integer type) {
        List<Category> categories = categoryService.selectForType(type);
        return Result.success(categories);

    }
}
