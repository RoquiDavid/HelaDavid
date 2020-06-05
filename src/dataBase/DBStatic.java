package dataBase;


public class DBStatic {
	
	public static final String mysql_host="jdbc:mysql://localhost/";
	public static final String mysql_user="root";
	public static final String mysql_password="";
	public static final String mysql_db="db1";
	public static final boolean pooling=false;
	public static final int emptyCaseError = -1;
	public static final int notInDbError = 2;
	public static final int checkError = 3;
	public static final int outDatedKeyError = 4;
	public static final int alreadyInDbError = 5;
	public static final int wrongFormatError = 6;
	public static final int jsonError = 100;
	public static final int sqlError = 1000;
	public static final int javaError = 10000;
	
}