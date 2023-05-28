package com.stillcoolme.mall.tiny.service.impl;

import com.stillcoolme.mall.tiny.service.FlinkParseSql;
import com.stillcoolme.mall.tiny.utils.FlinkSqlUtil;
import com.stillcoolme.mall.tiny.vo.SqlParseResultVo;
import com.stillcoolme.mall.tiny.vo.flink.ParseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FlinkParseSqlImpl implements FlinkParseSql {

    @Autowired
    FlinkSqlUtil flinkSqlUtil;

    @Override
    public SqlParseResultVo getParseResult(String sql) {

        if (StringUtils.isEmpty(sql)) {
            return SqlParseResultVo.builder().msg("sql不应为空").build();
        }
        List<ParseResult> parseResultList = flinkSqlUtil.parseSql(sql);
        if (parseResultList.isEmpty()) {
            return SqlParseResultVo.builder().msg("sql没有问题").resultObject("").resultCode(1).build();
        } else {
            return SqlParseResultVo.builder().msg("sql解析异常").resultObject(parseResultList).resultCode(1).build();
        }
    }
}
