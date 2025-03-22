package com.blossom.mapper;

import com.github.pagehelper.Page;
import com.blossom.annotation.AutoFill;
import com.blossom.dto.EmployeePageQueryDTO;
import com.blossom.entity.Employee;
import com.blossom.enumeration.OperationType;
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
     * 插入员工数据
     * @param employee
     */
    @Insert("insert into employee(name,username,password,phone,sex,id_number,create_time,update_time,create_user,update_user,status) "+
    "values "+
    "(#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{createTime},#{updateTime},#{createUser},#{updateUser},#{status})")
    @AutoFill(value= OperationType.INSERT)
    void insert(Employee employee);

    /**
     * 分页查询
     * @param employeePageQueryDTO
     * @return
     */
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 根据主键动态修改属性
     * @param employee
     */
    /*调用通用的更新方法update，可以对所有属性进行修改，包括但不限于这里的status*/
    /*这个方法可以动态地检测传入的新值，并用新值替代掉旧值*/
    /*具体的实现原理：将id和要修改的值封装到对象(employee)里，然后update的动态SQL会自动检测传入的对象的各个属性，如果检测到不为空的属性*/
    /*则表明该属性需要修改，就会将传入的新值取代掉旧值*/
    @AutoFill(value= OperationType.UPDATE)
    void update(Employee employee);

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    @Select("select * from employee where id=#{id}")
    Employee getById(Long id);


}





