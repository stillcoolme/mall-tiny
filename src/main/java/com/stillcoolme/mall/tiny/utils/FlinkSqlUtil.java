package com.stillcoolme.mall.tiny.utils;

import com.stillcoolme.mall.tiny.vo.flink.ParseResult;
import com.stillcoolme.mall.tiny.vo.flink.SqlInfo;
import org.apache.calcite.sql.parser.SqlParser;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

public class FlinkSqlUtil {

    public List<ParseResult> parseSql(String sql) {
        List<SqlInfo> sqlList = SqlListUtil.getSqlList(sql);
        int i = 0;
        ArrayList<ParseResult> list = new ArrayList();
        for (SqlInfo sqlInfo: sqlList) {
            SqlParser parser = SqlParser.create(sql, SqlParser.config());
            i ++;
            try {
                parser.parseQuery(sqlInfo.getSqlContent());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static void main(String[] args) {
        String sql = "create table view as select * from a;select * from view;";
        new FlinkSqlUtil().parseSql(sql);
    }

}
