

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

public class FetchAction extends HttpServlet 
{
	String driver, url, user, pass;
	public void init(ServletConfig sc) throws ServletException
	{
		ServletContext ct=sc.getServletContext();
		driver=ct.getInitParameter("driver");
		url=ct.getInitParameter("url");
		user=ct.getInitParameter("user");
		pass=ct.getInitParameter("pass");	
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String U_User=request.getParameter("un");
	//	String U_Pass=request.getParameter("pa");
		
	try
	{

		HttpSession hs=request.getSession(false);
		PrintWriter out=response.getWriter();
		if(hs != null)
		{

			Class.forName(driver);
			RowSetFactory rf=RowSetProvider.newFactory();
			JdbcRowSet jr=rf.createJdbcRowSet();
			jr.setUrl(url);
			jr.setUsername(user);
			jr.setPassword(pass);
			jr.setCommand("select * from amazon where Username=?");
			jr.setString(1, U_User);
			jr.execute();
			String u=(String)hs.getAttribute("u");
			String p=(String)hs.getAttribute("p");
				
			Vector v=new Vector();
			for(;jr.next();)
			{
				v.add(jr.getString("Username"));
				System.out.println(v);
				if(v.contains(U_User))
				{
				
				out.println("<html>");
				out.println("<center>");
				out.println("<table border=3 >");
				out.println("<tr>");
				out.println("<th>"+"First Name"+"</th>");
				out.println("<th>"+"Last Name"+"</th>");
				out.println("<th>"+"Email"+"</th>");
				out.println("<th>"+"STDCode"+"</th>");
				out.println("<th>"+"Phone No"+"</th>");
				out.println("<th>"+"Username"+"</th>");
				out.println("<th>"+"Password"+"</th>");
			//	out.println("<th>"+"Country"+"</th>");
				out.println("<th>"+"Gender"+"</th>");
				out.println("</tr>");
			
				out.println("<tr>");
				out.println("<td>"+jr.getString(1)+"</td>");
				out.println("<td>"+jr.getString(2)+"</td>");
				out.println("<td>"+jr.getString(3)+"</td>");
				out.println("<td>"+jr.getString(4)+"</td>");
				out.println("<td>"+jr.getString(5)+"</td>");
				out.println("<td>"+jr.getString(6)+"</td>");
				out.println("<td>"+jr.getString(7)+"</td>");
				out.println("<td>"+jr.getString(8)+"</td>");
			//	out.println("<td>"+jr.getString(9)+"</td>");
				out.println("<tr>");
				out.println("</table>");
				
				out.println("</br></br></br>");
			
				out.println("</br></br></br>");
			
				out.println("<a href='EndAction'>Logout</a>");
				out.println("</center>");
				out.println("</html>");
					
		//	RequestDispatcher rd=request.getRequestDispatcher("Logout");
		//	rd.include(request, response);
				}
			}
		}
		else
		{
			out.println("<html>");
			out.println("<center>");
			out.println("Account does not exsits with your Username & Password");
			RequestDispatcher rd=request.getRequestDispatcher("Login.html");
			rd.include(request, response);
			out.println("</center>");
			out.println("</html>");
		}
		
	}
	catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
		
		
	}
	public void destroy()
	{
		
	}
}
