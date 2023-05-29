/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller.Admin;

import Model.Accounts;
import Model.Users;
import Model.UsersJpaController;
import View.ViewManager;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
public class UpdateAccountController implements Initializable {

    @FXML
    private TextField AccountNumTF;
    @FXML
    private TextField currencyTF;
    @FXML
    private Button updateAccountBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField balanceTF;
    @FXML
    private ComboBox<String> userName;
    private EntityManagerFactory emf;
    private Accounts oldAccount;

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

        this.oldAccount = AccountsManagmentController.selectedAccounttoUpdate;

        AccountNumTF.setText(String.valueOf(oldAccount.getAccountNumber()));
        currencyTF.setText(oldAccount.getCurrency());
        balanceTF.setText(String.valueOf(oldAccount.getBalance()));
        userName.setValue(oldAccount.getUsername());
    }

    @FXML
    private void updateAccount(ActionEvent event) {
        int AccountNum = Integer.parseInt(AccountNumTF.getText());
        String currency = currencyTF.getText();
        double balance = Double.parseDouble(balanceTF.getText());
        String UserName = userName.getValue();

        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("Users.findByUsername").setParameter("username", UserName);
        Users userI = (Users) query.getSingleResult();
        int userId = userI.getId();

        Accounts newAccount = new Accounts(1, userId, AccountNum, UserName, currency, balance);
        newAccount.setId(oldAccount.getId());

        em.getTransaction().begin();
        em.merge(newAccount);
        em.getTransaction().commit();
        em.close();
        emf.close();
        AccountsManagmentController.updateStage.close();
    }

    @FXML
    private void cancelCreation(ActionEvent event) {
        AccountsManagmentController.updateStage.close();
    }

}
