package chapter4;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.SerializableEventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Vbox;

import services.SidebarPage;
import services.SidebarPageConfig;

public class SidebarChapter4Controller extends SelectorComposer<Component> {
	private static final long serialVersionUID = 1L;

	@Wire
	Grid fnList;
	
	@Wire
	Vbox mainBox;
	
	// services
	SidebarPageConfig pageConfig = new SidebarPageConfigChapter4Impl();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		Rows rows = fnList.getRows();

		for (SidebarPage page : pageConfig.getPages()) {
			Row row = constructSidebarRow(page.getLabel(), page.getIconUri(),
					page.getUri());
			rows.appendChild(row);
		}
		
		createButton();
		
//		createNewGrid();
	}
	
	private void createButton() {

		final Button button = new Button("Show Table");
		button.setId("buttonShow");
		mainBox.appendChild(button);
		
		EventListener<Event> buttonAction = new EventListener<Event>() {
			@Override
			public void onEvent(Event event) throws Exception {
				createNewGrid();
				
				button.setLabel("Done !!!");
			}
		};
		
		button.addEventListener(Events.ON_CLICK, buttonAction);
	}

	private void createNewGrid() {
		Grid newGrid = new Grid();
		
		newGrid.setWidth("250px");
		newGrid.setSclass("sidebar");
		newGrid.appendChild(new Columns());

		Columns columns = newGrid.getColumns();
		
		Column column1 = new Column();
		column1.setLabel("Name");
		Column column2 = new Column();
		column2.setLabel("Age");
		
		columns.appendChild(column1);
		columns.appendChild(column2);
		
		newGrid.appendChild(new Rows());
		
		Rows rows = newGrid.getRows();
		
		Label labelName1 = new Label("Ted");
		Label labelAge1 = new Label("31");
		
		Row row1 = new Row();
		row1.appendChild(labelName1);
		row1.appendChild(labelAge1);

		Label labelName2 = new Label("Kate");
		Label labelAge2 = new Label("23");
		
		Row row2 = new Row();
		row2.appendChild(labelName2);
		row2.appendChild(labelAge2);
		
		rows.appendChild(row1);
		rows.appendChild(row2);
		
		mainBox.appendChild(newGrid);
	}

	private Row constructSidebarRow(String label, String imageSrc, final String locationUri) {
		
		//construct component and hierarchy
		Row row = new Row();
		Image image = new Image(imageSrc);
		Label lab = new Label(label);
		
		row.appendChild(image);
		row.appendChild(lab);
		
		//set style attribute
		row.setSclass("sidebar-fn");
		
		//create and register event listener
		EventListener<Event> actionListener = new SerializableEventListener<Event>() {
			private static final long serialVersionUID = 1L;

			public void onEvent(Event event) throws Exception {
				//redirect current url to new location
				Executions.getCurrent().sendRedirect(locationUri);
			}
		};
		
		row.addEventListener(Events.ON_CLICK, actionListener);

		return row;
	}

}
