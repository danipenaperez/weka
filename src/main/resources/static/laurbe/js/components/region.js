/**
 * The menu item prototype
 */
laurbe.prototype.Region = $.extend({}, laurbe.BaseViewElement, {
	/**
	* String type definition
	**/
	type: 'region',
	/**
	* The laurbe owner element
	**/
	owner:null,
	/**
	* This object is from template, so this is the template info
	**/
	template: {
				scriptId : "regionTemplate",
				url: './laurbe/html/components/layout/regionTemplate.html'
	},
	/**
	* Return the div Id where the child element must be append
	**/
	getRenderChildWrapperId:function(){
		return this.id+'_childsWrapper';
	}
		

});


/**
 * Constructor definition
 */
laurbe.Region = function Region(args){
	
	/** Init values **/
	var defaults = {
			wrapper:null,//no use wrapper
			//text:'Region',
			//align:'float-right',
			fixedSize:''

	};
	
	/** Extends Defautls with args constructor **/
	var initializationProps = $.extend({}, defaults, args);

	/**Sitio Id **/
	initializationProps.id =  initializationProps.id || laurbe.utils.getIdFor(laurbe.prototype.Region.type) ;

	/** Return the instance **/
	var instance = $.extend({}, laurbe.prototype.Region, {instanceProperties:initializationProps});


	return instance;
}