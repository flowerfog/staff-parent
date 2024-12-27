package org.flowerfog.system.controller;

import io.jsonwebtoken.Claims;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.flowerfog.controller.BaseController;
import org.flowerfog.domain.company.Company;
import org.flowerfog.domain.company.response.DeptListResult;
import org.flowerfog.domain.system.Permission;
import org.flowerfog.domain.system.Role;
import org.flowerfog.domain.system.User;
import org.flowerfog.domain.system.response.ProfileResult;
import org.flowerfog.entity.PageResult;
import org.flowerfog.entity.Result;
import org.flowerfog.entity.ResultCode;
import org.flowerfog.exception.CommonException;
import org.flowerfog.system.service.UserService;
import org.flowerfog.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.flowerfog.utils.PermissionConstants;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.security.Security;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RequestMapping(value = "/system")
@RestController
public class UserController extends BaseController {
    @Autowired
    private UserService userService;


    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 分配角色
     *
     *
     *
     */
    @RequestMapping(value = "/user/assignRoles",method = RequestMethod.PUT)
    public Result save(@RequestBody Map<String,Object> map){
        //获取被分配用户的id
        String id = (String) map.get("id");

        //获取角色的id列表
        List<String> roleIds = (List<String>) map.get("roleIds");
        //调用service完成角色分配
        userService.assignRoles(id,roleIds);

        return new Result(ResultCode.SUCCESS);


    }


    /**
     * 保存
     * @param user
     * @return
     */
    @RequestMapping(value = "/user",method = RequestMethod.POST)
    public Result save(@RequestBody User user){

        //设置id
        /**
         * 企业id：使用固定值
         */
        user.setCompanyId(companyId);
        user.setCompanyName(companyName);

        //调用service
        userService.save(user);

        return new Result(ResultCode.SUCCESS);

    }

    /**
     * 查询部门列表
     * @return
     */
    @RequestMapping(value = "/user",method = RequestMethod.GET)
    public Result findAll(int page, int size, @RequestParam() Map map){
        // 查询
        Page<User> pageuser = userService.findAll(map,page,size);
        //返回
        PageResult<User> pageResult = new PageResult<>(pageuser.getTotalElements(),pageuser.getContent());

        return new Result(ResultCode.SUCCESS,pageResult);
    }


    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable(value = "id") String id){
        User user = userService.findById(id);
        return new Result(ResultCode.SUCCESS,user);
    }

    @RequestMapping(value = "/user/{id}",method = RequestMethod.POST,name = "API-USER-UPDATE")
    public Result update(@PathVariable(value = "id") String id,@RequestBody User user){
        User dep = userService.findById(id);
        dep.setId(user.getId());

        userService.update(dep);

        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/user/{id}",method = RequestMethod.DELETE,name = "API-USER-DELETE")
    public Result delete(@PathVariable(value = "id") String id){
        userService.deleteById(id);

        return new Result(ResultCode.SUCCESS);

    }


    /**
     * 登录成功后获取用户信息
     * 获取用户id
     * 查询用户
     * 构建返回对象
     * 响应
     */
    @RequestMapping(value = "/profile",method = RequestMethod.POST)
    public Result profile(HttpServletRequest request) throws Exception {

        Subject subject = SecurityUtils.getSubject();

        PrincipalCollection principals = subject.getPrincipals();

        ProfileResult result = (ProfileResult) principals.getPrimaryPrincipal();





        return new Result(ResultCode.SUCCESS,result);

    }



    /**
     * 登录
     * 通过service查询账号
     * 比较password
     * 生成jwt信息
     */
    @RequestMapping(value="/login",method = RequestMethod.POST)
    public Result login(@RequestBody Map<String,String> loginMap) {
        String mobile = loginMap.get("mobile");
        String password = loginMap.get("password");
        try {
            //1.构造登录令牌 UsernamePasswordToken
            //加密密码
            password = new Md5Hash(password,mobile,3).toString();  //1.密码，盐，加密次数
            UsernamePasswordToken upToken = new UsernamePasswordToken(mobile,password);
            //2.获取subject
            Subject subject = SecurityUtils.getSubject();
            //3.调用login方法，进入realm完成认证
            subject.login(upToken);
            //4.获取sessionId
            String sessionId = (String)subject.getSession().getId();
            //5.构造返回结果
            return new Result(ResultCode.SUCCESS,sessionId);
        }catch (Exception e) {
            return new Result(ResultCode.MOBILEEORPASSWORDERROR);
        }


//        User user = userService.findByMobile(mobile);
//        //登录失败
//        if(user == null || !user.getPassword().equals(password)) {
//            return new Result(ResultCode.MOBILEORPASSWORDERROR);
//        }else {
//            //登录成功
//            //api权限字符串
//            StringBuilder sb = new StringBuilder();
//            //获取到所有的可访问API权限
//            for (Role role : user.getRoles()) {
//                for (Permission perm : role.getPermissions()) {
//                    if(perm.getType() == PermissionConstants.PERMISSION_API) {
//                        sb.append(perm.getCode()).append(",");
//                    }
//                }
//            }
//            Map<String,Object> map = new HashMap<>();
//            map.put("apis",sb.toString());//可访问的api权限字符串
//            map.put("companyId",user.getCompanyId());
//            map.put("companyName",user.getCompanyName());
//            String token = jwtUtils.createJwt(user.getId(), user.getUsername(), map);
//            return new Result(ResultCode.SUCCESS,token);
//        }
    }
}
