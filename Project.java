/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Students_Information_System;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Project extends Application {

    //creating an object for stage close.
    private Stage primaryStage;

    //creating an object from Database_Connectivity class.
    private final Database_Connectivity LD = new Database_Connectivity();

    //creating an object for TabPane.
    private final TabPane tab = new TabPane();
    //creating an object for Tab that has all tab1 content.
    private final Tab t1 = new Tab("Insert Student");
    //creating an object for Tab that has all tab2 content.
    private final Tab t2 = new Tab("Read/Search Studnet");

    //creating Object FullName to store from the TextField
    private String FullName;
    //creating Object DateOfBirth to store from the DatePicker
    private String DateOfBirth;
    //creating Object GPA to store from the GPA Slider
    private float GPA;

    //creating Object getInfo to store the returned info from the DataBase
    private ObservableList<Student> getInfo;
    //creating Object table
    TableView<Student> table;

    public void start(Stage primaryStage) throws ClassNotFoundException, SQLException {

        //for stage close used on Exit buttons.
        this.primaryStage = primaryStage;
        /*
        set the tabs content from the returned methods
        and making the "X" mark that closes the tabs not shown 
         */
        t1.setContent(InsertPane());
        t2.setContent(ReadPane());
        t1.setClosable(false);
        t2.setClosable(false);

        //add the tabs to the TabPane object
        tab.getTabs().addAll(t1, t2);

        //giving the scene the TabPane object
        Scene scene = new Scene(tab);

        /*
        set the name for the stage
        giving the Stage the Scene object
        then showing the Stage
         */
        primaryStage.setTitle("Students Information System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /*
    This part will Handle The GUI content and Validations for Insertion Tab
     */
    public GridPane InsertPane() {
        //creating GridPane object
        GridPane pane1 = new GridPane();
        //Tab 1 Contents settings
        //Setting size for the pane 
        pane1.setMinSize(400, 350);
        //Setting the padding
        pane1.setPadding(new Insets(10));

        //Setting the vertical and horizontal gaps between the columns 
        pane1.setVgap(10);
        pane1.setHgap(20);

        //Creating Text object and adding it to the GridPane
        Text myInfo = new Text("Name: Anas AlTowairqi"
                + "\nID: 441010638"
                + "\nGroup: 1"
                + "\nEmail: s441010638@st.uqu.edu.sa");
        pane1.add(myInfo, 0, 0);

        //Text for FullName and it's settings
        Text NameL = new Text("Full Name:");
        //setting the fond size and type
        NameL.setFont(Font.font("Arial", 18));
        //moving the Text object yaxis
        NameL.setTranslateY(1);

        /*TextField for FullName  and it's settings
          then adding it to the GridPane with NameL
         */
        TextField NameTF = new TextField();
        //so it's not automatically selected upon running
        NameTF.setFocusTraversable(false);
        //set a messeage for the purpose of box inside it
        NameTF.setPromptText("Student Full Name");
        pane1.add(NameL, 0, 1);
        pane1.add(NameTF, 1, 1);

        //Text for DateOfBirth and it's settings
        Text DateOfBirthL = new Text("Date of birth:");
        //setting the fond size and type
        DateOfBirthL.setFont(Font.font("Arial", 18));
        //moving the Text object yaxis
        DateOfBirthL.setTranslateY(2);

        /*DatePicker for DateOfBirth and it's settings
          then adding it to the GridPane with DateOfBirthL
         */
        DatePicker datePicker = new DatePicker();
        datePicker.setEditable(false);
        //set a messeage for the purpose of box inside it
        datePicker.setPromptText("Student Date of birth");
        //so it's not automatically selected upon running
        datePicker.setFocusTraversable(false);
        //this part will handle so the user can't select a date in the future
        datePicker.setDayCellFactory(param -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) > 0);
            }
        });

        pane1.add(DateOfBirthL, 0, 2);
        pane1.add(datePicker, 1, 2);

        //Text for GPA and it's settings
        Text GPAL = new Text("GPA:");
        //setting the fond size and type
        GPAL.setFont(Font.font("Arial", 18));

        /*Slider for GPA and it's settings
          then adding it to the GridPane with GPAL
         */
        //the values inside are Min and Max and default value
        Slider s = new Slider(0.0, 4.0, 0.0);
        //so it's not automatically selected upon running
        s.setFocusTraversable(false);
        //Shows the each major Tick for the Slider
        s.setShowTickMarks(true);
        //set the Major Ticks by One for the Slider
        s.setMajorTickUnit(1);
        //set the Major Ticks by Zero for the Slider so it's not shown
        s.setMinorTickCount(0);
        //moving the slider object yaxis
        s.setTranslateY(5);
        //Text for GPA value
        Text GPAvalue = new Text("0.0");

        //this part will handle so the GPA value will be float with 1 decimal position precision
        s.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            Double onePoint = newValue.doubleValue();
            onePoint = Math.round(onePoint * 10) / 10.0d;
            //so the Text value will change if the user moves the slider
            GPAvalue.setText(Double.toString(onePoint));
            //stores the value in the global GPA
            this.GPA = onePoint.floatValue();
        });
        pane1.add(GPAL, 0, 3);
        pane1.add(s, 1, 3);
        pane1.add(GPAvalue, 2, 3);

        //Label for status and it's settings
        Label stat = new Label("Status:");
        //setting the fond size and type
        stat.setFont(Font.font("Arial", 13));
        //moving the Label object yaxis
        stat.setTranslateY(10);
        //adding it to the GridPane
        pane1.add(stat, 0, 4);
        //expand the messeage place
        GridPane.setColumnSpan(stat, 2);

        //if the user leaves the t1 the status messeage resets
        t1.setOnSelectionChanged((Event event) -> {
            stat.setText("Status:");
            stat.setTextFill(Color.BLACK);
        });

        //save button and it's settings
        Button b1 = new Button("Save");
        //moving the Button object Xaxis
        b1.setTranslateX(150);
        //moving the Button object Yaxis
        b1.setTranslateY(20);
        //so it's not automatically selected upon running
        b1.setFocusTraversable(false);
        //adding it to the GridPane
        pane1.add(b1, 0, 5);

        //if the user enters data in the Name field the Status resets
        NameTF.textProperty().addListener(c -> {
            if (!NameTF.getText().isEmpty()) {
                stat.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
                stat.setTextFill(Color.BLACK);
                stat.setText("Status");
            }
        });

        //if the user enters data in the DateOfBirth field the Status resets
        datePicker.valueProperty().addListener(c -> {
            if (datePicker.getValue() != null) {
                stat.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
                stat.setTextFill(Color.BLACK);
                stat.setText("Status");
            }
        });

        //Save button actions
        b1.setOnAction(e -> {
            //Retrieving data for FullName and DateOfBirth
            //FullName
            this.FullName = NameTF.getText();
            //DateOfBirth
            LocalDate date = datePicker.getValue();
            if (date != null) {
                this.DateOfBirth = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }

            //validating FullName length
            if (this.FullName.length() > 40) {
                stat.setTextFill(Color.RED);
                stat.setFont(Font.font("Arial", FontWeight.BOLD, 13));
                stat.setText("Name must be less than 40 characters");
                return;
            }
            //validating FullName letters
            if (!this.FullName.matches("[a-zA-Z\\s]*")) {
                stat.setTextFill(Color.RED);
                stat.setFont(Font.font("Arial", FontWeight.BOLD, 13));
                stat.setText("Name not accpeted must be only letters");
                return;
            }

            //validating FullName and Date Of Birth if empty
            if (this.FullName.isEmpty()) {
                stat.setTextFill(Color.RED);
                stat.setText("Name must not be empty");
                stat.setFont(Font.font("Arial", FontWeight.BOLD, 13));
                return;

            } else if (this.DateOfBirth == null) {
                stat.setTextFill(Color.RED);
                stat.setFont(Font.font("Arial", FontWeight.BOLD, 13));
                stat.setText("Date of Birth must not be empty");
                return;
            } else {
                stat.setFont(Font.font("Arial", FontWeight.NORMAL, 13));
                stat.setTextFill(Color.BLACK);
                stat.setText("Status");
            }
            //inserting to the Database
            try {
                LD.Insert_Student(FullName, DateOfBirth, GPA);
                stat.setTextFill(Color.GREEN);
                stat.setText("Student was added successfully");
                NameTF.setText("");
                datePicker.getEditor().clear();
                datePicker.setValue(null);
                s.setValue(0);
                this.FullName = null;
                this.DateOfBirth = null;
                this.GPA = 0;
            } catch (SQLException ex) {
                stat.setTextFill(Color.RED);
                stat.setFont(Font.font("Arial", FontWeight.BOLD, 13));
                stat.setText("Student was not added successfully");
            }

        }
        );

        //exit button and it's settings
        Button b2 = new Button("Exit");
        //moving the Button object Xaxis
        b2.setTranslateX(200);
        //moving the Button object Yaxis
        b2.setTranslateY(20);
        //so it's not automatically selected upon running
        b2.setFocusTraversable(false);

        //if exit button is pressed then close the stage
        b2.setOnAction(e -> {
            this.primaryStage.close();
        });
        //adding it to the GridPane
        pane1.add(b2, 0, 5);

        return pane1;
    }

    /*
    This part will Handle The GUI content and Validations for Read/Search Tab
     */
    public GridPane ReadPane() throws ClassNotFoundException, SQLException {
        GridPane pane2 = new GridPane();
        //Tab 2 Contents
        //Setting size for the pane 
        pane2.setMinSize(400, 350);
        //Setting the padding
        pane2.setPadding(new Insets(10, 10, 10, 10));

        //Setting the vertical and horizontal gaps between the columns 
        pane2.setVgap(10);
        pane2.setHgap(20);

        //Creating Text object and adding it to the GridPane
        Text myInfo = new Text("Name: Anas AlTowairqi"
                + "\nID: 441010638"
                + "\nGroup: 1"
                + "\nEmail: s441010638@st.uqu.edu.sa");
        pane2.add(myInfo, 0, 0);

        //Text for Read/Search and it's settings
        Text RSL = new Text("Read/Search Student");
        //setting the font size and type
        RSL.setFont(Font.font("Arial", 18));

        /*TextField for Read/Search  and it's settings
          then adding it to the GridPane with RSL
         */
        TextField SR = new TextField();
        //so it's not automatically selected upon running
        SR.setFocusTraversable(false);
        //set a messeage for the purpose of box inside it
        SR.setPromptText("Empty for all Results");
        //sets the height of the box
        SR.setPrefHeight(25);
        //sets the Width of the box
        SR.setPrefWidth(162);
        //adding to the GridPane
        pane2.add(RSL, 0, 1);
        pane2.add(SR, 1, 1);

        /*Label for the Table and it's settings
          then adding ShowInfo() to the GridPane
         */
        Label TLabel = new Label("Error fetching Data.");
        //setting the fond size and type
        TLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        //setting the font color
        TLabel.setTextFill(Color.RED);
        pane2.add(ShowInfo(), 0, 2, 3, 1);

        //Search button and it's settings
        Button b1 = new Button("Search");
        //moving the Button object Xaxis
        b1.setTranslateX(110);
        //moving the Button object Yaxis
        b1.setTranslateY(0);
        //so it's not automatically selected upon running
        b1.setFocusTraversable(false);
        //adding it to the GridPane
        pane2.add(b1, 0, 3);

        //if tab 2 is selected then show table content
        t2.setOnSelectionChanged((Event event) -> {
            if (SR.getText().isEmpty()) {
                try {
                    getInfo = LD.getAllRecords();
                    TLabel.setTextFill(Color.GREEN);
                    TLabel.setFont(Font.font("Arial", FontWeight.BOLD, 10));
                    TLabel.setText("All Records Found");
                    pane2.getChildren().remove(TLabel);
                    pane2.add(TLabel, 0, 3);
                    //removes Row 2 content 
                    pane2.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 2);
                    pane2.add(ShowInfo(), 0, 2, 3, 1);
                } catch (SQLException ex) {
                    if (table == null) {
                        table.getItems().clear();
                        table.setPlaceholder(TLabel);
                    }

                }
            }
        });

        //Search Button Actions
        b1.setOnAction(e -> {
            //if the buttons is empty then show all results
            if (SR.getText().isEmpty()) {
                try {
                    getInfo = LD.getAllRecords();
                    pane2.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 2);
                    TLabel.setFont(Font.font("Arial", FontWeight.BOLD, 10));
                    TLabel.setTextFill(Color.GREEN);
                    TLabel.setText("All Records Found");
                    pane2.getChildren().remove(TLabel);
                    pane2.add(TLabel, 0, 3);
                    pane2.add(ShowInfo(), 0, 2, 3, 1);
                } catch (SQLException ex) {
                    if (table == null) {
                        table.getItems().clear();
                        table.setPlaceholder(TLabel);
                    }

                }
                //if TexField is not empty then excuate search
            } else {
                try {
                    getInfo = LD.find_Student(SR.getText());
                    pane2.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 2);
                    TLabel.setFont(Font.font("Arial", FontWeight.BOLD, 10));
                    TLabel.setTextFill(Color.GREEN);
                    TLabel.setText("Name: " + SR.getText() + " Found");
                    pane2.getChildren().remove(TLabel);
                    pane2.add(TLabel, 0, 3);
                    pane2.add(ShowInfo(), 0, 2, 3, 1);
                    if (ShowInfo().getItems().isEmpty()) {
                        pane2.getChildren().remove(TLabel);
                    }

                } catch (SQLException ex) {
                    table.getItems().clear();
                    table.setPlaceholder(TLabel);
                }

                t2.setOnSelectionChanged((Event event) -> {
                    try {
                        getInfo = LD.find_Student(SR.getText());
                        TLabel.setFont(Font.font("Arial", FontWeight.BOLD, 10));
                        TLabel.setTextFill(Color.GREEN);
                        TLabel.setText("Name: " + SR.getText() + " Found");
                        if(SR.getText().isEmpty()){
                            TLabel.setText("All Records Found");
                        }
                        pane2.getChildren().remove(TLabel);
                        pane2.add(TLabel, 0, 3);
                        pane2.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 2);
                        pane2.add(ShowInfo(), 0, 2, 3, 1);
                        if (ShowInfo().getItems().isEmpty()) {
                            pane2.getChildren().remove(TLabel);
                        }
                    } catch (SQLException ex) {
                        if (table == null) {
                            table.getItems().clear();
                            table.setPlaceholder(TLabel);
                        }

                    }
                });
            }

        });
        //Refresh button and it's settings
        Button b2 = new Button("Refresh");
        //moving the Button object Xaxis
        b2.setTranslateX(165);
        //moving the Button object Yaxis
        b2.setTranslateY(0);
        //so it's not automatically selected upon running
        b2.setFocusTraversable(false);
        //adding it to the GridPane
        pane2.add(b2, 0, 3);

        //Refresh Button Actions
        b2.setOnAction(e -> {
            //if the TextField is empty
            if (SR.getText().isEmpty()) {
                try {
                    getInfo = LD.getAllRecords();
                    TLabel.setFont(Font.font("Arial", FontWeight.BOLD, 10));
                    TLabel.setTextFill(Color.GREEN);
                    TLabel.setText("All Records Found");
                    pane2.getChildren().remove(TLabel);
                    pane2.add(TLabel, 0, 3);
                    pane2.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 2);
                    pane2.add(ShowInfo(), 0, 2, 3, 1);

                } catch (SQLException ex) {
                    if (table == null) {
                        table.getItems().clear();
                    }
                    table.setPlaceholder(TLabel);
                }
            }
        });

        //Exit button and it's settings
        Button b3 = new Button("Exit");
        //moving the Button object Xaxis
        b3.setTranslateX(225);
        //moving the Button object Yaxis
        b3.setTranslateY(0);
        //so it's not automatically selected upon running
        b3.setFocusTraversable(false);
        //if exit button is pressed then close the stage
        b3.setOnAction(e -> {
            this.primaryStage.close();
        });
        //adding it to the GridPane
        pane2.add(b3, 0, 3);

        return pane2;
    }

    /*
    This part will Handle The TableView and how is shows for Read Tab
     */
    //SuppressWarnings used to suppress warnings
    @SuppressWarnings("unchecked")
    public TableView ShowInfo() throws SQLException {
        //set table column names and number
        TableColumn<Student, Integer> CID = new TableColumn<>("ID");
        TableColumn<Student, String> CFullName = new TableColumn<>("FullName");
        TableColumn<Student, String> CDateOfBirth = new TableColumn<>("DateOfBirth");
        TableColumn<Student, Float> CGPA = new TableColumn<>("GPA");

        //gets the data and putting it in the table colums
        CID.setCellValueFactory(cellData -> cellData.getValue().getStudentID().asObject());
        CFullName.setCellValueFactory(cellData -> cellData.getValue().getStudentFullName());
        CDateOfBirth.setCellValueFactory(cellData -> cellData.getValue().getStudentDateOfBirth());
        CGPA.setCellValueFactory(cellData -> cellData.getValue().getStudentGPA().asObject());

        CGPA.setMinWidth(70);
        CFullName.setMinWidth(150);
        CDateOfBirth.setMinWidth(80);
        CGPA.setMinWidth(50);

        table = new TableView<>();
        //putting the data colums  in the table 
        table.getColumns().addAll(CID, CFullName, CDateOfBirth, CGPA);

        //set default messeage if there is not data
        Label TLabel = new Label("No Records available \nfor this search criteria.");
        TLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        TLabel.setTextFill(Color.RED);
        table.setPlaceholder(TLabel);

        //putting the data in the table 
        table.setItems(getInfo);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //table height and column width settings
        table.setPrefHeight(200);
        table.setMaxWidth(500);
        CID.prefWidthProperty().bind(table.widthProperty().multiply(0.1));
        CFullName.prefWidthProperty().bind(table.widthProperty().multiply(0.4));
        CDateOfBirth.prefWidthProperty().bind(table.widthProperty().multiply(0.3));
        CGPA.prefWidthProperty().bind(table.widthProperty().multiply(0.19));

        return table;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
