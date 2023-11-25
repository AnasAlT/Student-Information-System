/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Students_Information_System;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Asura
 */

/*
this class is self explanatory it just setters and getters for the table view
*/
public class Student {

    private IntegerProperty ID;
    private StringProperty FullName;
    private StringProperty DateOfBirth;
    private FloatProperty GPA;

    public Student() {
        this.ID = new SimpleIntegerProperty();
        this.FullName = new SimpleStringProperty();
        this.DateOfBirth = new SimpleStringProperty();
        this.GPA = new SimpleFloatProperty();
    }

    public int getID() {
        return ID.get();
    }

    public void setID(int id) {
        this.ID.set(id);
    }

    public IntegerProperty getStudentID() {
        return ID;
    }

    public String getFullName() {
        return FullName.get();
    }

    public void setFullName(String name) {
        this.FullName.set(name);
    }

    public StringProperty getStudentFullName() {
        return FullName;
    }

    public String getDateOfBirth() {
        return DateOfBirth.get();
    }

    public void setDateOfBirth(String date) {
        this.DateOfBirth.set(date);
    }

    public StringProperty getStudentDateOfBirth() {
        return DateOfBirth;
    }

    public float getGPA() {
        return GPA.get();
    }

    public void setGPA(float gpa) {
        this.GPA.set(gpa);
    }

    public FloatProperty getStudentGPA() {
        return GPA;
    }

}
