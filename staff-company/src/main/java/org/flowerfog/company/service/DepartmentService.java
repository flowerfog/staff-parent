package org.flowerfog.company.service;

import org.flowerfog.company.dao.DepartmentDao;
import org.flowerfog.domain.company.Department;
import org.flowerfog.service.BaseService;
import org.flowerfog.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class DepartmentService extends BaseService {

    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private IdWorker idWorker;
    /**
     * 保存部门
     */
    public void save(Department department){
        //设置主键值
        String id =idWorker.nextId()+"";
        department.setId(id);
        //调用dao保存
        departmentDao.save(department);
    }

    /**
     * 更新部门
     */
    public void update(Department department){
        //查询
        Department dept = departmentDao.findById(department.getId()).get();
        //设置修改属性
        dept.setCode(department.getCode());
        dept.setIntroduce(department.getIntroduce());
        dept.setName(department.getName());


        //更新部门
        departmentDao.save(dept);
    }
    /**
     * 根据id查询
     */
    public Department findById(String id){
        return departmentDao.findById(id).get();
    }
    /**
     * 查询全部
     */
    //TODO
    public List<Department> findAll(String companyId){
//        Specification<Department> spec = new Specification<Department>() {
//
//            @Override
//            public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
//                //根据id查询
//                return cb.equal(root.get("companyId").as(String.class),id);
//            }
//        };
        return departmentDao.findAll(getSpec(companyId));
    }

    /**
     * id删除部门
     */
    public void deleteById(String id){
        departmentDao.deleteById(id);



    }
}
