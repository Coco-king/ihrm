package top.codecrab.common.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import top.codecrab.common.entity.company.Company;
import top.codecrab.common.entity.company.Department;

import java.util.List;

/**
 * @author codecrab
 */
@Data
@NoArgsConstructor
public class DeptListResult {
    private String companyId;
    private String companyName;
    private String companyManage;
    private List<Department> departmentList;

    public DeptListResult(Company company, List<Department> list) {
        this.companyId = company.getId();
        this.companyName = company.getName();
        this.companyManage = company.getLegalRepresentative();
        this.departmentList = list;
    }

}
