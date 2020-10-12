package com.dsp.soy.code.gen.service.impl;

import com.dsp.soy.code.gen.dao.TableDao;
import com.dsp.soy.code.gen.entity.Member;
import com.dsp.soy.code.gen.service.TableService;
import com.dsp.soy.code.gen.util.CamelNameUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TableServiceImpl implements TableService {

    @Resource
    TableDao tableDao;


    @Override
    public List<Member> getAllColumns(String tableSchema, String tableName) {
        List<Member> list = tableDao.getAllColumns(tableSchema, tableName);
        list.forEach(e -> {
            e.setName(CamelNameUtil.toJavaName(e.getColName()));
            e.setDisplayName(e.getComment().split("：")[0]);
        });
        return list;
    }
}
