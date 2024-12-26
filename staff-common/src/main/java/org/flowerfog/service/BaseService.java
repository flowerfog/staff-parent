package org.flowerfog.service;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class BaseService<T> {


    protected Specification<T> getSpec(String companyId){
        Specification<T> spect = new Specification<T>() {
            /**
             * 构造查询条件
             * @param root 包含所有的对象属性
             * @param cq 一般不用
             * @param cb 构造查询条件（sql类似）
             * @return
             */
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                return cb.equal(root.get("companyId").as(String.class),companyId);
            }
        };
        return spect;
    }
}
