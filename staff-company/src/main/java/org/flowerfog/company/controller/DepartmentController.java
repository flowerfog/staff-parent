package org.flowerfog.company.controller;

import com.sun.xml.internal.bind.v2.TODO;
import org.flowerfog.company.service.CompanyService;
import org.flowerfog.company.service.DepartmentService;
import org.flowerfog.controller.BaseController;
import org.flowerfog.domain.company.Company;
import org.flowerfog.domain.company.Department;
import org.flowerfog.domain.company.response.DeptListResult;
import org.flowerfog.entity.Result;
import org.flowerfog.entity.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping(value = "/company")
@RestController
public class DepartmentController extends BaseController {
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private CompanyService companyService;

    /**
     * 保存
     * @param department
     * @return
     */
    @RequestMapping(value = "/department",method = RequestMethod.POST)
    public Result save(@RequestBody Department department){

        //设置id
        /**
         * 企业id：使用固定值
         */
        department.setCompanyId(companyId);

        //调用service
        departmentService.save(department);

        return new Result(ResultCode.SUCCESS);

    }

    /**
     * 查询部门列表
     * @return
     */
    @RequestMapping(value = "/department",method = RequestMethod.GET)
    public Result findAll(){
        // id
        Company company = companyService.findById(companyId);
        List<Department> list = departmentService.findAll(companyId);
        //返回
        DeptListResult deptListResult = new DeptListResult(company,list);

        return new Result(ResultCode.SUCCESS,deptListResult);
    }


    @RequestMapping(value = "/department/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id){
        Department department = departmentService.findById(id);
        return new Result(ResultCode.SUCCESS,department);
    }

    @RequestMapping(value = "/department/{id}",method = RequestMethod.POST)
    public Result update(@PathVariable(value = "id") String id,@RequestBody Department department){
        Department dep = departmentService.findById(id);
        dep.setId(department.getId());

        departmentService.update(dep);

        return new Result(ResultCode.SUCCESS);
    }

    @RequestMapping(value = "/department/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id){
        departmentService.deleteById(id);

        return new Result(ResultCode.SUCCESS);

    }
}
