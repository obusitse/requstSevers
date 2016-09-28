package javaBeen;

import com.mysql.jdbc.Statement;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static java.lang.System.out;

@ManagedBean(name = "phone")
@SessionScoped
public class UserBean implements Serializable {
    /* Start Of Values */
    private static final long serialVersionUID = 1L;
    private String make; // Used for the make of the phone
    private String model;// Used for the model of the phone
    private String memory;// Used for the memory of the phone

    /*
     * used in the add function to test if model is the same I was going to use
     * the model as the primary key
     */
    private boolean used;
    private boolean makeB;
    private boolean modelB;
    private String colour; // Used for the colour of the phone

    /* return ture or false to disable the save button */
    public String getDisable() {
        if (phoneList.size() == 0)
            return "true";
        else
            return "false";
    }

    /* set and get Start */
    public void setMake(String make) {
        this.make = make;
    }

    public String getMake() {
        return make;
    }


    public String getUsed() {
        if (used)
            return "visible";
        return "hidden";
    }

    public String getMakeB() {
        if (makeB)
            return "visible";
        else
            return "hidden";
    }

    public String getModelB() {
        if (modelB)
            return "visible";
        else
            return "hidden";
    }

    public String getModel() {
        return model;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getMemory() {
        return memory;
    }

    public void setModel(String model) {
        this.model = model;
    }

	/* Set and get End */

    /* Start Start of list used for demo only */
    private static final ArrayList<Phone> phoneList = new ArrayList<Phone>();

    public ArrayList<Phone> getPhoneList() {
        return phoneList;
    }

    /*
     * undo method used to get the last remove item back or remove a new entry
     */
    public String Undo() {
        phoneList.clear();
        return null;
    }

    public void main() throws SQLException {
        phoneList.clear();
        out.println("-------- MySQL JDBC Connection Testing ------------");
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }
        out.println("MySQL JDBC Driver Registered!");
        Connection connection = null;
        try {
            connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/?user=root", "root", "root");

        } catch (SQLException e) {
            out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
        if (connection != null) {
            out.println("You made it, take control your database now!");
        } else {
            out.println("Failed to make connection!");
        }
        Statement stmt = (Statement) connection.createStatement();
        String t = getMake();
        String email = getModel();
        String order = getMemory();
        if (t.length() == 0) t = "";
        if (email.length() == 0) email="";
        if (order.length() == 0) order="";
        ResultSet rs = stmt.executeQuery("SELECT * FROM mydb.t1 WHERE Order_Number = '"+order+"' OR EMAIL_ADDRESS = '"+email+"' OR MSISDN = '"+t+"' ;");
        while (rs.next()) {
            String orderNumber = rs.getString("Order_Number");
            String msisdn = rs.getString("MSISDN");
            String transactionType = rs.getString("TRANSACTION_TYPE");
            String paymentType = rs.getString("PAYMENT_TYPE");
            String orderStatus = rs.getString("ORDER_STATUS");
            String paymentStatus = "payed";
            String channel = rs.getString("CHANNEL");
            String surname = rs.getString("SURNAME");
            String firstname = rs.getString("CHANNEL");
            String channel2 = rs.getString("CHANNEL2");
            String emailAddress = rs.getString("EMAIL_ADDRESS");
            String creationDate = rs.getString("CREATION_DATE");
            String modifiedDate = rs.getString("MODIFIED_DATE");

            Phone phone = new Phone(orderNumber, msisdn,
                    transactionType,
                    paymentType,
                    orderStatus,
                    paymentStatus,
                    channel,
                    surname,
                    firstname,
                    channel2,
                    emailAddress,
                    creationDate,
                    modifiedDate);
            out.println(orderNumber + "\t" +
                    msisdn + "\t" +
                    transactionType + "\t" +
                    paymentType + "\t" +
                    orderStatus + "\t" +
                    paymentStatus + "\t" +
                    channel + "\t" +
                    surname + "\t" +
                    firstname + "\t" +
                    channel2 + "\t" +
                    emailAddress + "\t" + phoneList.size());
            phoneList.add(phone);
        }
        try {
            connection.close();
        } catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

    /* Phone class for display in the Table */
    public static class Phone {
        String orderNumber;
        String msisdn;
        String transactionType;
        String paymentType;
        String orderStatus;
        String paymentStatus;
        String channel;
        String surname;
        String firstname;
        String channel2;
        String emailAddress;
        String creationDate;
        String modifiedDate;

        public Phone(String orderNumber,
                     String msisdn,
                     String transactionType,
                     String paymentType,
                     String orderStatus,
                     String paymentStatus,
                     String channel,
                     String surname,
                     String firstname,
                     String channel2,
                     String emailAddress,
                     String creationDate,
                     String modifiedDate) {
            this.orderNumber = orderNumber;
            this.msisdn = msisdn;
            this.transactionType = transactionType;
            this.paymentType = paymentType;
            this.orderStatus = orderStatus;
            this.paymentStatus = paymentStatus;
            this.channel = channel;
            this.surname = surname;
            this.firstname = firstname;
            this.channel2 = channel2;
            this.emailAddress = emailAddress;
            this.creationDate = creationDate;
            this.modifiedDate = modifiedDate;

        }


        public String getOrderNumber() {
            return orderNumber;
        }


        public String getMsisdn() {
            return msisdn;
        }

        public String getTransactionType(){
            return transactionType;
        }

        public String getPaymentType(){
            return paymentType;
        }

        public String getOrderStatus(){
            return orderStatus;
        }

        public String getPaymentStatus(){
            return paymentStatus;
        }

        public String getChannel(){
            return channel;
        }

        public String getSurname(){
            return surname;
        }

        public String getFirstname(){
            return firstname;
        }

        public String getChannel2(){
            return channel2;
        }

        public String getEmailAddress(){
            return emailAddress;
        }

        public String getCreationDate(){
            return creationDate;
        }

        public String getModifiedDate(){
            return modifiedDate;
        }
    }
}