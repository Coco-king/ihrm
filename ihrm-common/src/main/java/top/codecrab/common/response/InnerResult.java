package top.codecrab.common.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 由于多对多关系使用了JsonIgnore导致多的一方无法序列化到前端，此类用于补全信息
 *
 * @author codecrab
 * @since 2021年04月13日 16:09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class InnerResult<T> extends Result {

    /**
     * 多对多关系中多的一方
     */
    private Iterable<T> list;

    public InnerResult(ResultCode code, Object data, Iterable<T> list) {
        super(code, data);
        this.list = list;
    }
}
