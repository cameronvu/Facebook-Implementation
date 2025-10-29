package fazebook;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

/*
Name: Cameron Vu (UID: 121227508)
Honor Pledge: I pledge on my honor that I have not given or received any 
              unauthorized assistance on this assignment.
*/

/**
 * The Reader class supplements the Fazebook class by allowing the use of
 * Threads and concurrency. This class contains two class fields by the names
 * fazebook and filename, which represent the current Fazebook object for the
 * threads to operate on and the name of the file, respectively. The main 
 * functionality of this class is to override Java's run() method for threads
 * and ensure that synchronization and concurrency is correctly implemented
 * in order to allow multiple files in the Fazebook class to be read and acted
 * upon simultaneously with no risk of incorrectly overridden data or data 
 * races. 
 */
public class Reader extends Thread {

    private Fazebook fazebook; 
    private String filename;

    // Constructor to initialize the Fazebook instance and filename of the
    // current file being read 
    public Reader(Fazebook fazebook, String filename) {
        this.fazebook = fazebook;
        this.filename = filename;
    }

    /*
     * Overrides Java's run() method and determines the behavior of the current
     * thread by reading the first word in the file; if the word is adduser
     * or addfriends, then a user is added or a friendship is created between
     * the proceeding names in the files, respectively. This method supports
     * concurrency and handles synchronization by locking the methods that
     * call to addUser() and addFriends() to ensure that there is no data race
     * between working threads. 
     */
    @Override public void run() {
        try {
            File file = new File(filename);
            Scanner myReader = new Scanner(file);
            // Traverse the Collection of filenames and read the first
            // word in the file to determine the next action
            while (myReader.hasNext()) {
                String data = myReader.next();
                if (data.equals("adduser")) {
                    String user = myReader.next();
                    // Synchronize access to the graph object shared between
                    // the working threads
                    synchronized (fazebook.users) { 
                        fazebook.addUser(user);
                    }
                } else if (data.equals("addfriends")) {
                    String user1 = myReader.next();
                    String user2 = myReader.next();
                    // Synchronize access to the graph object shared between
                    // the working threads
                    synchronized (fazebook.users) { 
                        fazebook.addFriends(user1, user2);
                    }
                }
            }
            myReader.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

}

