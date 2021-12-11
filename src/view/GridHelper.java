package view;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class GridHelper extends GridPane{

	private int rowNumber = 0;
	private final int NumberOfElementsInInputRow = 7;
	private final int Offset = 3;
	private final int firstInputRow = 2;
	
	public int getRowsNumber()
	{
		return rowNumber;
	}
	
	@Override
	public void addRow(int rowIndex,Node... children)
	{
		super.addRow(rowIndex, children);
		rowNumber++;
	}
	
	public void RemoveRow(int rowIndex)
	{
		if(rowIndex>=2) {
			List<Node>list = super.getChildren();
			int firstElementToDeleteIndex = (rowIndex-firstInputRow)*NumberOfElementsInInputRow+Offset;
			for(int i = firstElementToDeleteIndex;i<(firstElementToDeleteIndex + 7);i++)
			{
				list.remove(i);
			}
			
		}
	}
	
	
}
