


function setHaveSaveData(){
	setDataRule();
	
}
function setDataRule(){
	var dataRule = document.getElementById('headerRowModel_dataRule').value;
	document.getElementById('headerRowModel_dataRule_temp').value = dataRule;
}



function getDataRule(){
	var dataRule = document.getElementById('headerRowModel_dataRule_temp').value;
	return dataRule;
}
function getDataItems(){
	return "";
}