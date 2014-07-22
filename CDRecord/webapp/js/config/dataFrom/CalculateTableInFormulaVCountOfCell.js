function addOneCell() {
	var allCells = document.getElementById('allcells');
	var selectValue = allCells.value;
	var selectText = allCells.options[allCells.selectedIndex].text;
	var selectedCells = document.getElementById('selectedcells');
	var varItem = new Option(selectText, selectValue);
	selectedCells.options.add(varItem);
}
function removeOneCell() {
	var selectedCells = document.getElementById('selectedcells');
	var selectValue = selectedCells.value;
	jsRemoveItemFromSelect(selectedCells, selectValue);
}
function removeAll() {
	var selectedCells = document.getElementById('selectedcells');
	jsSelectRemoveAll(selectedCells);
}




function setHaveSaveData(){
	setDataRule();
	setDataItems();
	
}
function setDataRule(){
	var dataRule = document.getElementById('headerRowModel_dataRule').value;
	document.getElementById('headerRowModel_dataRule_temp').value = dataRule;
}
function setDataItems(){
	var selectedCells = document.getElementById('selectedcells');
	var dataRuleItems = document.getElementById('headerRowModel_dataRuleItems').value;
	var allCells = document.getElementById('allcells');
    for (var i = 0; i < allCells.options.length; i++) {  
		var oneValue = allCells.options[i].value;
		if(dataRuleItems.indexOf(oneValue)!=-1){
			var oneText = allCells.options[i].text;
			var varItem = new Option(oneText, oneValue);
			selectedCells.options.add(varItem);
		}
    }  
}



function getDataRule(){
	var dataRule = document.getElementById('headerRowModel_dataRule_temp').value;
	return dataRule;
}
function getDataItems(){
	//计算公式中，选择的单元格
	var dataItems = '';
	var objSelect = document.getElementById('selectedcells');
    for (var i = 0; i < objSelect.options.length; i++) {  
		if (i == 0) {
			dataItems += objSelect.options[i].value;
		} else {
			dataItems += '|' + objSelect.options[i].value;
		}
    }  
    return dataItems;
}