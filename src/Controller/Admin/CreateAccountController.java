/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller.Admin;

import Model.Accounts;
import Model.AccountsJpaController;
import Model.Users;
import Model.UsersJpaController;
import View.ViewManager;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author Ahmed
 */
public class CreateAccountController implements Initializable {

    @FXML
    private Button usersManagmentPageBtn;
    @FXML
    private Button accountsPageBtn;
    @FXML
    private Button operationsPageBtn;
    @FXML
    private Button saveNewAccountBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField currencyTF;
    @FXML
    private TextField balanceTF;
    @FXML
    private TextField creationDateTF;
    @FXML
    private ComboBox<String> userName;
    private EntityManagerFactory emf;
    @FXML
    private TextField AccountNumTF;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO      
        this.emf = Persistence.createEntityManagerFactory("BankPU");
        UsersJpaController usersController = new UsersJpaController(emf);
        List<Users> list = usersController.findUsersEntities();

        ObservableList<String> observableArrayList = FXCollections.observableArrayList();
        for (int i = 0; i < list.size(); i++) {
            observableArrayList.add(list.get(i).getUsername());
        }
        userName.setItems(observableArrayList);
    }

    @FXML
    private void showUsersManagmentPage(ActionEvent event) {
        ViewManager.adminPage.changeSceneToUsersManagment();

    }

    @FXML
    private void showAccountsPage(ActionEvent event) {
        ViewManager.adminPage.changeSceneToAccountsManagment();
    }

    @FXML
    private void showOperationsPage(ActionEvent event) {
    }

    @FXML
    private void saveNewAccount(ActionEvent event) throws Exception {
        int AccountNum = Integer.parseInt(AccountNumTF.getText());
        String currency = currencyTF.getText();
        double balance = Double.parseDouble(balanceTF.getText());
//        String creationDate = creationDateTF.getText();
        String UserName = userName.getValue();

        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("Users.findByUsername").setParameter("username", UserName);
        Users userI = (Users) query.getSingleResult();
        int userId = userI.getId();

        AccountsJpaController AccountsController = new AccountsJpaController(emf);
        Accounts Account = new Accounts(1, userId, AccountNum, UserName, currency, balance);
        AccountsController.create(Account);
        ViewManager.adminPage.changeSceneToAccountsManagment();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Account inserted");
        alert.setContentText("Account inserted");
        alert.showAndWait();

    }

    @FXML
    private void cancelAccountCreation(ActionEvent event) {
        ViewManager.adminPage.changeSceneToAccountsManagment();

    }

}
