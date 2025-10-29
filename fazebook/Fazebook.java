package fazebook;

import java.util.Collection;
import java.util.HashSet;

/*
Name: Cameron Vu (UID: 121227508)
Honor Pledge: I pledge on my honor that I have not given or received any 
              unauthorized assistance on this assignment.
*/

/**
 * The Fazebook class simulates a social media platform in which users
 * are able to create accounts and maintain friendships. The class stores
 * each of the current users as vertices and their friendships as edges in
 * the class field named users, which is a EWDGraph object. The class calls
 * to many methods written in the EWDGraph class, which allow the current
 * Fazebook object to create users and initialize or terminate friendships
 * between two users. Lastly, the Thread class is extended in order to
 * utilize threads and concurrency in the last method, where files are read
 * and appropriate action is taken based on the contents of the file.
 */
public class Fazebook extends Thread {

    EWDGraph<String> users = new EWDGraph<>(new StringComparator());
    
    /**
     * Adds a new user by the name of the parameter userName to the current
     * Fazebook object through modifications to the EWDGraph field
     * that stores Fazebook users and friendships. The method returns
     * false if the user exists within the current object or if userName
     * is not a valid user name; true is returned otherwise.
     * 
     * @param userName  A String object representing the name of the user
     *                  to be added to the current Fazebook object
     * @return  true if the user is successfully added to the current
     *          Fazebook object and false otherwise.
     */
    public boolean addUser(String userName) {
        if (userName.isEmpty() || userName == null)
            return false;
        return users.newEWDGraphVertex(userName);
    }

    /**
     * Gathers and returns the users that are stored in the current Fazebook
     * object in the form of a Java Collection, which will be empty if there
     * are no users stored within the current object.
     * 
     * @return A Java Collection storing the Strings of the current users.
     */
    public Collection<String> getAllUsers() {
        return users.getEWDGraphVertices();
    }

    /**
     * Creates a friendship between the two specified users represented by
     * the parameters userName1 and userName2, respectively. Friendships are
     * added for both users to the current Fazebook object via alterations
     * to the users field. If either user does not exist, the user is
     * added to the Fazebook object. 
     * 
     * @param userName1     A String object representing the first user in
     *                      the friendship
     * @param userName2     A String object representing the second user in
     *                      the friendship
     * @return  true if the friendship was successfully established; false 
     *          otherwise.
     */
    public boolean addFriends(String userName1, String userName2) {
        boolean friends = true;
        if (userName1.isEmpty() || userName2.isEmpty() || userName1 == null
             || userName2 == null) {
            friends = false;
        } else {
            users.newEWDGraphEdge(userName1, userName2, 1);
            users.newEWDGraphEdge(userName2, userName1, 1);
        }
        return friends;
    }

    /**
     * Retrieves the friends that are associated with the parameter userName
     * within the current Fazebook object. If the user does not exist, then 
     * an empty Collection object is returned; otherwise, a Collection
     * containing the friends is returned.
     * 
     * @param userName  A String object representing the name of the desired
     *                  user in the current Fazebook object
     * @return  A Java Collection object containing the users that the 
     *          parameter userName is friends with.
     */
    public Collection<String> getFriends(String userName) {
        if (userName == null)
            return null;
        else 
            return users.getNeighborsOfVertex(userName);
    }

    /**
     * Terminates the friendship between two specified users through
     * parameters userName1 and userName2, respectively. The method
     * has no effect and false is returned if either user does not exist
     * or if the users are not friends; otherwise, the friendship is terminated
     * within the current Fazebook object and true is returned.
     * 
     * @param userName1     A String object representing the first user in
     *                      the friendship
     * @param userName2     A String object representing the second user in
     *                      the friendship
     * @return  true if the friendship is successfully terminated and false
     *          otherwise.
     */
    public boolean unfriend(String userName1, String userName2) {
        boolean unfriend = true;
        if (userName1.isEmpty() || userName2.isEmpty() || userName1 == null
                || userName2 == null || !users.isEWDGraphVertex(userName1)
                || !users.isEWDGraphVertex(userName2)
                || !getFriends(userName1).contains(userName2))
            unfriend = false;
        else
            users.removeEWDGraphEdge(userName1, userName2);
        return unfriend;
    }

    /**
     * Retrieves and returns friend suggestions for the parameter userName
     * in the form of a Java Collection that contains the friends of 
     * each friend of the specified user in the current Fazebook object. 
     * 
     * @param userName  A String representing the user in which the friend
     *                  suggestions should be calculated for
     * @return  A Java Collection containing the suggested friends for the
     *          specified user.
     */
    public Collection<String> peopleYouMayWannaKnow(String userName) {
        if (userName == null || userName.isEmpty()) 
           return null;
        
        Collection<String> friends = getFriends(userName);
        Collection<String> suggested = new HashSet<>();
        // iterate through each friend and add their friends
        for (String friend : friends) {
            if (!friend.equals(userName)) {
                suggested.addAll(users.getNeighborsOfVertex(friend));
            }
        }
        // the userName will always be added, so remove it at the end and 
        // return the result
        suggested.remove(userName);
        return suggested;
    }

    /**
     * Utilizes concurrency and threads to add users and create friendships
     * within the current Fazebook object. The method reads the files 
     * that are specified by the parameter filenames and either adds the
     * specified user or creates and initializes a friendship between two
     * specified users by reading the first word of the file, which would
     * be either addusers or addfriends, respectively. This method utilizes
     * Java's Threads and also utilizes the overridden run() method in the 
     * Reader class, which determines the behavior of the current Fazebook
     * object depending on the contents of the file.
     * 
     * @param filenames   A Java Collection of the filenames that are desired
     *                    for processing
     * @return  true if the data was successfully read and acted upon and
     *          false otherwise.
     */
    public boolean readSocialNetworkData(Collection<String> filenames) {
        boolean read = true;
        if (filenames == null) {
            read = false;
        } else {
            // Collection that holds all of the threads
            Collection<Reader> threads = new HashSet<>();

            // Creates and starts a thread for each file from parameter
            // filenames
            for (String filename : filenames) {
                // Ensure that the current Fazebook object is being passed into
                // the constructor for the Reader class of the new thread
                Reader thread = new Reader(this, filename);

                // Add the thread to the threads Collection to be used later and
                // start the thread's run() method in the Reader class
                threads.add(thread);
                thread.start();
            }

            // Ensures that all threads are finished running
            for (Reader thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return read;
    }
}


   

