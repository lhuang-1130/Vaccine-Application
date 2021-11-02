import java.sql.* ;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.Date;

class VaccineApp {
    private static Connection con;
    static Scanner scanner = new Scanner(System.in);
    public static void main ( String [ ] args ) throws SQLException
    {
        /*-----------------------Connection-------------------------*/
        // Register the driver.  You must register the driver before you can use it.
        try { DriverManager.registerDriver ( new com.ibm.db2.jcc.DB2Driver() ) ; }
        catch (Exception cnfe){ System.out.println("Class not found"); }
        // This is the url you must use for DB2.
        //Note: This url may not valid now !
        String url = "jdbc:db2://winter2021-comp421.cs.mcgill.ca:50000/cs421";

        //REMEMBER to remove your user id and password before submitting your code!!
        String your_userid = "lhuang53";
        String your_password = "gJQ9ck6N";

        if(your_userid == null && (your_userid = System.getenv("SOCSUSER")) == null)
        {
          System.err.println("Error!! do not have a password to connect to the database!");
          System.exit(1);
        }
        if(your_password == null && (your_password = System.getenv("SOCSPASSWD")) == null)
        {
          System.err.println("Error!! do not have a password to connect to the database!");
          System.exit(1);
        }

        con = DriverManager.getConnection (url,your_userid, your_password);


        /*------------------------Menu Section-------------------------*/

        int input;
        Boolean exit = false;

        while(!exit){
            //Print out the menu
            System.out.println("|-----VaccineApp Main Menu------|");
            System.out.println("1. Add a Person");
            System.out.println("2. Assign a slot to a Person");
            System.out.println("3. Enter Vaccination information");
            System.out.println("4. Exit Application");
            System.out.println("Please Enter The Number of The Action of Your Option:");

            input = scanner.nextInt();

            switch (input){
                case 1:
                    addPerson();
                    break;
                case 2:
                    assignSlot();
                    break;
                case 3:
                    enterInfo();
                    break;
                case 4:
                    exit = true;
                    System.out.println("Thank you for using the app. Have a great day!");
                    con.close();
                    break;
                default:
                    System.out.println("You input is invalid.");
                    break;
            }
        }
        scanner.close();
    }

