/**
 * The menu item prototype
 */
laurbe.prototype.Grid = $.extend({}, laurbe.BaseViewElement, {
	/**
	* String type definition
	**/
	type: 'grid',
	/**
	* The laurbe owner element
	**/
	owner:null,
	/**
	* This object is from template, so this is the template info
	**/
	template: {
				scriptId : "gridTemplate",
				url: './laurbe/html/components/grid/gridTemplate.html'
	},
	onclickHandler: function(ev){
		alert('soy container');
		console.log(this);
		var currentObject = laurbe.Directory[ev.currentTarget.id.replace('Wrapper','')];
		if(currentObject.instanceProperties.onclick){
			currentObject.instanceProperties.onclick(ev);
		}else{
			console.log('no hay event definido para '+currentObject.id);
		}

		//up the notification
		if(currentObject.owner && currentObject.owner.onChildItemEvent){
			currentObject.owner.onChildItemEvent(ev, ev, currentObject);
		}

		

	},
	onItemClicked:function (childItem){
		console.log(childItem.id+ ' me avisa que le han clickado ');
		console.log(this.instanceProperties.items);
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
laurbe.Grid = function Grid(args){
	
	/** Init values **/
	var defaults = {
			/**
			wrapper:{
				tag:'<div>',
				class :'container'
				//,class:'d-flex justify-content-center align-self-center'
			}
			**/
			
			
	};
	
	/** Extends Defautls with args constructor **/
	var initializationProps = $.extend({}, defaults, args);

	/**Sitio Id **/
	initializationProps.id =  initializationProps.id || laurbe.utils.getIdFor(laurbe.prototype.Grid.type) ;

	/** Return the instance **/
	var instance = $.extend({}, laurbe.prototype.Grid, {instanceProperties:initializationProps});


	return instance;
}