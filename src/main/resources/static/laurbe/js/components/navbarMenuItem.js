/**
 * The menu item prototype
 */
laurbe.prototype.NavBarMenuItem = $.extend({}, laurbe.BaseViewElement, {
	/**
	* String type definition
	**/
	type: 'navBarMenuItem',
	/**
	* The laurbe owner element
	**/
	owner:null,
	/**
	* This object is from template, so this is the template info
	**/
	template: {
				scriptId : "navbarMenuItemTemplate",
				url: './laurbe/html/components/navBar/navBarMenuItemTemplate.html'
	},
	onclickHandler: function(ev){
		alert('soy item y me han pulsado');
		console.log(this);
		var currentObject = laurbe.Directory[ev.currentTarget.id.replace('Wrapper','')];
		if(currentObject.instanceProperties.onclick){
			currentObject.instanceProperties.onclick(ev);
		}else{
			console.log('no hay event definido para '+currentObject.id);
		}

		//up the notification
		if(currentObject.owner){
			currentObject.owner.onChildItemEvent(ev, ev, currentObject);
		}

		

	},
	onItemClicked:function (menuItem){
		console.log(menuItem.id+ ' me avisa que le han clickado ');
		console.log(this.instanceProperties.items);
	},
	/**
	* Mark this item as active render
	**/
	setActive:function(isActive){
		if(isActive){
			$('#'+this.id).addClass('active');
		}else{
			$('#'+this.id).removeClass('active');
		}
	}	

});


/**
 * Constructor definition
 */
laurbe.NavBarMenuItem = function navBarMenuItem(args){
	
	/** Init values for laurbe.navBar **/
	var defaults = {
			wrapper:{
				tag:'<div>'
			},
			text:'Option',
			selected: true
	};
	
	/** Extends Defautls with args constructor **/
	var initializationProps = $.extend({}, defaults, args);

	/**Sitio Id **/
	initializationProps.id =  initializationProps.id || laurbe.utils.getIdFor(laurbe.prototype.NavBarMenuItem.type) ;

	/** Return the instance **/
	var instance = $.extend({}, laurbe.prototype.NavBarMenuItem, {instanceProperties:initializationProps});


	return instance;
}