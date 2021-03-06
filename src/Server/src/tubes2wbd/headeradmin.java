package tubes2wbd;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class headeradmin
 */
@WebServlet("/headeradmin")
public class headeradmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public headeradmin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String type= request.getParameter("type");
		String sql;
		if(type.equals("html")) sql = "select * from barang group by kategori";
		else sql= "select count(*) from barang";
        try{
			DbConnector dbconnector = new DbConnector();
			Connection conn = dbconnector.mySqlConnection(response);
	        // Execute SQL query
	      	Statement stmt = conn.createStatement();
	      	ResultSet rs = stmt.executeQuery(sql);
	      	// Extract data from result set
	      	while(rs.next()){
	        	if(type.equals("html")){
	      		//Retrieve by column name
	         	String kategori = rs.getString("kategori");
	         	//Display values
	         	out.println("<li><a href=\"halamanbarang.jsp?kategori="+kategori+"\">"+kategori+"</a></li>");}
	        	else {
	        		//Retrieve by column name
                 	int count = rs.getInt("count(*)");
                 	//Display values
                 	out.println(""+(count-1));	              	
	        	}
	      	}
	      	// Clean-up environment
	      	rs.close();
	      	stmt.close();
	      	conn.close();
	   	}catch(SQLException se){
	      	//Handle errors for JDBC
	      	out.println(se.toString());
	   	}catch(Exception e){
	    	//Handle errors for Class.forName
	      	out.println(e.toString());
	   	}//end try
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
