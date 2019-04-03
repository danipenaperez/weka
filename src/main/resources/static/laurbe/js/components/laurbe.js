/**
 * Main object namespace
 */
var laurbe ={
		/**
		 * Base view element 
		 */
		viewElement:{
			/**
			 * Current element Id
			 */
			id:null,
			/**
			 * The wrapper element
			 */
			ele:null
		},
		/**
		 * Default store for prototype loaded references
		 * (if the Js file is loaded will be added here to be available
		 */
		prototype:{},
		
		
		/***********************************/
		/***********************************/
		/***********************************/
		
		
		
		/**
		 * Utils 
		 */
		utils:{
			id:0,
			/**
			 * Return a generated unique sequencial string
			 */
			getIdFor:function(){
				this.id++;
				return this.id;
			}
		}
};