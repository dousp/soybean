package ${basePackage}.persistence;

import java.io.Serializable;
import java.util.List;

import ${basePackage}.persistence.entity.${className};
import ${basePackage}.persistence.provider.${className}SqlProvider;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

/*
 * ${displayName}
 * ${comment}
 * \@author ${author}
 * \@date ${date(),"yyyy-MM-dd"}
 */
\@Mapper
\@Repository
public interface ${className}Mapper {

	/**
	 * 物理删除，谨慎使用
	 * \@param id 主键
	 * \@return 0 失败，1 成功
	 */
	\@Delete({
			"delete from ${tableName}",
			"where id = #{id,jdbcType=BIGINT}"
	})
	int deleteById(Long id);

	/**
	 * 插入
	 * \@see ${className}SqlProvider#insertSelective(${className})
	 * \@param param
	 * \@return 0 失败，1 成功
	 */
	\@InsertProvider(type=${className}SqlProvider.class, method="insertSelective")
	int insertSelective(${className} record);

	/**
	 * 更新
	 * \@see ${className}SqlProvider#updateByIdSelective(${className})
	 * \@param param
	 * \@return 0 失败，1 成功
	 */
	\@UpdateProvider(type=${className}SqlProvider.class, method="updateByIdSelective")
	int updateByIdSelective(${className} record);

	/**
	 * 通过主键查询
	 * \@param id 主键
	 * \@return ${className}
	 */
	\@Select({
			"select",
		@var col = '';
		@for(attr in attrs) {
		@	var bd = attrLP.last ? "" : ",";
		@	col = col + attr.colName + bd;
		@	if(attrLP.index%5 ==0 || attrLP.last){
			"${col}",
		@   	col = '';
		@   }
		@}
			"from ${tableName}",
			"where id = #{id,jdbcType=BIGINT}"
	})
	\@Results({
		@for(attr in attrs) {
		@	var id = attr.isId ? ", id = true" : "";
		@	var bd = attrLP.last ? "" : ",";
			\@Result(column = "${attr.colName}", property = "${attr.name}", jdbcType = JdbcType.${attr.jdbcType}${id})${bd}
		@}
	})
	${className} findById(Long id);

	/**
	 * 分页查询
	 * \@see ${className}SqlProvider#findByParam(${className}Param)
	 * \@param param
	 * \@return 集合
	 */
	\@SelectProvider(type = ${className}SqlProvider.class, method = "findByParam")
	\@Results({
		@for(attr in attrs) {
		@	var id = attr.isId ? ", id = true" : "";
		@	var bd = attrLP.last ? "" : ",";
			\@Result(column = "${attr.colName}", property = "${attr.name}", jdbcType = JdbcType.${attr.jdbcType}${id})${bd}
		@}
	})
	List<${className}> findByParam(${className} param);
}