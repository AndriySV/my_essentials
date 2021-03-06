package tutorial.composer;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import tutorial.model.Car;
import tutorial.model.CarService;
import tutorial.model.CarServiceImpl;

@SuppressWarnings("serial")
public class SearchComposer extends SelectorComposer<Component> {

	@Wire
	private Textbox keywordBox;

	@Wire
	private Listbox carListbox;

	@Wire
	private Label modelLabel;
	@Wire
	private Label makeLabel;
	@Wire
	private Label priceLabel;
	@Wire
	private Label descriptionLabel;

	@Wire
	private Image previewImage;
	
	private CarService carService = new CarServiceImpl();
	
	 @Listen("onSelect = #carListbox")
	   public void showDetail(){
		 Car selected = carListbox.getSelectedItem().getValue();
		 previewImage.setSrc(selected.getPreview());
		 modelLabel.setValue(selected.getModel());
		 makeLabel.setValue(selected.getMake());
	     priceLabel.setValue(selected.getPrice().toString());
	     descriptionLabel.setValue(selected.getDescription());
		 
	 }
	
	@Listen("onClick = #searchButton")
	public void search() {
		String keyword = keywordBox.getValue();
		List<Car> result = carService.search(keyword);
		
		carListbox.setModel(new ListModelList<Car>(result));
	}
}
