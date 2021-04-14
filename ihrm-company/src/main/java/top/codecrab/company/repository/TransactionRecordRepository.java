package top.codecrab.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import top.codecrab.common.entity.company.TransactionRecord;

/**
 * @author codecrab
 * @since 2021年04月09日 10:35
 */
public interface TransactionRecordRepository extends JpaRepository<TransactionRecord, String>, JpaSpecificationExecutor<TransactionRecord> {
}
