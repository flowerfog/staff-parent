package org.flowerfog.system.service;


import org.flowerfog.system.dao.RoleDao;
import org.flowerfog.domain.system.Role;
import org.flowerfog.domain.system.User;
import org.flowerfog.system.dao.UserDao;
import org.flowerfog.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private IdWorker idWorker;


    /**
     * 根据mobile查询
     */
    public User findByMobile(String mobile){
        return userDao.findByMobile(mobile);
    }


    /**
     * 保存用户
     */
    public void save(User user){
        //设置主键值
        String id =idWorker.nextId()+"";
        user.setPassword("123456");
        user.setEnableState(1);
        user.setId(id);
        //调用dao保存
        userDao.save(user);
    }

    /**
     * 更新用户
     */
    public void update(User user){
        //查询
        User user1 = userDao.findById(user.getId()).get();
        //设置修改属性
        user1.setUsername(user.getUsername());
        user1.setUsername(user.getUsername());
        user1.setDepartmentId(user.getDepartmentId());
        user1.setDepartmentName(user.getDepartmentName());
        //更新部门
        userDao.save(user1);
    }
    /**
     * 根据id查询
     */
    public User findById(String id){
        return userDao.findById(id).get();
    }
    /**
     * 查询全部
     */
    //TODO
    public Page findAll(Map<String,Object> map,int page,int size){

        //1.需要查询条件
        Specification<User> spec = new Specification<User>() {
            /**
             * 动态拼接
             * @param root
             * @param criteriaQuery
             * @param criteriaBuilder
             * @return
             */
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<>();
                //根据companyId是否为空来构造查询条件
                if(!StringUtils.isEmpty(map.get("companyId"))){
                    list.add(criteriaBuilder.equal(root.get("companyId").as(String.class),(String)map.get("companyId")));
                }
                //根据departmentId是否为空构造
                if(!StringUtils.isEmpty(map.get("departmentId"))){
                    list.add(criteriaBuilder.equal(root.get("departmentId").as(String.class),(String)map.get("departmentId")));
                }
                //根据hasDep判断是否分配部门
                if(!StringUtils.isEmpty(map.get("hasDept"))|| "0".equals((String) map.get("hasDept"))){
                    list.add(criteriaBuilder.isNull(root.get("departmentId")));
                }else{
                    list.add(criteriaBuilder.isNotNull(root.get("departmentId")));
                }

                return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
            }
        };
        //2.分页
        Page pageuser = userDao.findAll(spec,new PageRequest(page-1,size));
        return pageuser;
    }

    /**
     * id删除部门
     */
    public void deleteById(String id){
        userDao.deleteById(id);

    }

    /**
     * 分配角色
     */
    public void assignRoles(String userId,List<String> roleIds){
        //根据id查询用户
        User user = userDao.findById(userId).get();
        //设置用户的角色集合
        Set<Role> roles = new HashSet<>();
        for(String roleId : roleIds){
            Role role = roleDao.findById(roleId).get();
            roles.add(role);
        }
        //设置用户和集合关系
        user.setRoles(roles);
        //更新用户
        userDao.save(user);
    }
}
