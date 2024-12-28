package org.flowerfog.employee.service;


import org.flowerfog.domain.employee.EmployeeResignation;
import org.flowerfog.employee.dao.EmployeeResignationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class ResignationService {
    @Autowired
    EmployeeResignationDao resignationDao;

    public void save(EmployeeResignation resignation) {
        resignation.setCreateTime(new Date());
        resignationDao.save(resignation);
    }

    public EmployeeResignation findById(String userId) {
        return resignationDao.findByUserId(userId);
    }
}
