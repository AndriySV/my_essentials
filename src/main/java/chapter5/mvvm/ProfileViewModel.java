package chapter5.mvvm;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.util.Clients;

import services.AuthenticationService;
import services.CommonInfoService;
import services.UserCredential;
import services.UserInfoService;
import chapter5.AuthenticationServiceChapter5Impl;
import chapter5.UserInfoServiceChapter5Impl;
import entity.User;

public class ProfileViewModel {
	// services
	AuthenticationService authService = new AuthenticationServiceChapter5Impl();
	UserInfoService userInfoService = new UserInfoServiceChapter5Impl();

	// data for the view
	User currentUser;

	public User getCurrentUser() {
		return currentUser;
	}

	public List<String> getCountryList() {
		return CommonInfoService.getCountryList();
	}

	@Init
	// @Init annotates a initial method
	public void init() {
		UserCredential cre = authService.getUserCredential();
		currentUser = userInfoService.findUser(cre.getAccount());
		if (currentUser == null) {
			// TODO handle un-authenticated access
			return;
		}
	}
	
    @Command //@Command annotates a command method 
    @NotifyChange("currentUser")
    public void save(){
        currentUser = userInfoService.updateUser(currentUser);
        Clients.showNotification("Your profile is updated");
    }
 
    @Command
    @NotifyChange("currentUser")
    public void reload(){
        UserCredential cre = authService.getUserCredential();
        currentUser = userInfoService.findUser(cre.getAccount());
    }
	
}
