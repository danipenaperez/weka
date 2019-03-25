var prototypeModel;

function buildFormFromPrototype(prototype, buttons){
	prototypeModel = prototype;
	var html='<form id="buildObjectFromPrototype">';
	for(i=0;i<prototype.length;i++){
		switch (prototype[i].type){
			case 'java.lang.String':
				html += '<label for="'+prototype[i].name+'"> '+prototype[i].name+'</label>';
				html += '<input type="text" name="'+prototype[i].name+'">';
				html += '<br>';
				break;
			case 'java.lang.Integer':
				html += '<label for="'+prototype[i].name+'"> '+prototype[i].name+'</label>';
				html += '<input type="number" name="'+prototype[i].name+'" min="0" max="32767">';
				html += '<br>';
				break;
			case 'java.lang.Boolean':
				html += '<label for="'+prototype[i].name+'"> '+prototype[i].name+'</label>';
				html += '<input type="text" name="'+prototype[i].name+'">';
				html += '<br>';
				break;
			case 'boolean':
				html += '<label for="'+prototype[i].name+'"> '+prototype[i].name+'</label>';
				html += '<input type="text" name="'+prototype[i].name+'">';
				html += '<br>';
				break;
			case 'int':
				html += '<label for="'+prototype[i].name+'"> '+prototype[i].name+'</label>';
				html += '<input type="number" name="'+prototype[i].name+'" min="0" max="32767">';
				html += '<br>';
				break;
		
		};
	}
	html +='</form>';
	return html;
}


/**
 * Read Form and create a Json object
 * @returns
 */
function buildJsonObjectFromForm(){
	var toCreate = {};
	for(i=0;i<prototypeModel.length;i++){
		var value = $('form[id=buildObjectFromPrototype]').find('input[name='+prototypeModel[i].name+']').val();
		toCreate[prototypeModel[i].name] = value;
	}
	console.log(toCreate);
	return toCreate;
}



/**
 * Replace spaces by underscores
 * @param text
 * @returns
 */
function format(text){
	return text.split(' ').join('_');
}