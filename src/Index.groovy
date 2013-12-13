import javax.servlet.http.*

import com.google.apphosting.utils.remoteapi.RemoteApiPb.Request;

class Index extends HttpServlet {
	
	void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.sendRedirect "input.groovy"
	}
	
}
