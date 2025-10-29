package tests;

// DO NOT ADD ANY JUNIT-RELATED IMPORTS TO THIS CLASS.  For your code to
// compile on the submit server you should use ONLY the features of JUnit
// that can be used with the imports below.
import org.junit.*;

import fazebook.Fazebook;

import static org.junit.Assert.*;

import java.util.Arrays;

public class StudentTests {
    
    @Test public void studentTest1() {
        Fazebook socialNetwork = TestData.exampleSocialNetwork2();

        assertTrue(TestData.compareColl(socialNetwork.getAllUsers(),
                                        Arrays.asList("Lion", "Meerkat",
                                                      "Numbat", "Otter",
                                                      "Penguin",
                                                      "Quokka", "Sheep",
                                                      "Walrus")));
    }
    
    @Test public void studentTest2() {
        Fazebook socialNetwork = TestData.exampleSocialNetwork2();
        
        socialNetwork.addFriends("Lion", "Zebra");

        assertTrue(TestData.compareColl(socialNetwork.getAllUsers(),
                                        Arrays.asList("Lion", "Meerkat",
                                                      "Numbat", "Otter",
                                                      "Penguin",
                                                      "Quokka", "Sheep",
                                                      "Walrus", "Zebra")));
    }
    
    @Test public void studentTest3() {
        Fazebook socialNetwork = TestData.exampleSocialNetwork2();
        
        assertTrue(TestData.compareColl(socialNetwork.getFriends("Sheep"),
                                        Arrays.asList("Lion", "Meerkat",
                                                      "Numbat")));
    }
    
    @Test public void studentTest4() {
        Fazebook socialNetwork = TestData.exampleSocialNetwork2();
        
        socialNetwork.addFriends("Sheep", "Zebra");
        
        assertTrue(TestData.compareColl(socialNetwork.getFriends("Sheep"),
                                        Arrays.asList("Lion", "Meerkat",
                                                      "Numbat", "Zebra")));
    }
    
    @Test public void studentTest5() {
        Fazebook socialNetwork = TestData.exampleSocialNetwork2();
        
        socialNetwork.addFriends("Sheep", "Zebra");
        socialNetwork.unfriend("Sheep", "Zebra");
        
        assertTrue(TestData.compareColl(socialNetwork.getFriends("Sheep"),
                                        Arrays.asList("Lion", "Meerkat",
                                                      "Numbat")));
    }
    
    @Test public void studentTest6() {
        Fazebook socialNetwork = TestData.exampleSocialNetwork2();
        
        socialNetwork.addFriends("Random1", "Random2");
        socialNetwork.addFriends("Random2", "Random3");
        
        assertTrue(TestData.compareColl(
                socialNetwork.peopleYouMayWannaKnow("Random1"),
                                        Arrays.asList("Random3")));
    }
    
    @Test public void studentTest7() {
        Fazebook socialNetwork = TestData.exampleSocialNetwork2();
        
        socialNetwork.addFriends("Random1", "Random2");
        socialNetwork.addFriends("Random2", "Random3");
        
        assertTrue(TestData.compareColl(
                socialNetwork.peopleYouMayWannaKnow("Random1"),
                                        Arrays.asList("Random3")));
    }
    
    @Test public void studentTest8() {
        Fazebook socialNetwork = new Fazebook();

        socialNetwork.readSocialNetworkData(Arrays.asList("random-1"));

        assertTrue(TestData.compareColl(socialNetwork.getAllUsers(),
                                        Arrays.asList("me")));
    }
    
    @Test public void studentTest9() {
        Fazebook socialNetwork = new Fazebook();

        socialNetwork.readSocialNetworkData(Arrays.asList("random-1"));

        assertTrue(TestData.compareColl(socialNetwork.getAllUsers(),
                                        Arrays.asList("me")));
    }

}
