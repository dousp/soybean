package com.dsp.soy.code.gen.service;

import com.dsp.soy.code.gen.entity.Member;

import java.util.List;

public interface TableService {

    List<Member> getAllColumns(String tableSchema, String tableName);
}
