package ${package};

import java.io.Serializable;

/*
* ${displayName}
* ${comment}
* \@date ${date(),"yyyy-MM-dd"}
*/
public class ${className} implements Serializable {

    @for(attr in attrs){
		@if(!isEmpty(attr.comment)){
    //${attr.comment}
		@}
    private ${attr.type} ${attr.name};
	@}

    public ${className}(){
    }

    @for(attr in attrs){
    public ${attr.type} get${attr.methodName}(){
        return  ${attr.name};
    }

    public ${className} set${attr.methodName}(${attr.type} ${attr.name}){
        this.${attr.name} = ${attr.name};
		return this;
    }
    @}

    \@Override
	public java.lang.String toString() {
		return new java.util.StringJoiner(", ", ${className}.class.getSimpleName() + "{", "}")
		@for(attr in attrs){
			@if(attr.type == 'ArrayList' || attr.type == 'Set' || attr.type == 'Map'){
			.add("${attr.name}=" + ${attr.name})
			@}else if(attr.type == 'Object'){
		    .add("${attr.name}='" + ${attr.name}.toString())
			@}else{
			.add("${attr.name}='" + ${attr.name} + "'")
			@}
		@}
			.toString();
    }
}
