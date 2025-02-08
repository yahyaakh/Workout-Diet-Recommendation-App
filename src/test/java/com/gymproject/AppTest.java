package com.gymproject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class AppTest {

    @Test
    public void testAppLaunch() {
        App app = new App(); 
        assertNotNull(app, "App should initialize properly.");
    }


  @Test
void testSaveUserData() throws IOException {
    App app = new App("testUser", "testPassword");
    App.userData = new String[]{"John", "25", "70", "170"};
    App.selections = new String[]{"male", "building muscle", "active"};

    app.saveUserData();

    // Read the last line from the file
    File file = new File("src/main/resources/users.txt");
    BufferedReader br = new BufferedReader(new FileReader(file));
    String lastLine = null, line;

    while ((line = br.readLine()) != null) {
        lastLine = line;
    }
    br.close();

    assertEquals("testUser,testPassword,John,25,70,170,male,building muscle,active", lastLine, "Data saved incorrectly.");
}

@Test
void testValidateRecommendations() {
    App app = new App();
    App.userData = new String[]{"John", "28", "85", "180"};
    App.selections = new String[]{"male", "losing weight", "active"};

    int caseNumber = app.cases2();
    assertEquals(9, caseNumber, "Recommendation case is incorrect.");
}



}