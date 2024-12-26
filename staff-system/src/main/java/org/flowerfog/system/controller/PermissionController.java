package org.flowerfog.system.controller;

import org.flowerfog.domain.system.Permission;
import org.flowerfog.entity.PageResult;
import org.flowerfog.entity.Result;
import org.flowerfog.entity.ResultCode;
import org.flowerfog.exception.CommonException;
import org.flowerfog.system.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.security.Permissions;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RequestMapping(value = "/system")
@RestController
public class PermissionController {
    @Autowired
    private PermissionService permissionService;



    /**
     * 保存
     * @param
     * @return
     */
    @RequestMapping(value = "/permission",method = RequestMethod.POST)
    public Result save(@RequestBody Map<String,Object> map) throws Exception {

        permissionService.save(map);



        return new Result(ResultCode.SUCCESS);

    }

    /**
     * 查询列表
     * @return
     */
    @RequestMapping(value = "/permission",method = RequestMethod.GET)
    public Result findAll(int page, int size, @RequestParam() Map map){
        List<Permission> list = permissionService.findAll(map);


        return new Result(ResultCode.SUCCESS,list);
    }


    /**
     * 根据id查询
     * @param id
     * @return
     */
    @RequestMapping(value = "/permission/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id) throws Exception {
        Map map = permissionService.findById(id);
        return new Result(ResultCode.SUCCESS,map);
    }

    /**
     * 修改
     * @param id
     * @param
     * @return
     */
    @RequestMapping(value = "/permission/{id}",method = RequestMethod.POST)
    public Result update(@PathVariable(value = "id") String id,@RequestBody Map<String,Object> map) throws Exception {
        map.put("id",id);
        permissionService.update(map);

        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/permission/{id}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable(value = "id") String id) throws Exception {
        permissionService.deleteById(id);

        return new Result(ResultCode.SUCCESS);

    }
}
