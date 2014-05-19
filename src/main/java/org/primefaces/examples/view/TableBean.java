/*
 * Copyright 2009-2011 Prime Technology.
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

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.primefaces.examples.domain.Car;
import org.primefaces.model.LazyDataModel;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import java.util.*;
import javax.faces.component.UIComponent;
import javax.faces.model.SelectItem;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.ColumnResizeEvent;
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.ToggleEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.examples.domain.Customer;
import org.primefaces.examples.domain.ManufacturerSale;
import org.primefaces.examples.domain.Player;
import org.primefaces.examples.domain.Stats;
import org.primefaces.examples.domain.User;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

public class TableBean implements Serializable {

    private final static List<String> VALID_COLUMN_KEYS = Arrays.asList("model", "manufacturer", "year", "color", "row", "nrolicencia", "tarifa", "descripcion","confirmado");
    private final static Logger logger = Logger.getLogger(TableBean.class.getName());
    private final static String[] colors;
    private final static String[] descripcion;
    private final static String[] tarifa;
    private final static Integer[] nrolicencia;

        
    private final static String[] manufacturers;
    private String theme;
    private String columnTemplate = "model manufacturer year";

    static {
        
        descripcion= new String[10];
        descripcion[0] = "Primer Cliente";
        descripcion[1] = "Segundo Cliente";
        descripcion[2] = "Tercer Cliente";
        descripcion[3] = "Cuarto Cliente";
        descripcion[4] = "Quinto Cliente";
        descripcion[5] = "Sexto Cliente";
        descripcion[6] = "Septimo Cliente";
        descripcion[7] = "Octavo Cliente";
        descripcion[8] = "Noveno Cliente";
        descripcion[9] = "Decimo Cliente";
        
        tarifa= new String[10];
        tarifa[0] = "Tarifa minima -2$";
        tarifa[1] = "Tarifa regular -2$";        
        tarifa[2] = "Tarifa regular -2$";        
        tarifa[3] = "Tarifa regular -2$";        
        tarifa[4] = "Tarifa regular -2$";        
        tarifa[5] = "Tarifa regular -2$";        
        tarifa[6] = "Tarifa regular -2$";        
        tarifa[7] = "Tarifa regular -2$";        
        tarifa[8] = "Tarifa regular -2$";        
        tarifa[9] = "Tarifa regular -2$";        
              
        
        nrolicencia= new Integer[10];
        nrolicencia[0] = 10;
        nrolicencia[1] = 40;               
        nrolicencia[2] = 60;
        nrolicencia[3] = 60;
        nrolicencia[4] = 60;
        nrolicencia[5] = 60;
        nrolicencia[6] = 60;
        nrolicencia[7] = 60;
        nrolicencia[8] = 60;
        nrolicencia[9] = 60;
        
        colors = new String[10];
        colors[0] = "Black";
        colors[1] = "White";
        colors[2] = "Green";
        colors[3] = "Red";
        colors[4] = "Blue";
        colors[5] = "Orange";
        colors[6] = "Silver";
        colors[7] = "Yellow";
        colors[8] = "Brown";
        colors[9] = "Maroon";

        manufacturers = new String[10];
        manufacturers[0] = "Mercedes";
        manufacturers[1] = "BMW";
        manufacturers[2] = "Volvo";
        manufacturers[3] = "Audi";
        manufacturers[4] = "Renault";
        manufacturers[5] = "Opel";
        manufacturers[6] = "Volkswagen";
        manufacturers[7] = "Chrysler";
        manufacturers[8] = "Ferrari";
        manufacturers[9] = "Ford";
    }
    private List<Car> filteredCars;
    private List<Customer> filteredCustomers;
    private List<Customer> filteredUsers;
   
    private List<Car> cars;
    private List<Customer> customers;
    private List<Customer> premiumCustomers;
    private List<Customer> freeCustomers;
    private List<Car> carsSmall;
    private List<Car> carsLarge;
    private List<Car> frozenCars;
    private Date date = new Date();
    private Car selectedCar;
    private Car[] selectedCars;
    private Customer selectedCustomer;
    private Customer[] selectedCustomers;
    private User selectedUser;    
    private User[] selectedUsers;    
    private LazyDataModel<Car> lazyModel;
    private List<ManufacturerSale> sales;
    private String columnName;
    private SelectItem[] manufacturerOptions;
    private List<Car> droppedCars;
    private List<ColumnModel> columns = new ArrayList<ColumnModel>();
    private boolean editMode;
    private List<Player> players;
    private List<User> users;
    private UserDataModel administradorUsersModel;    
    private UserDataModel evaluadorUsersModel;
    private CustomerDataModel freeCustomersModel;
    private CustomerDataModel premiumCustomerModel;
    private CarDataModel smallCarsModel;
    private CarDataModel mediumCarsModel;
    private TreeNode availableColumns;

    public TableBean() {
        cars = new ArrayList<Car>();
        customers = new ArrayList<Customer>();
        premiumCustomers = new ArrayList<Customer>();
        freeCustomers = new ArrayList<Customer>();

        carsSmall = new ArrayList<Car>();
        carsLarge = new ArrayList<Car>();
        droppedCars = new ArrayList<Car>();
        frozenCars = new ArrayList<Car>();

        populateRandomCars(cars, 50);
        populateCustomers(customers, 10);

//        populateRandomCars(cars, 50);
        populateRandomCars(carsSmall, 9);
        populateRandomCars(carsLarge, 200);
        populateRandomCars(frozenCars, 2);
        populateRandomSales();

        createDynamicColumns();

        manufacturerOptions = createFilterOptions(manufacturers);

        populatePlayers();

        populateUsers();

        administradorUsersModel = new UserDataModel(users);
        smallCarsModel = new CarDataModel(carsSmall);
        mediumCarsModel = new CarDataModel(cars);

        lazyModel = new LazyCarDataModel(cars);

        createAvailableColumns();
    }

    public LazyDataModel<Car> getLazyModel() {
        return lazyModel;
    }

    public Car[] getSelectedCars() {
        return selectedCars;
    }

    public void setSelectedCars(Car[] selectedCars) {
        this.selectedCars = selectedCars;
    }

    public Car getSelectedCar() {
        return selectedCar;
    }

    public void setSelectedCar(Car selectedCar) {
        this.selectedCar = selectedCar;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private void populateRandomCars(List<Car> list, int size) {
        for (int i = 0; i < size; i++) {
            list.add(new Car(getRandomModel(), getRandomYear(), getRandomManufacturer(), getRandomColor()));
        }
    }
    
    private void populateCustomers(List<Customer> list, int size) {
        for (int i = 0; i < size; i++) {
            list.add(new Customer(getRandomDescripcion(), getRandomTarifa(), getRandomLicencias()));
        }
    }

    private void populateLazyRandomCars(List<Car> list, int size) {
        for (int i = 0; i < size; i++) {
            list.add(new Car(getRandomModel(), getRandomYear(), getRandomManufacturer(), getRandomColor()));
        }
    }

    public List<Car> getFilteredCars() {
        return filteredCars;
    }

    public void setFilteredCars(List<Car> filteredCars) {
        this.filteredCars = filteredCars;
    }

    public List<Car> getCars() {
        return cars;
    }

    public List<Car> getCarsSmall() {
        return carsSmall;
    }

    public List<Car> getCarsLarge() {
        return carsLarge;
    }

    public List<Car> getFrozenCars() {
        return frozenCars;
    }

    private int getRandomYear() {
        return (int) (Math.random() * 50 + 1960);
    }

    private String getRandomColor() {
        return colors[(int) (Math.random() * 10)];
    }
    
    private String getRandomDescripcion() {
        return descripcion[(int) (Math.random() * 10)];
    }
    
    private String getRandomTarifa() {
        return tarifa[(int) (Math.random() * 10)];
    }
    
    private int getRandomLicencias() {
        return nrolicencia[(int) (Math.random() * 2)];
    }
    
    private int getRandomRow() {
        return  (int)(Math.random() * 10);
    }
     
    
    private int getCostoTotal(int index) {
        return (Integer.parseInt(tarifa[index]))*nrolicencia[index];
    }
      

    private String getRandomManufacturer() {
        return manufacturers[(int) (Math.random() * 10)];
    }

    public int getRandomPrice() {
        return (int) (Math.random() * 100000);
    }

    private int getRandomSale() {
        return (int) (Math.random() * 100000);
    }

    private int getRandomProfit() {
        return (int) (Math.random() * 100);
    }

    private String getRandomModel() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);

        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        for (int i = 0; i < header.getPhysicalNumberOfCells(); i++) {
            HSSFCell cell = header.getCell(i);

            cell.setCellStyle(cellStyle);
        }
    }

    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String logo = servletContext.getRealPath("") + File.separator + "images" + File.separator + "prime_logo.png";

        pdf.add(Image.getInstance(logo));
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void save() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Changes Saved"));
    }

    public void onRowSelect(SelectEvent event) {
        FacesMessage msg = new FacesMessage("Car Selected", ((Car) event.getObject()).getModel());
        FacesMessage msg1 = new FacesMessage("Customer Selected", ((Customer) event.getObject()).getTarifa());

//        FacesContext.getCurrentInstance().addMessage(null, msg);
        FacesContext.getCurrentInstance().addMessage(null, msg1);
    }

    public void onRowUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage("Car Unselected", ((Car) event.getObject()).getModel());
        FacesMessage msg1 = new FacesMessage("Customer Unselected", ((Customer) event.getObject()).getTarifa());

//        FacesContext.getCurrentInstance().addMessage(null, msg);
        FacesContext.getCurrentInstance().addMessage(null, msg1);
    }

    public String onRowSelectNavigate(SelectEvent event) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selectedCar", event.getObject());
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selectedCustomer", event.getObject());
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("selectedUser", event.getObject());

//        return "carDetail?faces-redirect=true";
        return "customerDetail?faces-redirect=true";
    }

    public List<ManufacturerSale> getSales() {
        return sales;
    }

    private void populateRandomSales() {
        sales = new ArrayList<ManufacturerSale>();

        for (int i = 0; i < 10; i++) {
            sales.add(new ManufacturerSale(manufacturers[i], getRandomSale(), getRandomSale(), getRandomProfit(), getRandomProfit()));
        }
    }

    public int getLastYearTotal() {
        int total = 0;

        for (ManufacturerSale sale : getSales()) {
            total += sale.getLastYearSale();
        }

        return total;
    }

    public int getThisYearTotal() {
        int total = 0;

        for (ManufacturerSale sale : getSales()) {
            total += sale.getThisYearSale();
        }

        return total;
    }

    public List<ColumnModel> getColumns() {
        return columns;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String[] getManufacturers() {
        return manufacturers;
    }

    public String[] getColors() {
        return colors;
    }

    private SelectItem[] createFilterOptions(String[] data) {
        SelectItem[] options = new SelectItem[data.length + 1];

        options[0] = new SelectItem("", "Select");
        for (int i = 0; i < data.length; i++) {
            options[i + 1] = new SelectItem(data[i], data[i]);
        }

        return options;
    }

    public SelectItem[] getManufacturerOptions() {
        return manufacturerOptions;
    }

    public void onCarDrop(DragDropEvent ddEvent) {
        Car car = ((Car) ddEvent.getData());

        droppedCars.add(car);
        carsSmall.remove(car);
    }

    public List<Car> getDroppedCars() {
        return droppedCars;
    }

    public void onCustomerDrop(DragDropEvent ddEvent) {
        Customer customer = ((Customer) ddEvent.getData());

        freeCustomers.add(customer);
        premiumCustomers.remove(customer);
    }

    private void createAvailableColumns() {
        availableColumns = new DefaultTreeNode("Root", null);
        TreeNode root = new DefaultTreeNode("Columns", availableColumns);
        root.setExpanded(true);
        TreeNode model = new DefaultTreeNode("column", new ColumnModel("Model", "model"), root);
        TreeNode year = new DefaultTreeNode("column", new ColumnModel("Year", "year"), root);
        TreeNode manufacturer = new DefaultTreeNode("column", new ColumnModel("Manufacturer", "manufacturer"), root);
        TreeNode color = new DefaultTreeNode("column", new ColumnModel("Color", "color"), root);
        TreeNode descripcion = new DefaultTreeNode("column", new ColumnModel("Descripcion", "descripcion"), root);
        TreeNode nrolicencia = new DefaultTreeNode("column", new ColumnModel("Nrolicencia", "nrolicencia"), root);
        TreeNode tarifa = new DefaultTreeNode("column", new ColumnModel("Tarifa", "tarifa"), root);
        TreeNode confirmado = new DefaultTreeNode("column", new ColumnModel("Confirmado", "confirmado"), root);
    }

    private void populateUsers() {
        users = new ArrayList<User>();

        User favio = new User();
        favio.setAge(30);
        favio.setEmail("favito.flores@gmail.com");
        favio.setBussiness("Cavali");
        favio.setCity("Lima");
        favio.setCreationdate(date);
        favio.setLastname("Flores");
        favio.setFirstname("Favio");

        users.add(favio);

        User alvaro = new User();
        alvaro.setAge(33);
        alvaro.setEmail("alcocer2001770@gmail.com");
        alvaro.setBussiness("CosapiSoft");
        alvaro.setCity("Lima");
        alvaro.setCreationdate(date);
        alvaro.setLastname("Alcocer");
        alvaro.setFirstname("Alvaro");
        users.add(alvaro);
    }

    static public class ColumnModel implements Serializable {

        private String header;
        private String property;

        public ColumnModel(String header, String property) {
            this.header = header;
            this.property = property;
        }

        public String getHeader() {
            return header;
        }

        public String getProperty() {
            return property;
        }
    }

    public void delete() {
        carsSmall.remove(selectedCar);
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public String navigate() {
        return "home";
    }

    public void onEdit(RowEditEvent event) {
//        FacesMessage msg = new FacesMessage("Car Edited", ((Car) event.getObject()).getModel());
        FacesMessage msg1 = new FacesMessage("Customer Edited", ((Customer) event.getObject()).getEmail());

        FacesContext.getCurrentInstance().addMessage(null, msg1);
    }

    public void onCancel(RowEditEvent event) {
//        FacesMessage msg = new FacesMessage("Car Cancelled", ((Car) event.getObject()).getModel());
        FacesMessage msg1 = new FacesMessage("Customer Cancelled", ((Customer) event.getObject()).getEmail());

        FacesContext.getCurrentInstance().addMessage(null, msg1);
    }

    public void onResize(ColumnResizeEvent event) {
        FacesMessage msg = new FacesMessage("Column " + event.getColumn().getClientId() + " resized", "W:" + event.getWidth() + ", H:" + event.getHeight());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void populatePlayers() {
        players = new ArrayList<Player>();


        Player messi = new Player("Messi", 10);
        messi.getStats().add(new Stats("2005-2006", 4, 2));
        messi.getStats().add(new Stats("2006-2007", 10, 7));
        messi.getStats().add(new Stats("2007-2008", 16, 10));
        messi.getStats().add(new Stats("2008-2009", 32, 15));
        messi.getStats().add(new Stats("2009-2010", 51, 22));
        messi.getStats().add(new Stats("2010-2011", 55, 30));
        players.add(messi);

        Player xavi = new Player("Xavi", 6);
        xavi.getStats().add(new Stats("2005-2006", 6, 15));
        xavi.getStats().add(new Stats("2006-2007", 10, 20));
        xavi.getStats().add(new Stats("2007-2008", 12, 22));
        xavi.getStats().add(new Stats("2008-2009", 9, 24));
        xavi.getStats().add(new Stats("2009-2010", 8, 21));
        xavi.getStats().add(new Stats("2010-2011", 10, 25));
        players.add(xavi);

        Player iniesta = new Player("Iniesta", 10);
        iniesta.getStats().add(new Stats("2005-2006", 4, 12));
        iniesta.getStats().add(new Stats("2006-2007", 7, 9));
        iniesta.getStats().add(new Stats("2007-2008", 10, 14));
        iniesta.getStats().add(new Stats("2008-2009", 15, 17));
        iniesta.getStats().add(new Stats("2009-2010", 14, 16));
        iniesta.getStats().add(new Stats("2010-2011", 17, 22));
        players.add(iniesta);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public CarDataModel getMediumCarsModel() {
        return mediumCarsModel;
    }

    public CarDataModel getSmallCarsModel() {
        return smallCarsModel;
    }

    public TreeNode getAvailableColumns() {
        return availableColumns;
    }

    public void deleteCar() {
        carsSmall.remove(selectedCar);
    }

    public String getColumnTemplate() {
        return columnTemplate;
    }

    public void setColumnTemplate(String columnTemplate) {
        this.columnTemplate = columnTemplate;
    }

    public void updateColumns() {
        //reset table state
        UIComponent table = FacesContext.getCurrentInstance().getViewRoot().findComponent(":form:cars");
        table.setValueExpression("sortBy", null);

        //update columns
        createDynamicColumns();
    }

    public void createDynamicColumns() {
        String[] columnKeys = columnTemplate.split(" ");
        columns.clear();

        for (String columnKey : columnKeys) {
            String key = columnKey.trim();

            if (VALID_COLUMN_KEYS.contains(key)) {
                columns.add(new ColumnModel(columnKey.toUpperCase(), columnKey));
            }
        }
    }

    public void onRowToggle(ToggleEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Row State " + event.getVisibility(),
                "Model:" + ((Car) event.getData()).getModel());

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void treeToTable() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String property = params.get("property");
        String droppedColumnId = params.get("droppedColumnId");
        String dropPos = params.get("dropPos");

        String[] droppedColumnTokens = droppedColumnId.split(":");
        int draggedColumnIndex = Integer.parseInt(droppedColumnTokens[droppedColumnTokens.length - 1]);
        int dropColumnIndex = draggedColumnIndex + Integer.parseInt(dropPos);

        //add to columns
        this.columns.add(dropColumnIndex, new ColumnModel(property.toUpperCase(), property));

        //remove from nodes
        TreeNode root = availableColumns.getChildren().get(0);
        for (TreeNode node : root.getChildren()) {
            ColumnModel model = (ColumnModel) node.getData();
            if (model.getProperty().equals(property)) {
                root.getChildren().remove(node);
                break;
            }
        }
    }

    public void tableToTree() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int colIndex = Integer.parseInt(params.get("colIndex"));

        //remove from table
        ColumnModel model = this.columns.remove(colIndex);

        //add to nodes
        TreeNode property = new DefaultTreeNode("column", model, availableColumns.getChildren().get(0));
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void selectCarFromDialog(Car car) {
        RequestContext.getCurrentInstance().closeDialog(car);
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    public Customer[] getSelectedCustomers() {
        return selectedCustomers;
    }

    public void setSelectedCustomers(Customer[] selectedCustomers) {
        this.selectedCustomers = selectedCustomers;
    }

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer selectedCustomer) {
        this.selectedCustomer = selectedCustomer;
    }

    public List<Customer> getFilteredCustomers() {
        return filteredCustomers;
    }

    public void setFilteredCustomers(List<Customer> filteredCustomers) {
        this.filteredCustomers = filteredCustomers;
    }

    public List<Customer> getPremiumCustomers() {
        return premiumCustomers;
    }

    public void setPremiumCustomers(List<Customer> premiumCustomers) {
        this.premiumCustomers = premiumCustomers;
    }

    public List<Customer> getFreeCustomers() {
        return freeCustomers;
    }

    public void setFreeCustomers(List<Customer> freeCustomers) {
        this.freeCustomers = freeCustomers;
    }

    public CustomerDataModel getFreeCustomersModel() {
        return freeCustomersModel;
    }

    public void setFreeCustomersModel(CustomerDataModel freeCustomersModel) {
        this.freeCustomersModel = freeCustomersModel;
    }

    public CustomerDataModel getPremiumCustomerModel() {
        return premiumCustomerModel;
    }

    public void setPremiumCustomerModel(CustomerDataModel premiumCustomerModel) {
        this.premiumCustomerModel = premiumCustomerModel;
    }
    
    public static String[] getDescripcion() {
        return descripcion;
    }
    
     public static String[] getTarifa() {
        return tarifa;
    }
     
     public static Integer[] getNrolicencia() {
        return nrolicencia;
    }
     
      public List<Customer> getFilteredUsers() {
        return filteredUsers;
    }

    public void setFilteredUsers(List<Customer> filteredUsers) {
        this.filteredUsers = filteredUsers;
    }
    
    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public User[] getSelectedUsers() {
        return selectedUsers;
    }

    public void setSelectedUsers(User[] selectedUsers) {
        this.selectedUsers = selectedUsers;
    }
    
    public UserDataModel getAdministradorUsersModel() {
        return administradorUsersModel;
    }

    public void setAdministradorUsersModel(UserDataModel administradorUsersModel) {
        this.administradorUsersModel = administradorUsersModel;
    }

    public UserDataModel getEvaluadorUsersModel() {
        return evaluadorUsersModel;
    }

    public void setEvaluadorUsersModel(UserDataModel evaluadorUsersModel) {
        this.evaluadorUsersModel = evaluadorUsersModel;
    }
}