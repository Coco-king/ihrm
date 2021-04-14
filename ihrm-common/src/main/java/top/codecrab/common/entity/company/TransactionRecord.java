package top.codecrab.common.entity.company;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author codecrab
 * @since 2021-04-09
 */
@Data
@Entity
@Table(name = "co_transaction_record")
public class TransactionRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private String companyId;

    private String userId;

    private Integer type;

    private Double amount;

    private Double balance;

    private String note;

    private LocalDateTime createTime;


}
