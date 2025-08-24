package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CustomerDetailsController implements Initializable {

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnLoad;

    @FXML
    private JFXButton btnShowItemDetails;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colCity;

    @FXML
    private TableColumn<?, ?> colCustID;

    @FXML
    private TableColumn<?, ?> colDOB;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colPostalCode;

    @FXML
    private TableColumn<?, ?> colProvince;

    @FXML
    private TableColumn<?, ?> colSalary;

    @FXML
    private TableColumn<?, ?> colTitle;

    @FXML
    private JFXComboBox<String> comboCity;

    @FXML
    private JFXComboBox<String> comboProvince;

    @FXML
    private JFXComboBox<String> comboTitle;

    @FXML
    private DatePicker pickerDOB;

    @FXML
    private TableView<Customer> tblCustomerDetails;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtCustID;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtPostalCode;

    @FXML
    private JFXTextField txtSalary;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String CustID=txtCustID.getText();
        String Title=comboTitle.getValue();
        String Name=txtName.getText();
        LocalDate DOB=pickerDOB.getValue();
        double Salary= Double.parseDouble(txtSalary.getText());
        String Address=txtAddress.getText();
        String City=comboCity.getValue();
        String Province=comboProvince.getValue();
        String PostalCode=txtPostalCode.getText();

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Thoga_Kade","root","1234");

            String SQL="INSERT INTO Customers VALUES(?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setObject(1,CustID);
            preparedStatement.setObject(2,Title);
            preparedStatement.setObject(3,Name);
            preparedStatement.setObject(4,DOB);
            preparedStatement.setObject(5,Salary);
            preparedStatement.setObject(6,Address);
            preparedStatement.setObject(7,City);
            preparedStatement.setObject(8,Province);
            preparedStatement.setObject(9,PostalCode);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void btnLoadOnAction(ActionEvent event) {
        ObservableList<Customer>customers= FXCollections.observableArrayList();

        try {
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/Thoga_Kade","root","1234");

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT*FROM Customers;");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Customer customer=new Customer(
                        resultSet.getString("Cust_ID"),
                        resultSet.getString("Title"),
                        resultSet.getString("Name"),
                        resultSet.getDate("DOB").toLocalDate(),
                        resultSet.getDouble("Salary"),
                        resultSet.getString("Address"),
                        resultSet.getString("City"),
                        resultSet.getString("Province"),
                        resultSet.getString("Postal_Code")
                );

                customers.add(customer);
                System.out.println(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        colCustID.setCellValueFactory(new PropertyValueFactory<>("custID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
        colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        tblCustomerDetails.setItems(customers);
    }

    Stage ShowItemDetailsStage = new Stage();

    @FXML
    void btnShowItemDetailsOnAction(ActionEvent event) {

        try {
            ShowItemDetailsStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/item_details.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ShowItemDetailsStage.show();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String>Types=FXCollections.observableArrayList(
                "Mr.",
                "Miss.",
                "Mrs",
                "Ms"
        );

        comboTitle.setItems(Types);

        ObservableList<String>CityType=FXCollections.observableArrayList(
                "Ampara",
                "Anuradhapura",
                "Badulla",
                "Batticaloa",
                "Colombo",
                "Galle",
                "Gampaha",
                "Hambanthota",
                "Jaffna",
                "Kandy",
                "Kegalle",
                "Killinochchi",
                "Kurunagala",
                "Mannar",
                "Matale",
                "Matara",
                "Monaragala",
                "Mullaitivu",
                "Nuwara Eliya",
                "Polonnaruwa",
                "Puttalam",
                "Ratnapura",
                "Trincomalee",
                "Vavuniya",
                "Kalutara",
                "Panadura"
        );

        comboCity.setItems(CityType);

        ObservableList<String>ProvinceType=FXCollections.observableArrayList(
                "Central",
                "Western",
                "Eastern",
                "Nothern",
                "Uva",
                "Sabaragamuwa",
                "Southern",
                "North Western",
                "North Central"
        );

        comboProvince.setItems(ProvinceType);
    }
}
