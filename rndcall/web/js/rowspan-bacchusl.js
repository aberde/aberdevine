/////////////////////////////////////////////////////////////////////////////////// rowspan ???? ????????
//tableId :  table id?? ???? 
//rowIndex : table?? ???? row index(0???? ????)
//cellIndex : ???? row?? cell index(0???? ????)
// created by singi(20030611)
///////////////////////////////////////////////////////////////////////////////////
	function cellMergeChk(tableObj, rowIndex, cellIndex)
	{
		var rowsCn = tableObj.rows.length; //테이블 row의 개수
		
		if(rowsCn-1 > rowIndex)
			cellMergeProcess(tableObj, rowIndex, cellIndex);						
			
	}
	
	function cellMergeProcess(tableObj, rowIndex, cellIndex)
	{
		var rowsCn = tableObj.rows.length; 
		var compareCellsLen = tableObj.rows(rowIndex).cells.length; // cell의 개수 10개 				

		//초기화
		var compareObj = tableObj.rows(rowIndex).cells(cellIndex);
		var compareValue = compareObj.innerHTML;		
		var cn = 1;
		var delCells = new Array();
		var arrCellIndex = new Array();
		for(i=rowIndex+1; i < rowsCn; i++)
		{
			var cellsLen = tableObj.rows(i).cells.length; //10개
			var bufCellIndex = cellIndex 
			
			//실질적인 row에 cellIndex를 구하자. 
			if(compareCellsLen != cellsLen) 
			{
				bufCellIndex = bufCellIndex - (compareCellsLen - cellsLen);
				
			}
			
			cellObj = tableObj.rows(i).cells(bufCellIndex);
			
			
			if(compareValue == cellObj.innerHTML)
			{
				delCells[cn - 1] = tableObj.rows(i);		//삭제할 cell의 row를 저장한다. 

				arrCellIndex[cn - 1] = bufCellIndex;	//해당 row cell index를 저장한다. 
				
				cn++;				
				
			}			
			else
			{
			 	
				//병합
				compareObj.rowSpan = cn;
				
				//삭제
				for(j=0; j < delCells.length; j++)
				{
					delCells[j].deleteCell(arrCellIndex[j]);
				}
				
				//??????	
				compareObj = cellObj;
				compareValue = cellObj.innerHTML;
				cn = 1;
				delCells = new Array();
				arrCellIndex = new Array();
			}
		}

		//????		
		compareObj.rowSpan = cn;
		//????		
		for(j=0; j < delCells.length; j++)
		{
			delCells[j].deleteCell(arrCellIndex[j]);			
		}
	}