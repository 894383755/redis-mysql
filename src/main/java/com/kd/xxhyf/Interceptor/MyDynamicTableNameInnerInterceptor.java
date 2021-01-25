package com.kd.xxhyf.Interceptor;

import com.baomidou.mybatisplus.core.plugins.InterceptorIgnoreHelper;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.TableNameParser;
import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.DynamicTableNameInnerInterceptor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * mybatis 拦截器
 */
@Component
public class MyDynamicTableNameInnerInterceptor extends DynamicTableNameInnerInterceptor {

    @Value("${jdbc.prefix}")
    private String prefix;

    /**
     * 在sql执行前拦截
     * @param sh
     * @param connection
     * @param transactionTimeout
     */
    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        PluginUtils.MPStatementHandler mpSh = PluginUtils.mpStatementHandler(sh);
        MappedStatement ms = mpSh.mappedStatement();
        SqlCommandType sct = ms.getSqlCommandType();
        if (sct == SqlCommandType.INSERT || sct == SqlCommandType.UPDATE || sct == SqlCommandType.DELETE) {
            if (InterceptorIgnoreHelper.willIgnoreDynamicTableName(ms.getId())) return;
            PluginUtils.MPBoundSql mpBs = mpSh.mPBoundSql();
            mpBs.sql(this.changeTable(mpBs.sql()));
        }
    }

    /**
     * 加上库名
     * @param sql
     * @return
     */
    protected String changeTable(String sql) {
        TableNameParser parser = new TableNameParser(sql);
        List<TableNameParser.SqlToken> names = new ArrayList<>();
        parser.accept(names::add);
        StringBuilder builder = new StringBuilder();
        int last = 0;
        for (TableNameParser.SqlToken name : names) {
            int start = name.getStart();
            if (start != last) {
                builder.append(sql, last, start);
                String value = name.getValue();
                if (!value.toUpperCase().contains(prefix.toUpperCase())) {
                    TableNameHandler handler = (originalql, tableName) -> {
                        return prefix + tableName ;
                    };
                    builder.append(handler.dynamicTableName(sql, value));
                } else {
                    builder.append(value);
                }
            }
            last = name.getEnd();
        }
        if (last != sql.length()) {
            builder.append(sql.substring(last));
        }
        return builder.toString();
    }
}
