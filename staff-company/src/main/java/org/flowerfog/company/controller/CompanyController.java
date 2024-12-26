package org.flowerfog.company.controller;

import org.flowerfog.company.service.CompanyService;
import org.flowerfog.domain.company.Company;
import org.flowerfog.entity.Result;
import org.flowerfog.entity.ResultCode;
import org.flowerfog.exception.CommonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//解决跨域
@CrossOrigin
@RestController
@RequestMapping(value = "/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    //保存企业
    @RequestMapping(value = "",method = RequestMethod.POST)
    public Result save(@RequestBody Company company){
        //业务
        companyService.add(company);
        return new Result(ResultCode.SUCCESS);

    }
    //根据id更新企业
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable(value = "id") String id,@RequestBody Company company){

        //业务
        company.setId(id);
        companyService.update(company);
        return new Result(ResultCode.SUCCESS);
    }
    //删除企业
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id){
        companyService.deleteById(id);
        return new Result(ResultCode.SUCCESS);
    }
    //查询
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id) throws CommonException {

        Company company = companyService.findById(id);
        Result result = new Result((ResultCode.SUCCESS));
        result.setData(company);
        return result;
    }
    //查询列表
    @RequestMapping(value = "",method = RequestMethod.GET)
    public Result findAll(){

//        int i = 1/0;
        List<Company> List = companyService.findAll();
        Result result = new Result(ResultCode.SUCCESS);
        result.setData(List);
        return result;
    }
}
