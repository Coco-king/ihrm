package top.codecrab.company.service;

import top.codecrab.common.entity.company.Company;
import top.codecrab.common.response.Result;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author codecrab
 * @since 2021-04-09
 */
public interface CompanyService {

    /**
     * 保存企业
     *
     * @param company 企业Bean
     * @return Result序列化json格式
     */
    void save(Company company);

    /**
     * 更新企业
     *
     * @param id      要修改的企业id
     * @param company 企业Bean
     * @return Result序列化json格式
     */
    boolean update(Company company);

    /**
     * 删除企业
     *
     * @param id 企业id
     * @return Result序列化json格式
     */
    void delete(String id);

    /**
     * 查询企业列表
     *
     * @return Result序列化json格式
     */
    List<Company> findAll();

    /**
     * 根据ID查询企业
     *
     * @param id 企业id
     * @return Result序列化json格式
     */
    Company findById(String id);

    /**
     * 审核
     *
     * @param id   企业id
     * @param flag 是否通过 true：通过 false：不通过
     * @return Result序列化json格式
     */
    boolean audit(String id, Boolean flag);
}
