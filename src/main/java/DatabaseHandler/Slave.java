package DatabaseHandler;

import java.sql.PreparedStatement;
import java.sql.Connection;


public class Slave extends Thread{

    private Connection con;

    public Slave(Connection con){
        this.con = con;
    }

    public void run(){

    }

}
