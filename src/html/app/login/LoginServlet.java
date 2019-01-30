package html.app.login;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ecams.service.list.LoginManager;
import com.google.gson.*;

import app.common.PrjInfo;
import app.common.SysInfo;
import app.common.SystemPath;
import app.eCmd.Cmd0100;
import app.eCmr.Cmr0100;
import app.eCmr.Cmr0200;
import html.app.common.ParsingCommon;

@WebServlet("/webPage/login/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Gson gson = new Gson();
	LoginManager loginManager = LoginManager.getInstance();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
		doPost(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestType = null;
		requestType = ParsingCommon.parsingRequestJsonParamToString(request, "requestType");
		
		try {
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			
			switch (requestType) {
				case "ISVALIDLOGIN" :
					response.getWriter().write( isValidLoginUser(request) );
					break;
				case "SETSESSION" :
					response.getWriter().write( getUserName(request) );
					break;	
				default:
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		
	}//end of getSysInfo() method statement
	
	private String isValidLoginUser(HttpServletRequest request) throws SQLException, Exception {
		
		String userId = ParsingCommon.parsingRequestJsonParamToString(request, "userId");
		String userPwd = ParsingCommon.parsingRequestJsonParamToString(request, "userPwd");
		
		return gson.toJson(loginManager.isValid(userId, userPwd));
	}
	
private String getUserName(HttpServletRequest request) throws SQLException, Exception {
		
		String userId = ParsingCommon.parsingRequestJsonParamToString(request, "userId");
		HttpSession session = request.getSession();
		
		session.setAttribute("userId", userId);
		session.setAttribute("userName", loginManager.getUserName(userId));
		loginManager.setSession(session, userId);
		
		return gson.toJson( session.getId() );
	}
	
/*	private String getConfirmInfo(HttpServletRequest request) throws SQLException, Exception {
		HashMap<String, String>	confirmInfoMap = null;
		confirmInfoMap = ParsingCommon.parsingRequestJsonParamToHashMap(request, "confirmInfoData");
		return gson.toJson( cmr0200.confSelect(	confirmInfoMap.get("sysCd"),
												confirmInfoMap.get("strReqCd"),
												confirmInfoMap.get("strRsrcCd"),
												confirmInfoMap.get("userId"),
												confirmInfoMap.get("strQry")) );
	}*/
	

}
