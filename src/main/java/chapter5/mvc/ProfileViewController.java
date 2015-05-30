package chapter5.mvc;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

import services.AuthenticationService;
import services.CommonInfoService;
import services.UserCredential;
import services.UserInfoService;
import chapter5.AuthenticationServiceChapter5Impl;
import chapter5.UserInfoServiceChapter5Impl;
import entity.User;

@SuppressWarnings("serial")
public class ProfileViewController extends SelectorComposer<Component> {
	List<String>  lislCountry = CommonInfoService.getCountryList();
	List<String>  lislLanguage = new LinkedList<String>();
	
	@Wire
	Listbox country;
	
	 //wire components
    @Wire
    Label account;
    @Wire
    Textbox fullName;
    @Wire
    Textbox email;
    @Wire
    Datebox birthday;
    @Wire
    Textbox bio;
    @Wire
    Listbox language;
	
    AuthenticationService authService = new AuthenticationServiceChapter5Impl();
    UserInfoService userInfoService = new UserInfoServiceChapter5Impl();
    
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		lislLanguage.add("Ukrainian");
		lislLanguage.add("English");
		lislLanguage.add("Germany");
		
		
		ListModelList<String> countryModelList = new ListModelList<String>(lislCountry);
		country.setModel(countryModelList);
		
		ListModelList<String> languageModelList = new ListModelList<String>(lislLanguage);
		language.setModel(languageModelList);
		
		refreshProfileView();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void refreshProfileView() {
		   UserCredential cre = authService.getUserCredential();
	        User user = userInfoService.findUser(cre.getAccount());
	        if(user==null){
	            //TODO handle un-authenticated access 
	            return;
	        }
	        
	        account.setValue(user.getAccount());
	        fullName.setValue(user.getFullName());
	        email.setValue(user.getEmail());
	        birthday.setValue(user.getBirthday());
	        bio.setValue(user.getBio());
	        
	        ListModelList countryModel = (ListModelList) country.getModel();
	        countryModel.addToSelection(user.getCountry());
	        
	        ListModelList languageModel = (ListModelList)language.getModel();
	        languageModel.addToSelection("Ukr");
	        
	}
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#saveProfile")
    public void doSaveProfile(){
        UserCredential cre = authService.getUserCredential();
        User user = userInfoService.findUser(cre.getAccount());
        if(user==null){
            //TODO handle un-authenticated access 
            return;
        }
         
        //apply component value to bean
        user.setFullName(fullName.getValue());
        user.setEmail(email.getValue());
        user.setBirthday(birthday.getValue());
        user.setBio(bio.getValue());
         
        Set<String> selection = ((ListModelList)country.getModel()).getSelection();
        
        ListModelList languageModel = (ListModelList) language.getModel();
        Set lang = languageModel.getSelection();      
        
        System.out.println("----------------------" + lang.iterator().next());
        
        if(!selection.isEmpty()){
            user.setCountry(selection.iterator().next());
        }else{
            user.setCountry(null);
        }
         
        userInfoService.updateUser(user);
         
        Clients.showNotification("Your profile is updated");
    }
    
    @Listen("onClick=#reloadProfile")
    public void doReloadProfile(){
        refreshProfileView();
    }
	
}
