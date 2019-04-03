/**
 * input 
 * {
 * 		#container element Id
 * 		renderTo:
 * 		#title to show
 * 		title:string
 * 		
 * }
 */
laurbe.prototype.navBar = $.extend({}, laurbe.viewElement, {
	
	
	
	/**
	 * Initialization method
	 */
	init : function(){
		this.id = laurbe.utils.getIdFor('navBar');
		this.ele = $('#'+this.renderTo);/** Wrapper reference object **/
		return this;
	},
	

	
	/**
	 * Render the element
	 */
	render: function(){
		$('#'+this.renderTo).load("./laurbe/html/components/navbar/wrapperTemplate.html", this.afterload );
	},
	
	afterload:function(){
		console.log('y ele es ');
		console.log(this);
		$('#navbarWrapperTemplate').tmpl(this).appendTo(this);
	},
	
	onclickMenuElement:function(){
		
	}

});


/**
 * Constructor definition
 */
laurbe.navBar = function NavBar(args){
	
	//console.log('esta entrando '+ args.renderTo);
	/** Init values for laurbe.navBar **/
	var navBarDefaults = {
			title:'defaultTitle',
			items: [],
	};
	
	/** Extends Defautls with args constructor **/
	var initializationProps = $.extend({}, navBarDefaults, args);
	//console.log('las propiedades de inicializacioon son ');
	//console.log(initializationProps);
	
	/** Return the instance **/
	var instance = $.extend({}, laurbe.prototype.navBar, initializationProps);
	var instance = instance.init();
	//console.log('finalmente es  ');
	//console.log(instance);
	/** Initialize object and return **/
	return instance;
}