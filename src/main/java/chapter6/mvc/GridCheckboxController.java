package chapter6.mvc;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.CheckEvent;
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
	public void onCheck$selectedAllCheckBox(CheckEvent checkEvent) {

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

			List rowList = gridPerson.getRows().getChildren();
			for (Object obj : rowList) {
				Row row = (Row) obj;
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

	private void lineThrough(CheckEvent checkEvent) {

	}

}
