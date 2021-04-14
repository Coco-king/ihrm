package top.codecrab.company.service.impl;

import org.springframework.stereotype.Service;
import top.codecrab.common.entity.company.Company;
import top.codecrab.company.base.BaseService;
import top.codecrab.company.service.CompanyService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author codecrab
 * @since 2021-04-09
 */
@Service
public class CompanyServiceImpl extends BaseService implements CompanyService {

    /**
     * 保存企业
     *
     * @param company 企业Bean
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public void save(Company company) {
        company.setId(idWorker.nextId().toString());
        company.setAuditState("0");
        company.setState(1);

        //执行保存
        companyRepository.save(company);
    }

    /**
     * 更新企业
     *
     * @param company 企业Bean
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean update(Company company) {
        Optional<Company> optional = companyRepository.findById(company.getId());
        if (!optional.isPresent()) {
            return false;
        }
        Company c = optional.get();

        c.setName(company.getName());
        c.setCompanyPhone(company.getCompanyPhone());

        //执行保存
        companyRepository.save(c);
        return true;
    }

    /**
     * 删除企业
     *
     * @param id 企业id
     */
    @Override
    @Transactional(rollbackOn = Exception.class)
    public void delete(String id) {
        companyRepository.deleteById(id);
    }

    /**
     * 查询企业列表
     */
    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    /**
     * 根据ID查询企业
     *
     * @param id 企业id
     */
    @Override
    public Company findById(String id) {
        Optional<Company> optional = companyRepository.findById(id);
        return optional.orElse(new Company());
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean audit(String id, Boolean flag) {
        if (flag == null) {
            flag = false;
        }
        Optional<Company> optional = companyRepository.findById(id);
        if (!optional.isPresent()) {
            return false;
        }
        Company company = optional.get();
        company.setAuditState(flag ? "1" : "0");
        companyRepository.saveAndFlush(company);
        return true;
    }
}
