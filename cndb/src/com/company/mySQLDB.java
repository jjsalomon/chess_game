package com.company;

import java.sql.*;

/**
 * Created by Francis on 3/11/2017.
 */

/*REFERENCES:
* https://www.tutorialspoint.com/jdbc/jdbc-sample-code.htm
* http://zetcode.com/db/mysqljava/
* http://www.vogella.com/tutorials/MySQLJava/article.html#jdbcdriver
*/

public class mySQLDB {

    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/test";

    //  Database credentials
    private static final String USER = "root";
    private static final String PASS = "";

    //java sql variables
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet res;

    //set up JDBC_driver
    public mySQLDB() {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Method to createTable
    public void createTable() {
        try {
            //create connection to database
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //create preparedStatement
            pstmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS account(username VARCHAR(20) NOT NULL PRIMARY KEY, password VARCHAR(20) NOT NULL)");
            pstmt.executeUpdate();
            pstmt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS profile(userID int(11) NOT NULL PRIMARY KEY AUTO_INCREMENT, rank int(11) NOT NULL, win int(11) NOT NULL, loss int(11) NOT NULL, coins int(11) NOT NULL, username varchar(20) NOT NULL, rewardID varchar(20) NOT NULL, status varchar(10),image BLOB)");
            pstmt.executeUpdate();
            pstmt = conn.prepareStatement("ALTER TABLE profile ADD CONSTRAINT profile_ibfk_1 FOREIGN KEY (username) REFERENCES account (username)");
            pstmt.executeUpdate();
        } catch (Exception e) {

        } finally {
            closeRsc();
        }
    }

    //Method for logging in
    public boolean Login(String username, String password) {
        try {
            //create connection to database
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //create preparedStatement
            pstmt = conn.prepareStatement("SELECT * FROM account WHERE username = ? AND password = ?");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            res = pstmt.executeQuery();
            //validation of account
            if (res.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        } finally {
            closeRsc();
        }

    }

    //Method to view selected user profile
    public boolean viewProfile(String username) {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            pstmt = conn.prepareStatement("SELECT * FROM profile WHERE username = ?");
            pstmt.setString(1, username);
            res = pstmt.executeQuery();

            //Extract data from result set
            //loop through database (cursor)
            if (res.next()) {
                System.out.println(username + " profile");
                //Retrieve by column name
                int ID = res.getInt("userID");
                int rank = res.getInt("rank");
                int win = res.getInt("win");
                int loss = res.getInt("loss");
                int coins = res.getInt("coins");
                String rewardID = res.getString("rewardID");
                String status = res.getString("status");
                String img = res.getString("image");

                //Display values
                System.out.println("UserID: " + ID);
                System.out.println("Username: " + username);
                System.out.println("Rank: " + rank);
                System.out.println("Win: " + win);
                System.out.println("Loss: " + loss);
                System.out.println("Coins: " + coins);
                System.out.println("Skin: " + rewardID);
                System.out.println("Status: " + status);
                System.out.println("Skin: " + img);

                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        } finally {
            closeRsc();
        }
    }

    //Method to create account and profile
    public boolean insertData(String username, String password) { //need to add parameters for getting input from user
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //create account
            pstmt = conn.prepareStatement("INSERT INTO account VALUES(?,?)");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            int row = pstmt.executeUpdate();
            //validate if registration account succeed return greater than 0
            if (row > 0) {
                //create profile
                int rank = 0;
                int win = 0;
                int loss = 0;
                int coins = 0;
                String rewardID = "";
                String status = "online";
                String img = "";
                pstmt = conn.prepareStatement("INSERT INTO profile VALUES(default,?,?,?,?,?,?,?,?)");
                pstmt.setInt(1, rank);
                pstmt.setInt(2, win);
                pstmt.setInt(3, loss);
                pstmt.setInt(4, coins);
                pstmt.setString(5, username);
                pstmt.setString(6, rewardID);
                pstmt.setString(7, status);
                pstmt.setString(8, img);
                row = pstmt.executeUpdate();
                //validate profile creation
                if (row > 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        } finally {
            closeRsc();
        }
    }

    //Method to delete account and profile
    public boolean deleteData(String username) {
        try {//!! if account is deleted  profile should be deleted too!
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //deletes profile
            pstmt = conn.prepareStatement("DELETE FROM profile WHERE username = ?");
            pstmt.setString(1, username);
            int row = pstmt.executeUpdate();
            if (row > 0) {
                //deletes account
                pstmt = conn.prepareStatement("DELETE FROM account WHERE username= ?");
                pstmt.setString(1, username);
                row = pstmt.executeUpdate();
                if (row > 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (SQLException e) {
            return false;
        } finally {
            closeRsc();
        }
    }

    //Method to update profile
    public boolean updateData() { //
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            //!! change to profile update where username = username
            pstmt = conn.prepareStatement("UPDATE account SET username = ?, password = ?, email = ? WHERE username = ?");
            pstmt.setString(1, "changes");
            pstmt.setString(2, "password");
            pstmt.setString(3, "kkdso@gmail.com");
            pstmt.setString(4, "kkdso");
            int row = pstmt.executeUpdate();
            if (row > 0) {
                return true;
            } else {
                return false;
            }
            //System.out.println("Update Complete");
        } catch (SQLException e) {
            return false;
        } finally {
            closeRsc();
        }
    }

    //Close database resources
    private void closeRsc() {
        try {
            if (res != null) {
                res.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
}