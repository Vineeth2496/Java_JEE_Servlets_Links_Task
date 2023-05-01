

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

public class LoginAction extends HttpServlet 
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
		String U_Pass=request.getParameter("pa");
		try
		{
			Class.forName(driver);
			RowSetFactory rf=RowSetProvider.newFactory();
			JdbcRowSet jr=rf.createJdbcRowSet();
			jr.setUrl(url);
			jr.setUsername(user);
			jr.setPassword(pass);
			jr.setCommand("select Username,Password from amazon");
			jr.execute();
			Vector v=new Vector();
			for(;jr.next();)
			{
				v.add(jr.getString("Username"));
				v.add(jr.getString("Password"));
			}
			PrintWriter out=response.getWriter();
			HttpSession hs=request.getSession();
			if(v.contains(U_User)&& v.contains(U_Pass))
			{
				hs.setAttribute("u", U_User);
				hs.setAttribute("p", U_Pass);
				RequestDispatcher rd=request.getRequestDispatcher("FetchAction");
				rd.forward(request, response);
			}
			else {
				out.println("<html>");
			//	out.println("<body style="color:white;">");
				out.println("<center>");
				out.println("Invalid Username or Password" );
				RequestDispatcher rd=request.getRequestDispatcher("Login.html");
				rd.include(request, response);
				out.println("</center>");
			//	out.println("</body>");
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
