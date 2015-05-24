package chapter5.mvc;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;

import services.CommonInfoService;

@SuppressWarnings("serial")
public class ProfileViewController extends SelectorComposer<Component> {
	
	List<String>  lislCountry = CommonInfoService.getCountryList();
	
	@Wire
	Listbox country;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		ListModelList<String> countryModelList = new ListModelList<String>(lislCountry);
		country.setModel(countryModelList);
		
	}
	
}
