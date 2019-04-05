/**
 * The menu item prototype
 */
laurbe.prototype.Layout = $.extend({}, laurbe.BaseViewElement, {
	/**
	* String type definition
	**/
	type: 'layout',
	/**
	* The laurbe owner element
	**/
	owner:null,
	/**
	* This object is from template, so this is the template info
	**/
	template: {
				scriptId : "layoutTemplate",
				url: './laurbe/html/components/layout/layoutTemplate.html'
	},
	/**
	* Return the div Id where the child element must be append
	**/
	getRenderChildWrapperId:function(){
		return this.id+'_childsWrapper';
	},
		

});


/**
 * Constructor definition
 */
laurbe.Layout = function Layout(args){
	
	/** Init values for laurbe.navBar **/
	var defaults = {
			wrapper:{
				tag:'<div>',
				class:'mt-1' //Spacing tòp 1
			},
			text:'Option',
			selected: true
	};
	
	/** Extends Defautls with args constructor **/
	var initializationProps = $.extend({}, defaults, args);

	/**Sitio Id **/
	initializationProps.id =  initializationProps.id || laurbe.utils.getIdFor(laurbe.prototype.Layout.type) ;

	/** Return the instance **/
	var instance = $.extend({}, laurbe.prototype.Layout, {instanceProperties:initializationProps});


	return instance;
}