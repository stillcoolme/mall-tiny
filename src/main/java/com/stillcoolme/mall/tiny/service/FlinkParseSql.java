package com.stillcoolme.mall.tiny.service;

import com.stillcoolme.mall.tiny.vo.SqlParseResultVo;

public interface FlinkParseSql {

    public SqlParseResultVo getParseResult(String sql);
}
