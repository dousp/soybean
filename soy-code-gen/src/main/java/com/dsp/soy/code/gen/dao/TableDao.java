package com.dsp.soy.code.gen.dao;

import com.dsp.soy.code.gen.entity.Member;

import java.util.List;

public interface TableDao {

    List<Member> getAllColumns(String tableSchema, String tableName);
}
