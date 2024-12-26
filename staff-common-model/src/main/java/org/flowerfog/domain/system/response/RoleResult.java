package org.flowerfog.domain.system.response;

import lombok.Getter;
import lombok.Setter;
import org.flowerfog.domain.system.Permission;
import org.flowerfog.domain.system.Role;
import org.springframework.beans.BeanUtils;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class RoleResult implements Serializable {
    private String id;
    /**
     * 角色名
     */
    private String name;
    /**
     * 说明
     */
    private String description;
    /**
     * 企业id
     */
    private String companyId;

    private List<String> permIds = new ArrayList<>();

    public RoleResult(Role role) {
        //复制
        BeanUtils.copyProperties(role,this);
        for (Permission perm : role.getPermissions()) {
            this.permIds.add(perm.getId());
        }
    }
}
