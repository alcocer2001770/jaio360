/*
 * Copyright 2009 Prime Technology.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primefaces.examples.view;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

public class PPRBean implements Serializable {

	private String firstname;
	
	private String surname;
	
	private String country;

	private String city;
	
	private Map<String,String> countries = new HashMap<String, String>();

	private Map<String,Map<String,String>> citiesData = new HashMap<String, Map<String,String>>();
	
	private Map<String,String> cities = new HashMap<String, String>();
	
	private Map<String,String> rooms = new HashMap<String, String>();
	
	private Map<String,Map<String,String>> itemsData = new HashMap<String, Map<String,String>>();
	
	private Map<String,String> items = new HashMap<String, String>();
	
	private String room;

	private String item;

	private String[] selectedCountries;

	public PPRBean() {
		countries.put("Peru", "Peru");
		countries.put("España", "España");
		countries.put("Colombia", "Colombia");
		
		Map<String,String> citiesPeru = new HashMap<String, String>();
		citiesPeru.put("Lima", "Lima");
		citiesPeru.put("Huancayo", "Huancayo");
		citiesPeru.put("Arequipa", "Arequipa");
		
		Map<String,String> citiesEspania = new HashMap<String, String>();
		citiesEspania.put("Madrid", "Madrid");
		citiesEspania.put("Cataluña", "Cataluña");
		citiesEspania.put("Toledo", "Toledo");
		
		Map<String,String> citiesColombia = new HashMap<String, String>();
		citiesColombia.put("Cali", "Cali");
		citiesColombia.put("Bogota", "Bogota");
		citiesColombia.put("Medellin", "Medellin");
		
		citiesData.put("Peru", citiesPeru);
		citiesData.put("España", citiesEspania);
		citiesData.put("Colombia", citiesColombia);
		
		rooms.put("Living Room", "Living Room");
		rooms.put("Kitchen", "Kitchen");
		rooms.put("Bedroom", "Bedroom");
		
		Map<String,String> livingRoomItems = new HashMap<String, String>();
		livingRoomItems.put("Sofa", "Sofa");
		livingRoomItems.put("Armchair", "Armchair");
		livingRoomItems.put("Coffee Table", "Coffee Table");
		
		Map<String,String> kitchenItems = new HashMap<String, String>();
		kitchenItems.put("Refrigirator", "Refrigirator");
		kitchenItems.put("Dishwasher", "Dishwasher");
		kitchenItems.put("Oven", "Oven");
		
		Map<String,String> bedroomItems = new HashMap<String, String>();
		bedroomItems.put("Bed", "Bed");
		bedroomItems.put("Wardrobe", "Wardrobe");
		bedroomItems.put("Drawer Chest", "Drawer Chest");
		
		itemsData.put("Living Room", livingRoomItems);
		itemsData.put("Kitchen", kitchenItems);
		itemsData.put("Bedroom", bedroomItems);
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public void savePerson(ActionEvent actionEvent) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("You've registered"));
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Map<String, String> getCountries() {
		return countries;
	}

	public void setCountries(Map<String, String> countries) {
		this.countries = countries;
	}
	
	public Map<String, Map<String, String>> getCitiesData() {
		return citiesData;
	}

	public void setCitiesData(Map<String, Map<String, String>> citiesData) {
		this.citiesData = citiesData;
	}
	
	public Map<String, String> getCities() {
		return cities;
	}

	public void setCities(Map<String, String> cities) {
		this.cities = cities;
	}
	
	public void handleCountryChange() {
		if(country !=null && !country.equals(""))
			cities = citiesData.get(country);
		else
			cities = new HashMap<String, String>();
	}
        
        
	
	public void handleRoomChange(ActionEvent actionEvent) {
		if(room !=null && !room.equals(""))
			items = itemsData.get(room);
		else
			items = new HashMap<String, String>();
	}
	
	private boolean checked;

	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public String[] getSelectedCountries() {
		return selectedCountries;
	}
	public void setSelectedCountries(String[] selectedCountries) {
		this.selectedCountries = selectedCountries;
	}
	
	public String getSelectedCountriesAsString() {
		if(selectedCountries == null)
			return "";
		
		StringBuffer buffer = new StringBuffer();
		
		for(String country : selectedCountries) {
			buffer.append("(");
			buffer.append(country);
			buffer.append(")");
		}
		
		return buffer.toString();
	}
	
	public Map<String, String> getRooms() {
		return rooms;
	}
	public void setRooms(Map<String, String> rooms) {
		this.rooms = rooms;
	}

	public Map<String, Map<String, String>> getItemsData() {
		return itemsData;
	}
	public void setItemsData(Map<String, Map<String, String>> itemsData) {
		this.itemsData = itemsData;
	}

	public Map<String, String> getItems() {
		return items;
	}
	public void setItems(Map<String, String> items) {
		this.items = items;
	}

	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}

	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}

    public void displayLocation() {
        FacesMessage msg = new FacesMessage("Selected", "country:" + country + ", city: " + city);

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void reset() {
        RequestContext.getCurrentInstance().reset("form:panel");
    }
    
    public void resetFail() {
        this.firstname = null;
        this.surname = null;
        
        FacesMessage msg = new FacesMessage("Model reset, but it won't work.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
