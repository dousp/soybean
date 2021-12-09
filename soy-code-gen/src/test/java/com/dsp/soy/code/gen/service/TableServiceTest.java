package com.dsp.soy.code.gen.service;

import com.dsp.soy.code.gen.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class TableServiceTest {

    @Resource
    TableService tableService;


    // @Test
    public void queryAllColumns(){
        List<Member> memberList = tableService.getAllColumns("tjdb1","rbac_role");
        memberList.forEach(e-> System.out.println(e.toString()));
        Assertions.assertEquals(11,memberList.size());
    }


}
