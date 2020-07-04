package com.cskaoyan.mall.typehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author: GZ
 * @description: String--String[]
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(String[].class)
public class String2ArrayTypeHandler extends BaseTypeHandler<String[]> {
    private ObjectMapper objectMapper = new ObjectMapper();

    //输入映射
    @SneakyThrows
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int index, String[] strings, JdbcType jdbcType) throws SQLException {
        String value = objectMapper.writeValueAsString(strings);
        preparedStatement.setString(index,value);
    }

    @Override
    public String[] getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String value = resultSet.getString(s);
        return parse(value);
    }

    @Override
    public String[] getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String value = resultSet.getString(i);
        return parse(value);
    }

    @Override
    public String[] getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String value = callableStatement.getString(i);
        return parse(value);
    }

    private String[] parse(String value){
        String[] strs = null;
        if (value == null) return null;
        try {
            strs = objectMapper.readValue(value, String[].class);
        } catch (JsonProcessingException e) {
                e.printStackTrace();
        }
        return strs;
    }
}
