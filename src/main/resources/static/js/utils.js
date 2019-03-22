var prototypeModel;

function buildFormFromPrototype(prototype){
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
				html += '<input type="radio" name="'+prototype[i].name+'" value="true"> yes';
				html += '<input type="radio" name="'+prototype[i].name+'" value="false"> no';
				html += '<br>';
				break;
			case 'boolean':
				html += '<label for="'+prototype[i].name+'"> '+prototype[i].name+'</label>';
				html += '<input type="radio" name="'+prototype[i].name+'" value="true"> yes';
				html += '<input type="radio" name="'+prototype[i].name+'" value="false"> no';
				html += '<br>';
				break;
			case 'int':
				html += '<label for="'+prototype[i].name+'"> '+prototype[i].name+'</label>';
				html += '<input type="number" name="'+prototype[i].name+'" min="0" max="32767">';
				html += '<br>';
				break;
		
		};
	}
	html += '<button type="button" onclick="javascript:createChoice();">Create!!</button>';
	html +='</form>';
	return html;
}

function createChoice(){
	var toCreate = buildJsonObjectFromForm();
	$.ajax ({
        type: "POST",
        url: '/persons',
        contentType: 'application/json',
        async: false,
        data: JSON.stringify(toCreate),
        success: function () {
        	alert("He aprendido algo mas de ti!"); 
        	buildFormFromPrototype(prototypeModel);
        },error:function(){
        	alert('something wrong!!');
        }
    });
}
function buildJsonObjectFromForm(){
	var toCreate = {};
	for(i=0;i<prototypeModel.length;i++){
		var value = $('form[id=buildObjectFromPrototype]').find('input[name='+prototypeModel[i].name+']').val();
		toCreate[prototypeModel[i].name] = value;
	}
	console.log(toCreate);
	return toCreate;
}