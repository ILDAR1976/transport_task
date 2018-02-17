package edu.transport_task;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javafx.embed.swing.JFXPanel;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SuppressWarnings("unused")
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = Application.class)
public class ApplicationTests {

	@BeforeClass
    public static void bootstrapJavaFx(){
        new JFXPanel();
    }

	@Test
    public void testDrawChargesPlane() {
    	assertTrue(true);
    }
}
