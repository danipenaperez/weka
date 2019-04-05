/**
 * The menu item prototype
 */
laurbe.prototype.Button = $.extend({}, laurbe.BaseViewElement, {
	/**
	* String type definition
	**/
	type: 'button',
	/**
	* The laurbe owner element
	**/
	owner:null,
	/**
	* This object is from template, so this is the template info
	**/
	template: {
				scriptId : "buttonTemplate",
				url: './laurbe/html/components/form/buttonTemplate.html'
	},
	onclickHandler: function(ev){
		alert('soy Button');
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
laurbe.Button = function Button(args){
	
	/** Init values **/
	var defaults = {
			text: 'button',
			//important do not use wrapper!!
			type:'primary',
			//align: 'float-right'
	};
	
	/** Extends Defautls with args constructor **/
	var initializationProps = $.extend({}, defaults, args);

	/**Sitio Id **/
	initializationProps.id =  initializationProps.id || laurbe.utils.getIdFor(laurbe.prototype.Button.type) ;

	/** Return the instance **/
	var instance = $.extend({}, laurbe.prototype.Button, {instanceProperties:initializationProps});


	return instance;
}