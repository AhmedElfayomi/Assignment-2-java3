/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Admin;

import static Controller.Admin.UsersManagmentController.updateStage;
import Model.Accounts;
import Model.AccountsJpaController;
import Model.Users;
import Model.UsersJpaController;
import Model.exceptions.IllegalOrphanException;
import Model.exceptions.NonexistentEntityException;
import View.ViewManager;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * FXML Controller class
 *
 * @author Yahya
 */
public class AccountsManagmentController implements Initializable {

    @FXML
    private Button usersManagmentPageBtn;
    @FXML
    private Button accountsPageBtn;
    @FXML
    private Button operationsPageBtn;
    @FXML
    private Button createNewAccountrBtn;
    @FXML
    private Button showAllAccountsBtn;
    @FXML
    private Button updateSelectedAccountBtn;
    @FXML
    private Button deleteSelectedAccountBtn;
    @FXML
    private Button searchAccountBtn;
    @FXML
    private TextField accontSearchTF;
    @FXML
    private TableColumn<Accounts, Integer> idCol;
    @FXML
    private TableColumn<Accounts, Integer> accountNumCol;
    @FXML
    private TableColumn<Accounts, String> usernameCol;
    @FXML
    private TableColumn<Accounts, String> currencyCol;
    @FXML
    private TableColumn<Accounts, Double> balanceCol;
    @FXML
    private TableColumn<Accounts, Date> dateCol;
    private EntityManagerFactory emf;
    @FXML
    private TableView<Accounts> UsersTableView;
    public static Accounts selectedAccounttoUpdate;
    public static Stage updateStage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        idCol.setCellValueFactory(new PropertyValueFactory("id"));
        accountNumCol.setCellValueFactory(new PropertyValueFactory("accountNumber"));
        usernameCol.setCellValueFactory(new PropertyValueFactory("username"));
        currencyCol.setCellValueFactory(new PropertyValueFactory("currency"));
        balanceCol.setCellValueFactory(new PropertyValueFactory("balance"));
        dateCol.setCellValueFactory(new PropertyValueFactory("creationDate"));
        this.emf = Persistence.createEntityManagerFactory("BankPU");

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
    private void showAccountCreationPage(ActionEvent event) {
        ViewManager.adminPage.changeSceneToCreateAccount();
    }

    @FXML
    private void showAllAccounts(ActionEvent event) {
        EntityManager em = emf.createEntityManager();
        List<Accounts> listUsers = em.createNamedQuery("Accounts.findAll").getResultList();
        UsersTableView.getItems().setAll(listUsers);
        em.close();
    }

        @FXML
    private void searchForAnAccount(ActionEvent event) {
        int search = Integer.parseInt(accontSearchTF.getText());
        EntityManager em = emf.createEntityManager();
        List<Accounts> listUsers = em.createNamedQuery("Accounts.findByAccountNumber").setParameter("accountNumber", search).getResultList();
        UsersTableView.getItems().setAll(listUsers);
        em.close();
    } 
    
    @FXML
    private void updateSelectedAccount(ActionEvent event) throws IOException {
         Accounts selected = UsersTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selectedAccounttoUpdate = selected;
            FXMLLoader UpdateAccountLoader = new FXMLLoader(getClass().getResource("/View/AdminFXML/UpdateAccount.fxml"));
            Parent UpdateAccountRoot = UpdateAccountLoader.load();
            Scene UpdateAccountScene = new Scene(UpdateAccountRoot);
            //store loaded fxml in our global stage updateStage and show it
            updateStage = new Stage();
            updateStage.setScene(UpdateAccountScene);
            updateStage.setTitle("Update user " + selectedAccounttoUpdate.getUsername());
            updateStage.show();

        } else {
            Alert warnAlert = new Alert(Alert.AlertType.WARNING);
            warnAlert.setTitle("Select an Account");
            warnAlert.setContentText("Please select an Account from the table view");
            warnAlert.show();
        }
    
    }

    @FXML
    private void deleteSelectedAccount(ActionEvent event) {
            if (UsersTableView.getSelectionModel().getSelectedItem() != null) {
            Accounts selectedUser = UsersTableView.getSelectionModel().getSelectedItem();
            Alert deleteConfirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            deleteConfirmAlert.setTitle("Account delete");
            deleteConfirmAlert.setContentText("Are you sure to delete this Account ?");
            deleteConfirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        Integer id = selectedUser.getId();
                        EntityManager em = emf.createEntityManager();
                        AccountsJpaController AccountsController = new AccountsJpaController(emf);
                        AccountsController.destroy(id);

                    } catch (NonexistentEntityException ex) {
                        Logger.getLogger(AccountsManagmentController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Alert deletedSuccessAlert = new Alert(Alert.AlertType.INFORMATION);
                    deletedSuccessAlert.setTitle("Account deleted");
                    deletedSuccessAlert.setContentText("Account deleted");
                    deletedSuccessAlert.show();
                }
            });
        } else {
            Alert warnAlert = new Alert(Alert.AlertType.WARNING);
            warnAlert.setTitle("Select an Account");
            warnAlert.setContentText("Please select an Account from the table view");
            warnAlert.show();
        }
    }



}
