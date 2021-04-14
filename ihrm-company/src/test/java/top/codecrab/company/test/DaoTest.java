package top.codecrab.company.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.codecrab.common.entity.company.Company;
import top.codecrab.common.utils.IdWorker;
import top.codecrab.company.repository.CompanyRepository;

import java.time.LocalDateTime;

/**
 * @author codecrab
 * @since 2021年04月09日 10:11
 */
@SpringBootTest
public class DaoTest {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private IdWorker idWorker;

    @Test
    void now() {
        System.out.println(System.currentTimeMillis());
    }

    @Test
    void testInsert() {
        Company company = new Company();
        company.setId(idWorker.nextId().toString());
        company.setName("1");
        company.setManagerId("1");
        company.setVersion("1");
        company.setRenewalDate(LocalDateTime.now());
        company.setExpirationDate(LocalDateTime.now());
        company.setCompanyArea("1");
        company.setCompanyAddress("1");
        company.setBusinessLicenseId("1");
        company.setLegalRepresentative("1");
        company.setCompanyPhone("1");
        company.setMailbox("1");
        company.setCompanySize("1");
        company.setIndustry("1");
        company.setRemarks("1");
        company.setAuditState("0");
        company.setState(1);
        company.setBalance(0.0D);
        company.setCreateTime(LocalDateTime.now());

        companyRepository.save(company);
    }

    @Test
    void testFind() {
        System.out.println(companyRepository.findById("180434821120"));
    }
}
