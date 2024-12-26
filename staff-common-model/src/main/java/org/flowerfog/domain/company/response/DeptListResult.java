package org.flowerfog.domain.company.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.flowerfog.domain.company.Company;
import org.flowerfog.domain.company.Department;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class DeptListResult {

    private String companyId;
    private String companyName;
    private String companyManage;
    private List<Department> depts;

    public DeptListResult(Company company,List depts){
        this.companyId = company.getId();
        this.companyName = company.getName();
        this.companyManage = company.getLegalRepresentative();//公司联系人
        this.depts = depts;
    }

}
