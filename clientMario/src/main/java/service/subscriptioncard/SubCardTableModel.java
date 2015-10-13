package service.subscriptioncard;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import tn.mario.moovtn.entities.SubscriptionCard;

public class SubCardTableModel extends AbstractTableModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<SubscriptionCard> list = new ArrayList<SubscriptionCard>();
	private List<Boolean> checkList = new ArrayList<Boolean>();
	private String [] header={"","Id","Locked","Expired","Validity Start","validity End","User"};
	int i;
	
	public SubCardTableModel(){
		list = new  DelegateSubCard().FindAll();
		for (i=0;i<list.size();i++){
			checkList.add(false);
		}
	}
	
	@Override
	public int getRowCount() {
		return list.size();
	}

	@Override
	public int getColumnCount() {
		
		return header.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(columnIndex){
		case 0 : return checkList.get(rowIndex);
		case 1 : return list.get(rowIndex).getId();
		case 2 : {
			if(list.get(rowIndex).getLocked()){
				return "locked";
			}
			else{
				return "unlocked";
			}
		}
		case 3: if(list.get(rowIndex).getExpired()){
			return "Expired";
		}
		else{
			return "valid";
		}
		case 4 : return list.get(rowIndex).getValidityStart();
		case 5 : return list.get(rowIndex).getValidityEnd();
		case 6 : return list.get(rowIndex).getUser().getFirstName()+" "+list.get(rowIndex).getUser().getLastName();
		default : return null;
		}
		
	}
	@Override
	public String getColumnName(int column){
		return header[column];
	}
	
	@Override
	public boolean isCellEditable(int row,int col){
		if(col==0 || col==2 ) {
			return true;
		}
		return false;
	}
	
	public Class<?> getColumnClass(int col){
		
		switch(col){
		case 0 : return Boolean.class;
		case 1 : return Integer.class;
		case 2 : return String.class;
		case 3 : return String.class;
		case 4 : return Date.class;
		case 5 : return Date.class;
		case 6 : return String.class;
		default: return null;
		}
	}
	public void setValueAt(Object value,int row, int col){
		if (col==0){
			if(value.toString().equals("true")){
				checkList.set(row, true);
			}
			else {	
				checkList.set(row, false);
			}
		}
		if(col==2){
			SubscriptionCard sc = list.get(row);
			if(value.toString().equals("locked"))
			{
				sc.setLocked(true);
			}
			else if(value.toString().equals("unlocked"))
			{
				sc.setLocked(false);
			} 
			DelegateSubCard delegate = new DelegateSubCard();
			delegate.Update(sc);
			list.set(row, sc);
		}
		fireTableCellUpdated(row, col);
		
	}
	
	public void deleteRow(int i){
		list.remove(i);
		checkList.remove(i);
		fireTableDataChanged();
	}

}
