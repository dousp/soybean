package ${package};

/*
* ${displayName}
* ${comment}
* \@date ${date(),"yyyy-MM-dd"}
*/
public class ${className} {

    @for(attr in attrs){
		@if(!isEmpty(attr.comment)){
    //${attr.comment}
		@}
    private ${attr.type} ${attr.name} ;
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
}
