package tutorial.composer;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

import tutorial.model.Car;
import tutorial.model.CarService;
import tutorial.model.CarServiceImpl;

public class SearchViewModel {

	private String keyword;
	private List<Car> carList;
	private Car selectedCar;

	private CarService carService = new CarServiceImpl();
	
	@Command
	@NotifyChange("carList")
	public void search() {
		carList = carService.search(keyword);
	}
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public List<Car> getCarList() {
		return carList;
	}
	public void setCarList(List<Car> carList) {
		this.carList = carList;
	}
	public Car getSelectedCar() {
		return selectedCar;
	}
	public void setSelectedCar(Car selectedCar) {
		this.selectedCar = selectedCar;
	}
	
}