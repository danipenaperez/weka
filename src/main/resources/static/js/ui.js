/**
 * navbars de https://getbootstrap.com/docs/4.0/examples/navbars/
 *	grids https://getbootstrap.com/docs/4.0/layout/grid/
 *
 *ZINO UI https://zinoui.com/demos/button#default-1
 *
 *https://developer.mozilla.org/es/docs/Web/JavaScript/Introducci%C3%B3n_a_JavaScript_orientado_a_objetos
 *
 *https://github.com/BorisMoore/jquery-tmpl
 */


function selectMenu(option){
//	$('#')
}

function renderView(viewName){
	$('#menu_tenant').removeClass( "active" );
	$('#menu_prototypes').removeClass( "active" );
	$('#menu_models').removeClass( "active" );
	
	$('#menu_'+viewName).addClass("active");
	
	if(viewName == 'tenant'){
		$('#mainContainer').html('');
	}else if(viewName == 'prototypes'){
		$('#mainContainer').html('');
	}else if(viewName == 'models'){
		$('#mainContainer').html('');
	}
}

function access(){
	
	var email = $('#tenant_email').val();
	alert('welcome '+email);
}