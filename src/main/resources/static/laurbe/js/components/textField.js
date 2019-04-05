/**
 * The menu item prototype
 */
laurbe.prototype.TextField = $.extend({}, laurbe.BaseViewElement, {
	/**
	* String type definition
	**/
	type: 'textField',
	/**
	* The laurbe owner element
	**/
	owner:null,
	/**
	* This object is from template, so this is the template info
	**/
	template: {
				scriptId : "textFieldTemplate",
				url: './laurbe/html/components/form/textFieldTemplate.html'
	},
	onclickHandler: function(ev){
		console('TEXT FIELD PULSADO');
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
	}
		

});


/**
 * Constructor definition
 */
laurbe.TextField = function TextField(args){
	
	/** Init values **/
	var defaults = {
			//label: 'textField',
			//value:'...'
			
	};
	
	/** Extends Defautls with args constructor **/
	var initializationProps = $.extend({}, defaults, args);

	/**Sitio Id **/
	initializationProps.id =  initializationProps.id || laurbe.utils.getIdFor(laurbe.prototype.TextField.type) ;

	/** Return the instance **/
	var instance = $.extend({}, laurbe.prototype.TextField, {instanceProperties:initializationProps});


	return instance;
}