/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx.project;

import Model.Users;
import Model.UsersJpaController;
import View.ViewManager;
import java.sql.SQLException;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 *
 * @author Yahya
 */
public class JavaFXProject extends Application {

    @Override
    public void start(Stage primaryStage) throws SQLException, ClassNotFoundException {
       ViewManager.openRegisterPage();
//        User user = new User("omar", "212", "omar@gmail.com", "male", "admin");
//        user.Save();

//         EntityManagerFactory emf = Persistence.createEntityManagerFactory("BankPU");
//         UsersJpaController usersController = new UsersJpaController(emf);
//         Users user = new Users(1, "aa", "432", "aa@gmail.com", "male", "admin");
//         usersController.create(user);


//         List<Users> list = usersController.findUsersEntities();
//         list.forEach(e -> System.out.println(e.getUsername() + " ----- "+e.getEmail()+ " ----- "+e.getRole()));

//         EntityManager em = emf.createEntityManager();
//         Query query = em.createNamedQuery("Users.findByUsername").setParameter("username", "omar");
//         List<Users> list = query.getResultList();
//         list.forEach(e -> System.out.println(e.getUsername() + " ----- "+e.getGender()+ " ----- "+e.getEmail()+ " ----- "+e.getRole()));
         
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
