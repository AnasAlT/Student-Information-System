/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Students_Information_System;

/*
1- import sql Connection
2- import sql Driver Manager
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Asura
 */
public class Database_Connectivity {

    //creating an object of type connection
    private Connection con;

    /*
    Database_Connectivity method will establish a connection to the DataBase
    if the given DataBase doesn't exists then it will print an error message 
     */
    public Connection Load_Database() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String dbURL = "jdbc:mysql://localhost:3306/StudentsDB_AlTowairqi";
            String username = "project";
            String pass = "Anas_010203";
            con = DriverManager.getConnection(dbURL, username, pass);

        } catch (Exception e) {
            System.out.println("something went wrong connecting to the DataBase");
        }
        return con;

    }
    
    /*
    inster method will load the driver, then writing an sql command into the sql String
    and making the values not given yet, after that it will it will establish the connection
    using the prepStat and inserting each value by it's give number and the given global variable
    it will then excuate the update then close the prepStat to have more space in the memory, and close the connection to the DataBase
     */
    public void Insert_Student(String FullName, String DateOfBirth, float GPA) throws SQLException {
        //loading the configuration from the Database_Connectivity class and storing it
        con = Load_Database();
        String sql = "insert into StudentsTBL_Anas(FullName, DateOfBirth, GPA) values (?, ?, ?)";
        PreparedStatement prepStat = con.prepareStatement(sql);
        prepStat.setString(1, FullName);
        prepStat.setString(2, DateOfBirth);
        prepStat.setFloat(3, GPA);

        prepStat.executeUpdate();

        prepStat.close();
        con.close();
    }
    
    //SuppressWarnings used to suppress warnings
    @SuppressWarnings("unchecked")
    /*
    Read_Records method will load the driver, then writing an sql Query into the sql String
    after that it will it will establish the connection using the prepStat then
    it will then excuate the Query and store the results in a ResultSet
    then it will store the result set into an Observable List then close the prepStat and ResultSet
    to have more space in the memory, and close the connection to the DataBase
    then return that Observable List
     */
    public ObservableList<Student> getAllRecords() throws SQLException {
        con = Load_Database();
        String sql = "select * from StudentsTBL_Anas";

        PreparedStatement prepStat = con.prepareStatement(sql);
        ResultSet rs = prepStat.executeQuery();
        ObservableList<Student> getInfo = getInfoObjects(rs);
        prepStat.close();
        con.close();
        return getInfo;

    }

    //SuppressWarnings used to suppress warnings
    @SuppressWarnings("unchecked")
    /*
    Read_Records method will load the driver, then getting the name from the method then
    writing an sql Query into the sql String that searches for that name
    after that it will it will establish the connection using the prepStat then using the prepStat
    setString to search for the first value in the Query (?) and using % so that the FullName doesn't haveto be exact
    it will then excuate the Query and store the results in a ResultSet
    then it will store the result set into an Observable List then close the prepStat and ResultSet
    to have more space in the memory, and close the connection to the DataBase
    then return that Observable List
     */
    public ObservableList<Student> find_Student(String name) throws SQLException {
        //loading the configuration from the Database_Connectivity class and storing it
        con = Load_Database();
        String sql = "SELECT * FROM StudentsTBL_Anas WHERE FullName LIKE ?";

        PreparedStatement prepStat = con.prepareStatement(sql);
        prepStat.setString(1, "%" + name + "%");
        ResultSet rs = prepStat.executeQuery();

        ObservableList<Student> getInfo = getInfoObjects(rs);

        prepStat.close();
        con.close();
        return getInfo;

    }
    
    //this method for setting the student information using class Student
    private ObservableList getInfoObjects(ResultSet rs) throws SQLException {

        ObservableList<Student> infolist = FXCollections.observableArrayList();
        while (rs.next()) {
            Student info = new Student();
            info.setID(rs.getInt("ID"));
            info.setFullName(rs.getString("FullName"));
            info.setDateOfBirth(rs.getString("DateOfBirth"));
            info.setGPA(rs.getFloat("GPA"));
            infolist.add(info);

        }
        return infolist;
    }
}
