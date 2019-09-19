package gasAgencyManagement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HomeWindow extends Application {
	static boolean flag = false;
	Group layout1;
	static int count = 0,countCpy = 0;
	
	static int checkLogin = 0;
	
	public HomeWindow() {
		checkLogin = 0;
	}
	
	public HomeWindow(int x) {
		checkLogin = x;
	}
	
	@Override
	public void start(Stage stage) throws FileNotFoundException {
		// Creating an image
		//Image image = new Image(new FileInputStream("C:\\Users\\Mohit-PC\\Desktop\\logo.jpg"));
		Image image = new Image(new FileInputStream("asset/logo.jpg"));
		Image image1 = new Image(new FileInputStream("asset/header1.jpg"));
		Image image2 = new Image(new FileInputStream("asset/header5.jpg"));

		// Setting the image view
		ImageView imageView = new ImageView(image);
		ImageView imageView1 = new ImageView(image1);
		ImageView imageView2 = new ImageView(image2);

		// setting the fit height and width of the image view
		imageView.setFitHeight(120);
		imageView.setFitWidth(160);
		imageView1.setFitHeight(500);
		imageView1.setFitWidth(1400);
		imageView2.setFitHeight(500);
		imageView2.setFitWidth(1400);

		// Setting the preserve ratio of the image view
		imageView.setPreserveRatio(false);
		imageView1.setPreserveRatio(false);
		imageView2.setPreserveRatio(false);

		//Button loginB = new Button("Login");
		Button homeB = new Button("Home");
		Button adminB = new Button("Admin Zone");
		Button customerB = new Button("Customer Zone");
		Button changeImg = new Button("");
		changeImg.setStyle("-fx-border-color : BLACK");
		changeImg.setStyle("-fx-background-color : GOLD");
		changeImg.setShape(new Circle(50));
		changeImg.setPrefSize(20, 20);
		
		
		//loginB.setPrefSize(300, 120);
		homeB.setPrefSize(400, 120);
		adminB.setPrefSize(410, 120);
		customerB.setPrefSize(400, 120);
		
		homeB.setFont(Font.font("Georgia",24));
		homeB.setStyle("-fx-background-color : GOLD");
		//loginB.setFont(Font.font("Georgia",24));
		//loginB.setStyle("-fx-background-color : GOLD");
		adminB.setFont(Font.font("Georgia",24));
		adminB.setStyle("-fx-background-color : GOLD");
		customerB.setFont(Font.font("Georgia",24));
		customerB.setStyle("-fx-background-color : GOLD");
		
		
		HBox layout = new HBox();
		layout1 = new Group();
		HBox layout2 = new HBox();
		

		Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);

        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);
        
		layout.getChildren().addAll(imageView,homeB,adminB,customerB);
		System.out.println(count);
		if(count%2==0)
			layout1.getChildren().addAll(imageView1);
		else
			layout1.getChildren().addAll(imageView2);
		layout2.getChildren().addAll(region1,changeImg,region2);

		BorderPane mainLayout = new BorderPane();
		mainLayout.setTop(layout);
		mainLayout.setCenter(layout1);
		mainLayout.setBottom(layout2);
		
		changeImg.setOnAction(e->{
			if(flag) {
				layout1.getChildren().removeAll(imageView2);
				layout1.getChildren().addAll(imageView1);
				
			} else {
				layout1.getChildren().removeAll(imageView1);
				layout1.getChildren().addAll(imageView2);
			}
			flag = !flag;
		});
		
		adminB.setOnAction(e->{
			count++;
			if(count%2==0)
				flag=false;
			else
				flag=true;
			
			layout1.getChildren().removeAll(imageView1);
			layout1.getChildren().removeAll(imageView2);
			LoginWindow loginObj = new LoginWindow();
			OptionWindow optionObj = new OptionWindow();
			try {
				if(checkLogin == 0)
					loginObj.displayLoginWindow(stage);
				else if(checkLogin == 1)
					optionObj.displayOptionWindow(stage);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
		
		customerB.setOnAction(e->{
			count++;
			if(count%2==0)
				flag=false;
			else
				flag=true;
			
			layout1.getChildren().removeAll(imageView1);
			layout1.getChildren().removeAll(imageView2);
			
			OptionWindowForCustomers customerObj = new OptionWindowForCustomers(checkLogin); 
			try {
				customerObj.displayOptionWindowForCustomers(stage);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		});
		
		Scene s = new Scene(mainLayout, 1365,745);

		

		// Setting title to the Stage
		stage.setTitle("Project Mahanagar Gas");
		stage.setMaximized(true);
		stage.setResizable(false);

		// Adding scene to the stage
		stage.setScene(s);

		// Displaying the contents of the stage
		stage.show();
	}

	public static void main(String args[]) {
		launch(args);
	}
}
