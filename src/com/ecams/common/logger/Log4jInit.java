package com.ecams.common.logger;
 import org.apache.log4j.PropertyConfigurator;
 import javax.servlet.http.HttpServlet;
 import javax.servlet.http.HttpServletRequest;
 import javax.servlet.http.HttpServletResponse;
 
 public class Log4jInit extends HttpServlet {
   private static final long serialVersionUID = -5104057715091654401L;
   
   public void init() {
     String prefix =  getServletContext().getRealPath("/");
     String file = getInitParameter("log4j-init-file");
     System.out.println("Initializing log4j with " + prefix + file);
     // if the log4j-init-file is not set, then no point in trying
     if(file != null) {
       PropertyConfigurator.configure(prefix+file);
     }
   }
   public void doGet(HttpServletRequest req, HttpServletResponse res) {
     // Nothing happening here yet.
   }
 }
