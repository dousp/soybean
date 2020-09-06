package ${basePackage}.persistence.provider;

import ${basePackage}.persistence.entity.${className};
import org.apache.ibatis.jdbc.SQL;

/*
 * ${displayName}
 * tableName ${tableName}
 * \@author ${author}
 * \@date ${date(),"yyyy-MM-dd"}
 */
public class ${className}SqlProvider {

    public String insertSelective(${className} record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("${tableName}");

    @for(attr in attrs){
        if (record.get${attr.methodName}() != null) {
            sql.VALUES("${attr.colName}", "#{${attr.name},jdbcType=${attr.jdbcType}}");
        }
    @}
        // sql.VALUES("created_on", "now()");
        // sql.VALUES("updated_on", "now()");

        return sql.toString();
    }

    public String updateByIdSelective(${className} record) {
        SQL sql = new SQL();
        sql.UPDATE("${tableName}");

    @for(attr in attrs){
        if (record.get${attr.methodName}() != null) {
            sql.SET("${attr.colName} = #{${attr.name},jdbcType=${attr.jdbcType}}");
        }
    @}
        // sql.SET("updated_on = now() ");

        sql.WHERE("id = #{id,jdbcType=BIGINT}");

        return sql.toString();
    }

    public String findByParam(${className} record) {
        SQL sql = new SQL();

    @var col = '';
	@for(attr in attrs) {
    @	var bd = attrLP.last ? "" : ",";
    @	col = col + attr.colName + bd;
	@	if(attrLP.index%5 ==0 || attrLP.last){
        sql.SELECT(" ${col} ");
    @   	col = '';
    @   }
    @}
        // sql.SELECT("(case t.created_by when 0 then 'admin' else (select u.name from r_user u where u.id = t.created_by) end) as created_name ");
        // sql.SELECT("(case t.updated_by when 0 then 'admin' else (select u.name from r_user u where u.id = t.updated_by) end) as updated_name ");

        sql.FROM("${tableName} t ");

	@for(attr in attrs){
        if (record.get${attr.methodName}() != null) {
            sql.WHERE("${attr.colName} = #{${attr.name},jdbcType=${attr.jdbcType}}");
        }
    @}

        // sql.ORDER_BY("t.created_on desc");

	    return sql.toString();
    }
}