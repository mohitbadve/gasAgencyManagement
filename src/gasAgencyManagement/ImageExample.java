package gasAgencyManagement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.*;

import javafx.scene.shape.*;
import javafx.stage.Stage;

public class ImageExample extends Application {  
   @Override 
   public void start(Stage stage) throws FileNotFoundException {         
      //Creating an image 
     Image image = new Image(new FileInputStream("asset/logo.jpg"));  
      
      //Setting the image view 
      ImageView imageView = new ImageView(image); 
      
      //Setting the position of the image 
      imageView.setX(0); 
      imageView.setY(0); 
      
      //setting the fit height and width of the image view 
      imageView.setFitHeight(60); 
      imageView.setFitWidth(70); 
      
      //Setting the preserve ratio of the image view 
      imageView.setPreserveRatio(true); 
      
      Button button = new Button("Login");
      button.setLayoutX(130);
      button.setLayoutY(150);;
      //Creating a Group object  
      Group root = new Group();  
      
      //Creating a scene object 
 

	Scene s = new Scene(root, 300, 300, Color.valueOf("#00FF00"));

	root.getChildren().addAll(button,imageView);
      
      //Setting title to the Stage 
      stage.setTitle("Loading an image");  
      stage.setResizable(false);
  
      //Adding scene to the stage 
      stage.setScene(s);
      
      
      //Displaying the contents of the stage 
      stage.show(); 
   }  
   public static void main(String args[]) { 
      launch(args); 
   } 
}