    /*-------------------------------Question 1------------------------------*/
    /*
    * 1. allow the user to enter (type) the relevant information.
    * 2. If the user's health insurance number is already associated with someone else, the application should stop adding the new person and instead respond with an appropriate message to the user.
    * 3. At this point the system should prompt the user whether they wish to update the existing Person information associated with that insurance number with the newly entered details.
    * 4. If the user chooses to do so, then the existing information is updated.
    * 5. Otherwise, no action is taken
    * */
    public static void addPerson() throws SQLException {
        Statement statement = con.createStatement();
        ResultSet rs;
        /*-----------Collect information from user--------------*/
        System.out.println("Please enter health insurance ID.");
        //Required fields for a person
        int healthInsure = scanner.nextInt(); //primary key
        System.out.println(healthInsure + " is entered. Please enter the birth date.");
        String birthDate = scanner.next();
        System.out.println(birthDate + " is entered. Please enter the registration date.");
        String regisDate = scanner.next();
        System.out.println(regisDate + " is entered. Please enter gender.");
        String gender = scanner.next();
        System.out.println(gender + " is entered. Please enter name.");
        //Contains space
        String name = scanner.nextLine();
        name += scanner.nextLine();
        System.out.println(name + " is entered. Please enter phone number.");
        //Contains space
        String phoneNum = scanner.nextLine();
        System.out.println(phoneNum + " is entered. Please enter city.");
        String city  = scanner.next();
        System.out.println(city + " is entered. Please enter postal code.");
        //Contains space
        String posCode = scanner.nextLine();
        posCode += scanner.nextLine();
        System.out.println(posCode + " is entered. Please enter address.");
        //Contains space
        String address = scanner.nextLine();
        System.out.println(address + " is entered. Please enter category.");
        //Contains space
        String category = scanner.nextLine();
        System.out.println(category + " is entered. All the required information are collected.");

        //Check if this person has already existed in the database
        Boolean notFound = false;

        rs = statement.executeQuery("SELECT * FROM Person WHERE healthinsur =" + healthInsure);
        if(!rs.next()){
            notFound = true;
        }
        //this means this person is already in the database
        if(notFound == false){
            System.out.println("This person already exists on the database.");
            System.out.println("Do you want to update this person's information with the newly entered details?");
            System.out.println("Please enter YES or NO.");
            String input;
            input = scanner.next();

            //Update
            if(input.equals("YES")){
                String query1 = "UPDATE Person SET birthDate = '" + birthDate + "' WHERE healthinsur =" + healthInsure;
                statement.executeUpdate(query1);
                System.out.println("Updated birth date successfully.");

                String query2 =  "UPDATE Person SET regisDate = '" + regisDate + "' WHERE healthinsur =" + healthInsure;
                statement.executeUpdate(query2);
                System.out.println("Updated registration date successfully.");

                String query3 = "UPDATE Person SET gender = '" +gender + "' WHERE healthinsur =" + healthInsure;
                statement.executeUpdate(query3);
                System.out.println("Updated gender successfully.");

                String query4 =  "UPDATE Person SET name = '" + name + "' WHERE healthinsur =" + healthInsure;
                statement.executeUpdate(query4);
                System.out.println("Updated name successfully.");

                String query5 = "UPDATE Person SET phone = '" + phoneNum + "' WHERE healthinsur =" + healthInsure;
                statement.executeUpdate(query5);
                System.out.println("Updated phone successfully.");

                String query6 =  "UPDATE Person SET city = '" + city + "' WHERE healthinsur =" + healthInsure;
                statement.executeUpdate(query6);
                System.out.println("Updated city successfully.");

                String query7 = "UPDATE Person SET posCode = '" + posCode + "' WHERE healthinsur =" + healthInsure;
                statement.executeUpdate(query7);
                System.out.println("Updated postal code successfully.");

                String query8 =  "UPDATE Person SET address = '" + address + "' WHERE healthinsur =" + healthInsure;
                statement.executeUpdate(query8);
                System.out.println("Updated address successfully.");

                String query9 =  "UPDATE Person SET category = '" + category + "' WHERE healthinsur =" + healthInsure;
                statement.executeUpdate(query9);
                System.out.println("Updated category successfully.");

            } else if (input.equals("NO")){ //existing information is NOT updated.
                System.out.println("Your input is NO, therefore, no information is updated.");
            }
        } else {
            String query =
                    "INSERT INTO Person VALUES ("+healthInsure + ",'" + birthDate + "','" + regisDate + "','" + gender + "','" + name + "','" + phoneNum + "','" + city + "','" + posCode + "','" + address + "','" + category+"' )";
            statement.executeUpdate(query);
            System.out.println("This person has been added to the database successfully.");
        }
        statement.close();
        rs.close();
    }

