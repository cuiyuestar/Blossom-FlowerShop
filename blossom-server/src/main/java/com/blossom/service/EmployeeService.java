package com.blossom.service;

import com.blossom.dto.EmployeeDTO;
import com.blossom.dto.EmployeeLoginDTO;
import com.blossom.dto.EmployeePageQueryDTO;
import com.blossom.entity.Employee;
import com.blossom.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);


    /**
     * 新增员工
     * @param employeeDTO
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * 员工分页查询
     * @param employeePageQueryDTO
     * @return
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 启用禁用员工账号(根据员工id)
     * @param status
     * @param id
     */
    void startOrStop(Integer status, long id);

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    Employee getById(Long id);

    /**
     * 修改员工信息
     */
    void update(Employee employee);
}
