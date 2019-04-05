/**
 * The menu item prototype
 */
laurbe.prototype.Form = $.extend({}, laurbe.BaseViewElement, {
	/**
	* String type definition
	**/
	type: 'form',
	/**
	* The laurbe owner element
	**/
	owner:null,
	/**
	* This object is from template, so this is the template info
	**/
	template: {
				scriptId : "formTemplate",
				url: './laurbe/html/components/form/formTemplate.html'
	},
	/**
	* Return the div Id where the child element must be append
	**/
	getRenderChildWrapperId:function(){
		return this.id;
	}
		

});


/**
 * Constructor definition
 */
laurbe.Form = function Form(args){
	
	/** Init values **/
	var defaults = {
		//title:'myForm'

	};
	
	/** Extends Defautls with args constructor **/
	var initializationProps = $.extend({}, defaults, args);

	/**Sitio Id **/
	initializationProps.id =  initializationProps.id || laurbe.utils.getIdFor(laurbe.prototype.Form.type) ;

	/** Return the instance **/
	var instance = $.extend({}, laurbe.prototype.Form, {instanceProperties:initializationProps});


	return instance;
}