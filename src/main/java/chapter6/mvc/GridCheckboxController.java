package chapter6.mvc;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.CheckEvent;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Row;

import entity.Person;

@SuppressWarnings("serial")
public class GridCheckboxController extends SelectorComposer<Component> {

	@Wire
	Grid gridPerson;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		List<Person> list = new ArrayList<Person>();
		list.add(new Person("Nik", 21));
		list.add(new Person("Kate", 18));
		list.add(new Person("Nataly", 45));
		list.add(new Person("Nik", 11));
		list.add(new Person("Kate", 33));
		list.add(new Person("Nataly", 76));

		ListModelList<Person> listModelList = new ListModelList<Person>(list);
		gridPerson.setModel(listModelList);

	}

	boolean isCheck = false;

	@Listen("onCheck = #selectedAllCheckBox")
	public void selectedAllCheckBox(CheckEvent checkEvent) {

		if (checkEvent.isChecked()) {

			List<Row> rowList = gridPerson.getRows().getChildren();
			
			for (Row row : rowList) {
				Cell cell = (Cell) row.getChildren().get(0);
				Checkbox checkbox = (Checkbox) cell.getChildren().get(0);
				checkbox.setChecked(checkEvent.isChecked());

				Label name = (Label) row.getChildren().get(1).getChildren()
						.get(0);
				name.setStyle("text-decoration: line-through;");

				Label age = (Label) row.getChildren().get(2).getChildren()
						.get(0);
				age.setStyle("text-decoration: line-through;");
			}
		} else {

			List<Row> rowList = gridPerson.getRows().getChildren();
			for (Row row : rowList) {
				Cell cell = (Cell) row.getChildren().get(0);
				Checkbox checkbox = (Checkbox) cell.getChildren().get(0);
				checkbox.setChecked(checkEvent.isChecked());

				Label name = (Label) row.getChildren().get(1).getChildren()
						.get(0);
				name.setStyle("");

				Label age = (Label) row.getChildren().get(2).getChildren()
						.get(0);
				age.setStyle("");
			}
		}
	}

	Label name1 = null;
	Label age1 = null;
	
	@Listen("onPersonCheck = #gridPerson")
	public void selectedCheckbox(ForwardEvent forwardEvent) {

		Checkbox checkbox = (Checkbox) forwardEvent.getOrigin().getTarget();
		Row row = (Row) checkbox.getParent().getParent();
		Label name = (Label) row.getChildren().get(1).getChildren().get(0);
		Label age = (Label) row.getChildren().get(2).getChildren().get(0);
		
		if (checkbox.isChecked() && isCheckedRows()) {

			name.setStyle("text-decoration: line-through;");
			age.setStyle("text-decoration: line-through;");
			
			name1 = name;
			age1 = age;
		} else{
			name.setStyle("");
			age.setStyle("");
			
			if (name1 != null && age1 != null) {
				name1.setStyle("");
				age1.setStyle("");
			}
		}
	}
	
	private boolean isCheckedRows() {
		int countCheckedCheckbox = 0;
		boolean isCheckedRows = false;
		Checkbox checkbox1 = null;
		
		List<Row> rowList = gridPerson.getRows().getChildren();
		for (Row row : rowList) {
			Cell cell = (Cell) row.getChildren().get(0);
			Checkbox checkbox = (Checkbox) cell.getChildren().get(0);
			
			if (checkbox.isChecked()) {
				countCheckedCheckbox++;
				 if (countCheckedCheckbox == 2 && checkbox1 != null) {
					 
					Row row1 = (Row) checkbox1.getParent().getParent();
					Label name = (Label) row1.getChildren().get(1).getChildren().get(0);
					Label age = (Label) row1.getChildren().get(2).getChildren().get(0);
					name.setStyle("text-decoration: line-through;");
					age.setStyle("text-decoration: line-through;"); 
					
					isCheckedRows = true;
					return isCheckedRows;
				}
				 checkbox1 = checkbox; 
			}
		}
		
		/*Row row1 = (Row) checkbox1.getParent().getParent();
		Label name = (Label) row1.getChildren().get(1).getChildren().get(0);
		Label age = (Label) row1.getChildren().get(2).getChildren().get(0);
		name.setStyle("");
		age.setStyle(""); */
		
		return isCheckedRows;
	}

}
