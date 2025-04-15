package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     *添加员工
     * @param employee
     */
    @Insert("insert into employee (name, username, password, phone, sex, id_number, status, create_time, update_time, create_user, update_user) " +
            "VALUE (#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{status},#{createTime},#{updateTime},#{createUser},#{updateUser})")
    void insert(Employee employee);

    // 通过用户名查询员工（用于判断重复）
    @Select("SELECT * from employee where username=#{username}")
    Employee findByUsername(String username);

    /**
     * 分页查询
     * 动态查询，根据 name 模糊查询 @EmployeeMapper.xml
     * @param employeePageQueryDTO
     * @return
     */
    Page<Employee> page(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 根据主键动态修改属性
     * @param employee
     */
//    @Update("UPDATE employee set status=#{status} where id =#{id}")
//    void startOrStop(Integer status, Long id);

    void update(Employee employee);

    /**
     * 根据员工id查询信息
     * @param id
     * @return
     */
    @Select("SELECT * FROM employee WHERE id = #{id}")
    Employee findById(Long id);
}
