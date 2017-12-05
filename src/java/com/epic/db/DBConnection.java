package com.epic.db;

import com.epic.init.InitConfigValue;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import snaq.db.ConnectionPool;




public class DBConnection{
	private static ConnectionPool pool = null;

	public static  void createDbPool()throws Exception{
		
		
                Driver driver = (Driver)Class.forName(InitConfigValue.DBDRIVER).newInstance();
		DriverManager.registerDriver(driver);
		
		if (InitConfigValue.DBCONEXPIRTIMEOUT==0){
                        System.out.println("Creating a non-expiring icbs database connection pool");
		}else{	
                        System.out.println("Creating an expiring icbs database connection pool with expir timeout ["+InitConfigValue.DBCONEXPIRTIMEOUT+"] (s)"); 

		}
		
		pool = new ConnectionPool("MysqlK",
				InitConfigValue.MINPOOL,
				InitConfigValue.MAXPOOL,
				InitConfigValue.MAXCON,
				InitConfigValue.DBCONEXPIRTIMEOUT,
				InitConfigValue.DBURL,
				InitConfigValue.DBUSERNAME,
				InitConfigValue.DBPASSWORD);
		

		
		Thread.sleep(3000);
		Connection contest = getConnection();
		dbConnectionClose(contest);
                 System.out.println("Establish the database connection.... ");

	}
	

	
	public static  Connection getConnection()throws Exception{
		return  pool.getConnection();
	}
	
	
	
	public static void dbConnectionClose(Connection con )throws Exception{
		if(con !=null){con.close();con=null;}
	}
		
        protected static  int getNumberofcheckoutCon()throws Exception{
		return  pool.getCheckedOut();
	}
	protected static  int getNumberoffreeCon()throws Exception{
		return  pool.getFreeCount();
	}
        
        public static String checkDBPoolStatus()throws Exception{
		String msg = "Database pool status : free connections : "+pool.getFreeCount()+"  Active connections : "+pool.getCheckedOut();
		return msg;

	}
}