    /*-------------------------------Question 2------------------------------*/
    /*
    * 1. Prompt the user for any necessary input and update the slot allocation information in the database.
    * 2. The application should not allow the user to assign a slot to this Person, if the said Person has already taken the required number of shots for that particular brand of vaccine (if it is not their first shot).
    * 3. You can assume that a person is never vaccinated by two different brands.
    * 4. Do not assign a slot if that is already given to someone else or is in the past
    * */
    public static void assignSlot() throws SQLException {
        Statement statement = con.createStatement();
        ResultSet rs;
        PreparedStatement prepareCid;

        /*-----------Collect information from user--------------*/
        System.out.println("Please enter health insurance ID.");
        //Required fields for an appointment
        int healthInsure = scanner.nextInt();
        System.out.println(healthInsure + " is entered. Please enter the appointment location.");
        String location = scanner.nextLine();
        location += scanner.nextLine();
        System.out.println(location + " is entered. Please enter the appointment date.");
        String appDate = scanner.next();
        System.out.println(appDate + " is entered. Please enter appointment time.");
        String appTime = scanner.next();
        System.out.println(appTime+ " is entered. Please enter slot ID.");
        int slotID = scanner.nextInt();
        System.out.println(slotID + " is entered. Please enter the date on which this appointment is made.");
        String callDate = scanner.next();
        System.out.println(callDate + " is entered. All the required information are collected.");

        /*------------Check if this slot exists on the database--------------*/
        prepareCid = con.prepareStatement("SELECT COUNT(*) AS slotCount FROM Slot WHERE appDate = ? AND appTime = ? AND slotID = ? AND loName = ?");
        prepareCid.setString(1, appDate);
        prepareCid.setString(2, appTime);
        prepareCid.setInt(3,slotID);
        prepareCid.setString(4, location);
        int sCount = -1;
        rs = prepareCid.executeQuery();
        while(rs.next()){
            sCount = rs.getInt("slotCount");
            System.out.println("Available slot: " + sCount);
        }

        if(sCount != 1){
            System.out.println("This slot is not on the database.");
            return;
        }

        /*-----------Check if the person already has enough of vaccine shots--------------*/
        //Check how many shots this person has taken
        prepareCid = con.prepareStatement("SELECT COUNT(*) AS Count FROM Vaccinated, Person, Appointment WHERE Person.healthinsur = ? AND Person.healthinsur = Appointment.healthinsur " +
                "AND Vaccinated.appDate = Appointment.appDate AND Vaccinated.appTime = Appointment.appTime AND Vaccinated.loName = Appointment.loName AND Vaccinated.slotID = Appointment.slotID");
        prepareCid.setInt(1, healthInsure);
        int count = -1;
        rs = prepareCid.executeQuery();
        while(rs.next()){
            count = rs.getInt("Count");
            System.out.println("This person has taken " + count + " vaccine shots");
        }

        //Check which vaccine this person has taken
        prepareCid = con.prepareStatement( "SELECT DISTINCT vname FROM Vaccinated, Person, Appointment WHERE Person.healthinsur = ? AND Person.healthinsur = Appointment.healthinsur " +
                "AND Vaccinated.appDate = Appointment.appDate AND Vaccinated.appTime = Appointment.appTime AND Vaccinated.loName = Appointment.loName AND Vaccinated.slotID = Appointment.slotID");
        prepareCid.setInt(1, healthInsure);
        String vaccineName = "";
        rs = prepareCid.executeQuery();
        while(rs.next()){
            vaccineName = rs.getString("vname");
            System.out.println("Vaccine name " + vaccineName);
        }

        prepareCid = con.prepareStatement ("SELECT requiredDose FROM Vaccine WHERE Vaccine.vname = ?");
        prepareCid.setString(1, vaccineName);
        int requiredDose = 1; //At least 1 dose
        rs = prepareCid.executeQuery();
        while(rs.next()){
            requiredDose = rs.getInt("requiredDose");
            System.out.println("Required dose " + requiredDose);
        }

        if(count >= requiredDose){
            System.out.println("This person already has enough of vaccine shots. No slot will be assigned.");
            return;
        }

        /*-----------Check if the slot has already been assigned--------------*/
        prepareCid = con.prepareStatement("SELECT COUNT(*) AS assignmentCount FROM Appointment WHERE appDate = ? AND appTime = ? AND slotID = ? AND loName = ?");
        prepareCid.setString(1, appDate);
        prepareCid.setString(2, appTime);
        prepareCid.setInt(3,slotID);
        prepareCid.setString(4, location);
        rs = prepareCid.executeQuery();
        int aCount = -1;
        while(rs.next()){
            aCount = rs.getInt("assignmentCount");
            System.out.println("Assignment count with the given information: " + aCount);
        }

        if(aCount != 0){
            System.out.println("This slot has been assigned to someone else.");
            return;
        }

        /*-----------Check if the slot has already expired--------------*/
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("MM/dd/yy");
        try {
            Date date1 = df.parse(appDate);
            //If appointment date is before the current date
            if(date1.before(date)){
                System.out.println("This appointment date is no longer valid.");
                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        /*-------------Assign a slot to Person--------------*/
        prepareCid = con.prepareStatement("INSERT INTO Appointment VALUES (?, ?, ?, ?, ?, ?)");
        prepareCid.setInt(1, healthInsure);
        prepareCid.setString(2, appTime);
        prepareCid.setString(3, appDate);
        prepareCid.setInt(4, slotID);
        prepareCid.setString(5, location);
        prepareCid.setString(6, callDate);
        prepareCid.executeUpdate();
        System.out.println("A slot has been assigned to this person successfully.");

        prepareCid.close();
        statement.close();
        rs.close();
    }

    /*
    * 1. a certain Person was vaccinated.
    * 2. record appropriate information for this
    * 3. Stop the entry from being made if the vial is from a vaccine brand that is different from previously applied vaccine brand for that Person
    * */
    public static void enterInfo() throws SQLException {
        Statement statement = con.createStatement();
        ResultSet rs;
        PreparedStatement prepareCid;

        /*-----------Collect information from user--------------*/
        System.out.println("Please enter vaccine name.");
        //Required fields for an appointment
        String vaccineName = scanner.next();
        System.out.println(vaccineName+ " is entered. Please enter the batch number.");
        int batchNum = scanner.nextInt();
        System.out.println(batchNum + " is entered. Please enter the vial number.");
        int vialNum = scanner.nextInt();
        System.out.println(vialNum + " is entered. Please enter the vaccination location.");
        String location = scanner.nextLine();
        location += scanner.nextLine();
        System.out.println(location + " is entered. Please enter the appointment date.");
        String appDate = scanner.next();
        System.out.println(appDate + " is entered. Please enter appointment time.");
        String appTime = scanner.next();
        System.out.println(appTime+ " is entered. Please enter slot ID.");
        int slotID = scanner.nextInt();
        System.out.println(slotID + " is entered. Please enter the nurse license number.");
        int licenseNum = scanner.nextInt();
        System.out.println(licenseNum + " is entered. All the required information are collected.");

        /*-----------Check if the vial is already on the database--------------*/
        prepareCid = con.prepareStatement("SELECT COUNT(*) AS vialCount FROM Vial WHERE batchNum = ? AND vialNum = ? AND vname = ?");
        prepareCid.setInt(1, batchNum);
        prepareCid.setInt(2, vialNum);
        prepareCid.setString(3, vaccineName);
        int vCount = -1;
        rs = prepareCid.executeQuery();
        while(rs.next()){
            vCount = rs.getInt("vialCount");
            System.out.println("Vial presented with the provided information: " + vCount);
        }

        if(vCount != 0){
            System.out.println("This vial is already on the database.");
            return;
        }

        /*------------Check if vaccine name is the same as previous one the person receives------------*/
        //Find the health insurance number
        prepareCid = con.prepareStatement("SELECT healthinsur FROM Appointment WHERE loName = ? AND appDate = ? AND appTime = ? AND slotID = ?");
        prepareCid.setString(1, location);
        prepareCid.setString(2, appDate);
        prepareCid.setString(3, appTime);
        prepareCid.setInt(4, slotID);
        int healthinsure = -1;
        rs = prepareCid.executeQuery();
        while(rs.next()){
            healthinsure = rs.getInt("healthinsur");
            System.out.println("Health insurance number associated with the provided information: " + healthinsure);
        }

        //Find the previous appointment information
        prepareCid = con.prepareStatement("SELECT COUNT(*) AS appCount FROM Appointment WHERE healthinsur = ?");
        prepareCid.setInt(1, healthinsure);
        int appCount = -1;
        rs = prepareCid.executeQuery();
        while(rs.next()){
            appCount = rs.getInt("appCount");
            System.out.println("This person has " + appCount + " appointment(s) in total.");
        }

        //Check the person has an appointment previously, check the vaccine name
        if(appCount > 1){
            prepareCid = con.prepareStatement("SELECT loName, appDate, appTime, slotID FROM Appointment WHERE healthinsur = ? AND appDate < ?");
            prepareCid.setInt(1, healthinsure);
            prepareCid.setString(2, appDate);
            String loName = "";
            String prevDate = "";
            String prevTime = "";
            int prevSlot = -1;
            rs = prepareCid.executeQuery();
            while (rs.next()){
                loName = rs.getString("loName");
                prevDate = rs.getString("appDate");
                prevTime = rs.getString("appTime");
                prevSlot = rs.getInt("slotID");
            }

            prepareCid = con.prepareStatement("SELECT vname FROM Vaccinated WHERE loName = ? AND appDate = ? AND appTime = ? AND slotID = ?");
            prepareCid.setString(1, loName);
            prepareCid.setString(2, prevDate);
            prepareCid.setString(3, prevTime);
            prepareCid.setInt(4, prevSlot);
            String prevVaccine = "";
            rs = prepareCid.executeQuery();
            while (rs.next()){
                prevVaccine = rs.getString("vname");
            }

            if(!prevVaccine.equals(vaccineName)){
                System.out.println("Current vaccine shot's brand is different from the previous one. Cannot enter into the database.");
                return;
            }
        }

        //Insert the new vial information into database
        prepareCid = con.prepareStatement("INSERT INTO Vial VALUES (?, ?, ?)");
        prepareCid.setInt(1, vialNum);
        prepareCid.setInt(2, batchNum);
        prepareCid.setString(3, vaccineName);
        prepareCid.executeUpdate();
        System.out.println("New vial record has been entered into the database.");
        //Insert the new vaccinated record into database
        prepareCid = con.prepareStatement("INSERT INTO Vaccinated VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        prepareCid.setInt(1, licenseNum);
        prepareCid.setInt(2, vialNum);
        prepareCid.setInt(3,batchNum);
        prepareCid.setString(4,vaccineName);
        prepareCid.setString(5,appTime);
        prepareCid.setString(6,appDate);
        prepareCid.setInt(7, slotID);
        prepareCid.setString(8, location);
        prepareCid.executeUpdate();
        System.out.println("New vaccination record has been entered into the database.");

        prepareCid.close();
        statement.close();
        rs.close();
    }


}
