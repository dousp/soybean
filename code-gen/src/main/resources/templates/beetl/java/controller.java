package ${basePackage}.controller;

import ${basePackage}.persistence.entity.${className};
import ${basePackage}.service.CustomizeOrgNameService;
import com.ikang.saas.common.utils.Messages;
import com.ikang.saas.common.vo.AppResult;
import com.ikang.saas.common.vo.Page;
import com.ikang.saas.common.vo.ReturnWrapper;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/*
 * ${displayName}
 * ${comment}
 * \@author ${author}
 * \@date ${date(),"yyyy-MM-dd"}
 */
\@CrossOrigin()
\@RestController
\@RequestMapping(value = "/v1/${className}")
public class ${className}Controller extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(${className}Controller.class);

    \@Resource
    private ${className}Service ${classNameFL}Service;

    /**
     * \@description 分页查询
     * \@author ${author}
     * \@date ${date(),"yyyy-MM-dd"}
     * \@param ${classNameFL}
     * \@return com.ikang.saas.common.vo.AppResult<Page>
     */
    \@GetMapping("/list")
    \@ApiOperation(value = "${displayName}", notes = "分页查询", tags = "v1.0")
    public AppResult<Page> page(${className} ${classNameFL}) {
        return new ReturnWrapper<>(${classNameFL}Service.findByPage(${classNameFL})).success(Messages.getString("success.0"));
    }

    /**
     * \@description 新增
     * \@author ${author}
     * \@date ${date(),"yyyy-MM-dd"}
     * \@param ${classNameFL}
     * \@return com.ikang.saas.common.vo.AppResult<java.lang.Integer>
     */
    \@PostMapping("/add")
    \@ApiOperation(value = "${displayName}", notes = "新增", tags = "v1.0")
    public AppResult<Integer> add(\@RequestBody ${className} ${classNameFL}) {
        ${classNameFL}.setCreatedBy(this.getUserSession().getId().longValue());
        ${classNameFL}.setUpdatedBy(this.getUserSession().getId().longValue());
        ${classNameFL}.setUpdatedOn(new Date());
        ${classNameFL}.setCreatedOn(new Date());
        return new ReturnWrapper<>(${classNameFL}Service.add(${classNameFL})).success(Messages.getString("success.0"));
    }

    /**
     * \@description 修改
     * \@author ${author}
     * \@date ${date(),"yyyy-MM-dd"}
     * \@param ${classNameFL}
     * \@return com.ikang.saas.common.vo.AppResult<java.lang.Integer>
     */
    \@PostMapping("/update")
    \@ApiOperation(value = "${displayName}", notes = "修改", tags = "v1.0")
    public AppResult<Integer> update(\@RequestBody ${className} ${classNameFL}) {
        Assert.notNull(customizeOrgName.getId(),"id不能为空");
        ${classNameFL}.setUpdatedBy(this.getUserSession().getId().longValue());
        ${classNameFL}.setUpdatedOn(new Date());
        return new ReturnWrapper<>(${classNameFL}Service.update(${classNameFL})).success(Messages.getString("success.0"));
    }


}