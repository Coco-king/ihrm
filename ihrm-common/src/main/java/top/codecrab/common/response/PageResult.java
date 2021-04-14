package top.codecrab.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author codecrab
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {

    /**
     * 总数
     */
    private Long total;

    /**
     * 数据
     */
    private List<T> rows;

}
