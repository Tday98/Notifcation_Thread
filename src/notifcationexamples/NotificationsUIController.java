/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notifcationexamples;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import taskers.*;

/**
 * FXML Controller class
 *
 * @author dalemusser
 */
public class NotificationsUIController  implements Initializable, Notifiable {

    @FXML
    private TextArea textArea;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    
    private Task1 task1;
    private Task2 task2;
    private Task3 task3;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void start(Stage stage) {
        stage.setOnCloseRequest((WindowEvent we) -> {
            if (task1 != null) task1.end();
            if (task2 != null) task2.end();
            if (task3 != null) task3.end();
        });
    }
    
    @FXML
    public void startTask1(ActionEvent event) {
        if (task1 == null) {
            button1.setText("End Task 1");
            System.out.println("start task 1");
            task1 = new Task1(2147483647, 1000000);
            task1.setNotificationTarget(this);
            task1.start();
        } else {
            // This is for when the button is pressed again and a task is running
            button1.setText("Start Task 1");
            task1.end();
        }
    }
    
    @Override
    public void notify(String message) {
        if (message.equals("Task1 done.")) {
            task1 = null;
            button1.setText("Start Task 1");
            System.out.println("end task 1");
        }
        
        textArea.appendText(message + "\n");
    }
    
    @FXML
    public void startTask2(ActionEvent event) {
        if (task2 == null) {
            System.out.println("start task 2");
            button2.setText("End Task 2");
            task2 = new Task2(2147483647, 1000000);
            task2.setOnNotification((String message) -> {
                textArea.appendText(message + "\n");
                if (message.equals("Task2 done.")) {
                    handle(message);
                }
            });
            task2.start(); 
        } else {
            // This is for when the button is pressed again and a task is running
            button2.setText("Start Task 2");
            task2.end();
        }
    }

    public void handle(String message) {
        if (message.equals("Task2 done.")) {
            task2 = null;
            button2.setText("Start Task 2");
        }
        System.out.println("end task 2");
    }
    
    @FXML
    public void startTask3(ActionEvent event) {
        if (task3 == null) {
            System.out.println("start task 3");
            button3.setText("End Task 3");
            task3 = new Task3(2147483647, 1000000);
            // this uses a property change listener to get messages
            task3.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                if (evt.getNewValue().equals("Task3 done.")) { //listening for the event to be done.
                    task3 = null;
                    button3.setText("Start Task 3");
                    textArea.appendText((String)evt.getNewValue() + "\n");
                    System.out.println("end task 3");
                }else {
                    textArea.appendText((String)evt.getNewValue() + "\n");
                }
            });
            
            task3.start();
        } else {
            // This is for when the button is pressed again and a task is running
            button3.setText("Start Task 3");
            task3.end();
        }
    } 
}
