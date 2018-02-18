package edu.transport_task;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.testfx.framework.junit.ApplicationTest;

import edu.transport_task.ui.MainController;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.annotation.DirtiesContext;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = Application.class)
public class ApplicationTests extends ApplicationTest implements ApplicationContextAware {
	
	@Value("${ui.title} ...")
    private String windowTitle;

    @Qualifier("mainView")
    @Autowired
    private ConfigurationControllers.View view;
 	
 
	
	@BeforeClass
    public static void bootstrapJavaFx(){
		new JFXPanel();
		
    }
 
	@Override
    public void start(Stage stage) throws Exception {
        stage.setTitle(windowTitle);
        stage.setScene(new Scene(view.getView()));
        stage.setResizable(true);
		stage.getIcons().add(new Image(getClass().getClassLoader().getResource("icon.png").toString()));
        stage.centerOnScreen();
        stage.show();
    }

   
	@Test
    public void testDrawChargesPlane() throws Exception {
		
		
		clickOn(((Button)((HBox)((VBox)view.getView().getChildrenUnmodifiable().get(1)).getChildren().get(1)).getChildren().get(0)));
		    	assertTrue(true);
    }

	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		
	}
}
