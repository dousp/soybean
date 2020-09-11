package ${basePackage}.service;

import ${basePackage}.persistence.CustomizeOrgNameMapper;
import ${basePackage}.persistence.entity.CustomizeOrgName;
import ${basePackage}.vo.CustomizeOrgNameParam;
import com.ikang.saas.common.interceptor.PageInterceptor;
import com.ikang.saas.common.vo.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/*
 * ${displayName}
 * ${comment}
 * \@author ${author}
 * \@date ${date(),"yyyy-MM-dd"}
 */
\@Service
public class ${className}Service {

    \@Resource
    ${className}Mapper mapper;


    /**
     * \@description 分页查询
     * \@author ${author}
     * \@date ${date(),"yyyy-MM-dd"}
     * \@param param 查询参数
     * \@return page
     */
    public Page findByPage(${className} param) {
        PageInterceptor.startPage(param);
        List<${className}> list = mapper.findByParam(param);
        param.setResult(list);
        return param;
    }

    /**
     * \@description 新增
     * \@author ${author}
     * \@date ${date(),"yyyy-MM-dd"}
     * \@param projectCode 项目编码
     * \@return 1 成功 0 失败
     */
    public int add(${className} record) {
        return mapper.insertSelective(record);
    }

    /**
     * \@description 更新
     * \@author ${author}
     * \@date ${date(),"yyyy-MM-dd"}
     * \@param projectCode 项目编码
     * \@return 1 成功 0 失败
     */
    public int update(${className} record) {
        return mapper.updateByIdSelective(record);
    }


}
