import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class ebookstore 
{
	public static void main ( String [] args ) 
	{
		Scanner userInput = new Scanner(System.in);
		
		try (			
				// Step 1: Allocate a database 'Connection' object
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ebookstore?useSSL=false", "myuser", "mysql123");
				
				// Step 2: Allocate a 'Statement' object in the Connection
				Statement stmt = conn.createStatement();
			) 
			{			
				mainMenu(userInput, stmt);
				
			} 
		catch ( SQLException ex ) 
		{
			ex.printStackTrace();
		}		
		// Step 5: Close the resources - Done automatically by try-with-resources
		
	}
	
	
//====================================================================================================
//	Main Menu
//====================================================================================================

	
	public static void mainMenu(Scanner input, Statement stmt2) throws SQLException
	{
		boolean optionCheck = false;
		int chosenOpt = 0;
		
		System.out.println("");
		System.out.println("=========== MAIN MENU ============");
		System.out.println("|                                |");
		System.out.println("|   1. Add New Book              |");
		System.out.println("|   2. Update Book Information   |");
		System.out.println("|   3. Delete Books              |");
		System.out.println("|   4. Search Book               |");
		System.out.println("|   5. Show All books            |");
		System.out.println("|   0. Exit                      |");
		System.out.println("|                                |");
		System.out.println("==================================");
		System.out.println("|       SELECT OPTION (0 - 5)    |");
		System.out.println("==================================");
		
		
		
		while (optionCheck == false)
		{
			chosenOpt = input.nextInt();
			
			if (chosenOpt > 0 && chosenOpt < 6)
			{
				optionCheck = true;
			}
			else
			{
				System.out.println("Please Enter An Option Between 1 to 5");
			}
			
		}
		
		
		switch (chosenOpt)
		{
		case 1: enterBook(input, stmt2);
				break;
		case 2: updateBook(input, stmt2);
			 	break;
		case 3: deleteBook(input, stmt2);
	 			break;
		case 4: searchBook(input, stmt2);
				break;
		case 5: showResultSet(input, stmt2);
				mainMenu(input, stmt2);
				break;
		}		
	}

//====================================================================================================
//			Add New Record
//====================================================================================================

	
	public static void enterBook(Scanner input, Statement stmt2) throws SQLException
	{		
		System.out.println("");
		System.out.println("Enter Book id: ");		
		int idBook = input.nextInt();
		input.skip("\\R");
		
		System.out.println("");
		System.out.println("Enter Book Title: ");		
		String titleBook = input.nextLine();
		titleBook = "'" + titleBook + "'";
		
		
		System.out.println("");
		System.out.println("Enter Book Author: ");		
		String authorBook = input.nextLine();
		authorBook = "'" + authorBook + "'";
		
		System.out.println("");
		System.out.println("Enter Book Quantity: ");		
		int qtyBook = input.nextInt();
		input.skip("\\R");
		
		String sqlInsert = "insert into books values ("
		+ idBook + "," + titleBook + "," + authorBook + "," + qtyBook + ")" ;
		System . out . println ( "The SQL query is: " + sqlInsert );
		int countInserted = stmt2.executeUpdate(sqlInsert);
		System . out . println ( countInserted + " records inserted.\n" );
		
		showResultSet(input, stmt2);
		boolean optionCheck = false;
		mainMenu(input, stmt2);
	}

//====================================================================================================
//	Update Records
//====================================================================================================

	
	public static void updateBook(Scanner input, Statement stmt2) throws SQLException
	{
		System.out.println("Enter id of Book to Update OR Enter 5 for Main Menu: ");
		int idUpdate = input.nextInt();
		input.skip("\\R");
		
		System.out.println("");
		System.out.println("============== UPDATE ==============");
		System.out.println("|                                  |");
		System.out.println("|    1. ID                         |");
		System.out.println("|    2. Title                      |");
		System.out.println("|    3. Author                     |");
		System.out.println("|    4. Quantity                   |");
		System.out.println("|    5. Back to Main Menu          |");
		System.out.println("|                                  |");
		System.out.println("====================================");
		System.out.println("|       SELECT OPTION (0 - 5)      |");
		System.out.println("====================================");
		System.out.println("");
		
		int chosenOptUpdate = input.nextInt();
		input.skip("\\R");
		
		
		switch(chosenOptUpdate)
		{
		case 1: System.out.println("Enter new ID for book: ");
				int newID = input.nextInt();
				input.skip("\\R");
				
				String sqlUpdateID = "update books set id = " + newID + " where id = " + idUpdate;
				int countUpdated = stmt2 . executeUpdate (sqlUpdateID);
				System.out.println(countUpdated + " records affected.");
				
				showResultSet(input, stmt2);
				updateBook(input, stmt2);
				break;
				
		case 2: System.out.println("Enter new Title for book: ");
				String newTitle = input.nextLine();
				input.skip("\\R");
				
				String sqlUpdateTitle = "update books set Title = '" + newTitle + "' where id = " + idUpdate;
				int countUpdated1 = stmt2 . executeUpdate (sqlUpdateTitle);
				System.out.println(countUpdated1 + " records affected.");	
				
				showResultSet(input, stmt2);
				updateBook(input, stmt2);
				break;
		
		case 3: System.out.println("Enter new Author for book: ");
				String newAuthor = input.nextLine();
				input.skip("\\R");
				
				String sqlUpdateAuthor = "update books set Author = '" + newAuthor + "' where id = " + idUpdate;
				int countUpdated2 = stmt2 . executeUpdate (sqlUpdateAuthor);
				System.out.println(countUpdated2 + " records affected.");			
				
				showResultSet(input, stmt2);
				updateBook(input, stmt2);
				break;
				
		case 4: System.out.println("Enter new Quantity for book: ");
				int newQty = input.nextInt();
				input.skip("\\R");
				
				String sqlUpdateQty = "update books set Qty = " + newQty + " where id = " + idUpdate;
				int countUpdated3 = stmt2 . executeUpdate (sqlUpdateQty);
				System.out.println(countUpdated3 + " records affected.");		
				
				showResultSet(input, stmt2);
				updateBook(input, stmt2);
				break;
				
		case 5: boolean optionCheck = false;
				mainMenu(input, stmt2);
		
		}		
	}
	
	
	
//====================================================================================================
//	Delete Records
//====================================================================================================		
	
	
	public static void deleteBook(Scanner input, Statement stmt2) throws SQLException
	{		
		showResultSet(input, stmt2);
		
		System.out.println("----------------------------------------------------");
		System.out.println("Enter id of Book to Delete OR Enter 5 for Main Menu: ");
		int idDeleteRow = input.nextInt();
		input.skip("\\R");
		
		String sqlDelete = "delete from books where id = " + idDeleteRow ;
		System.out.println("Deleted Book with ID: " + idDeleteRow);
		int countDeleted = stmt2.executeUpdate(sqlDelete);
		System.out.println(countDeleted + " records deleted.\n");
		
		showResultSet(input, stmt2);
		boolean optionCheck = false;
		mainMenu(input, stmt2);
	}

	
	
//====================================================================================================
//	Search Records
//====================================================================================================		
	
	
	public static void searchBook(Scanner input, Statement stmt2) throws SQLException
	{
		
		System.out.println("Enter ID of Book to Search for: ");
		int searchID = input.nextInt();
		input.skip("\\R");
		
		String strSelect = "select * from books where id = " + searchID;
		
		ResultSet rset = stmt2.executeQuery (strSelect);
		System.out.println("---------------------Search Result---------------------");
		System.out.println("");
		while (rset.next()) 
		{
			if (rset.getInt("id") == searchID)
			{
				System. out.println(rset.getInt("id") + ", "
				+ rset.getString ("Author") + ", "
				+ rset.getString ("Title") + ", "
				+ rset.getInt("Qty"));
			}
		}
		System.out.println("");
		System.out.println("------------------------------------------------------");
		
		boolean optionCheck = false;
		mainMenu(input, stmt2);	
	}
	
	
//====================================================================================================
//	Show Existing Records
//====================================================================================================
	
	
	public static void showResultSet(Scanner input, Statement stmt2) throws SQLException
	{
		String strSelect = "select * from books" ;
		
		System.out.println("");
		System.out.println("---------------------Book Records---------------------");
		ResultSet rset = stmt2.executeQuery (strSelect);
		
		while (rset.next()) 
		{
			System. out.println(rset.getInt("id") + ", "
			+ rset.getString ("Title") + ", "
			+ rset.getString ("Author") + ", "
			+ rset.getInt("Qty"));
		}
		
		System.out.println("");
		System.out.println("------------------------------------------------------");
		
//		boolean optionCheck = false;
//		mainMenu(input, stmt2);		
	}	
}
