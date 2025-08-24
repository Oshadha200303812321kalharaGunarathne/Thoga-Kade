package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;

import java.sql.*;

public class ItemDetailsController {

    @FXML
    private TableView <Item> tblItemDetails;
    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private Button btnLoad;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colPackSize;

    @FXML
    private TableColumn<?, ?> colQtyOnHand;

    @FXML
    private TableColumn<?, ?> colUnitPrize;

    @FXML
    private JFXTextField txtDescription;

    @FXML
    private JFXTextField txtItemCode;

    @FXML
    private JFXTextField txtPackSize;

    @FXML
    private JFXTextField txtQtyOnHand;

    @FXML
    private JFXTextField txtUnitPrize;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        String ItemCode=txtItemCode.getText();
        String Description=txtDescription.getText();
        String PackSize=txtPackSize.getText();
        String UnitPrize=txtUnitPrize.getText();
        String QtyOnHand=txtQtyOnHand.getText();

        try {
            Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/Thoga_Kade","root","1234");

            String SQL="INSERT INTO Items VALUES(?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setObject(1,ItemCode);
            preparedStatement.setObject(2,Description);
            preparedStatement.setObject(3,PackSize);
            preparedStatement.setObject(4,UnitPrize);
            preparedStatement.setObject(5,QtyOnHand);

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
        ObservableList<Item>items= FXCollections.observableArrayList();

        try {
            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/Thoga_Kade","root","1234");

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT*FROM Items;");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Item item=new Item(
                        resultSet.getString("Item_Code"),
                        resultSet.getString("Description"),
                        resultSet.getString("Pack_Size"),
                        resultSet.getString("Unit_Price"),
                        resultSet.getString("Qty_On_Hand")
                );

                items.add(item);
                System.out.println(item);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colPackSize.setCellValueFactory(new PropertyValueFactory<>("packSize"));
        colUnitPrize.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));

        tblItemDetails.setItems(items);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

}

