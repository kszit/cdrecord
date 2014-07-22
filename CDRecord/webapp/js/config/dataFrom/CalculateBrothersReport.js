

function setHaveSaveData(){
	var dataRule = document.getElementById('headerRowModel_dataRule').value;
	var selectCell = dataRule;
	var reportbindid = selectCell.split("$")[0];
	window.frames["selectTableCellPageMain"].window.setCellSelected(reportbindid,selectCell);
}



function getDataRule(){
	var selectedCell = window.frames["selectTableCellPageMain"].window.getSelectedCellHVIds();//选择的单元格
	var cells = selectedCell.split('|');
	var rule =  cells[0];
	return rule;
}


function getDataItems(){
	return "";
}