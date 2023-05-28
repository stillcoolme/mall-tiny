package com.stillcoolme.mall.tiny.vo.flink;

import lombok.Data;

/**
 * <p>TODO</p>
 *
 * @author stillcoolme
 * @version V1.0.0
 * @date 2022/11/29 20:10
 */
@Data
public class SqlInfo {
    private String sqlContent;
    private int line;
    private int firstLineIndex;
}
