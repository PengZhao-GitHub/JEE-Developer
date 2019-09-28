                Sun Certified Developer for the Java 2 Platform
                       Application Submission Readme
                           Candidate: Peng Zhao
                               ID: SP9143168
================================================================================

1. JDK verion and the platform
------------------------------
java version:  1.3.1
platform:      WindowsXP Home Edition

2. How to execute the programs
------------------------------
[Server]
Copy the scjdServer.jar and the db.db file to any directory you want. Enter the 
directory which has the scjdServer.jar file. Then type the following command.
   
   java -cp scjdServer.jar suncertify.server.Server port db_path

eg.
   java -cp scjdServer.jar suncertify.server.Server 5001 e:\db.db

[Client]
Copy the scjdClient.jar and the db.db file to any directory you want. Enter the 
directory which has the scjdServer.jar file. Then type the following command.

   java -cp scjdClient.jar suncertify.client.Client
   java -cp scjdClient.jar suncertify.client.Client db_path
   java -cp scjdClient.jar suncertify.client.Client server_address port

eg.
   java -cp scjdClient.jar suncertify.client.ClientApp c:\db.db
   java -cp scjdClient.jar suncertify.client.Client 192.168.1.23 5001
   
3. The location of the db.db
----------------------------
submission/build/db.db

4. The location of the Design Choices document
----------------------------------------------
submission/DesignChoicesDoc.txt

5. File description
-------------------
submission
„   DesignChoicesDoc.txt                 //Design Choices document
„   README.txt                           //This README.txt file
„   UserDoc.txt                          //User documentation
„   
„¥„Ÿassignment                           ** Original assignment files **
„   „   instructions.html
„   „   
„   „¤„Ÿstarting
„       „¤„Ÿsuncertify
„           „¤„Ÿdb
„                   Data.class
„                   Data.java
„                   DatabaseException.class
„                   DatabaseException.java
„                   DataInfo.class
„                   DataInfo.java
„                   db.db
„                   FieldInfo.class
„                   FieldInfo.java
„                   
„¥„Ÿbuild                                ** Classfiles and the db file **
„       db.db                            //db file]
„       scjdClient.jar                   //Client classfiles]
„       scjdServer.jar                   //Server classfiles]
„       
„¥„Ÿdoc                                  ** HTML/Javadoc documentation **
„¤„Ÿsrc                                  ** Source files **
    „¤„Ÿsuncertify
        „¥„Ÿclient
        „       BookFlight.java          //Flight's seats booking dialog box
        „       Client.java              //Client's entry point
        „       ClientFrame.java         //Main user interface
        „       ClientModel.java         //subclass of the AbstractTableModel
        „       ClientNet.java           //Client's network functions
        „       ClientProxy.java         //Database serives proxy
        „       DBLocationSetting.java   //DB location setting dialog box
        „       
        „¥„Ÿdb
        „       Data.java                //Database services implementation
        „       DatabaseException.java   //Database error
        „       DataInfo.java            //Description of a record
        „       FieldInfo.java           //Description of a field
        „       LockManager.java         //Lock management functions
        „       Service.java             //Database services interface
        „       
        „¥„Ÿprotocol
        „       NetProtocol.java         //Network protocal
        „       Request.java             //Request object
        „       Response.java            //Response object
        „       
        „¤„Ÿserver
                Server.java              //Socket based multithread server
                ServerStub.java          //Database services stub
                ServerUtility.java       //Server side utility class
                
