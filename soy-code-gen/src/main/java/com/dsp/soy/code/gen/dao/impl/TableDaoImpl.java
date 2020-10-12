package com.dsp.soy.code.gen.dao.impl;

import com.dsp.soy.code.gen.dao.TableDao;
import com.dsp.soy.code.gen.entity.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class TableDaoImpl implements TableDao {

    public static final String TABLE_COLUMN_SQL
            = "select * from information_schema.columns where table_schema = ? and table_name = ? order by ordinal_position";


    @Resource
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Member> getAllColumns(String tableSchema, String tableName) {
        return jdbcTemplate.query(TABLE_COLUMN_SQL, new Object[]{tableSchema, tableName}, (rs, rowNum) -> {
            Member member = new Member();
            member.setId("PRI".equalsIgnoreCase(rs.getString("column_key")));
            member.setColName(rs.getString("column_name"));
            member.setJdbcType(rs.getString("data_type"));
            member.setPosition(rs.getInt("ordinal_position"));
            member.setComment(rs.getString("column_comment"));
            return member;
        });
    }
}
