package com.SOAScheduler;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.HolidayCalendar;


public class SOASchedulerServer extends HttpServlet {
    static Logger logger = Logger.getLogger(SOASchedulerServer.class);
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        logger.setLevel(Level.INFO);
        super.init(config);
        
        try {
            SchedulerFactory sf = new StdSchedulerFactory("quartzServer.properties");
            Scheduler sched = sf.getScheduler();
            sched.start();
            HolidayCalendar noHolidays = new HolidayCalendar();
            sched.addCalendar("No_Holidays", noHolidays, true, true);
        } 
        
        catch (Exception e) {
            StringWriter w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
            logger.error("Error in starting the Scheduler server.");
            logger.error(w.toString());
        }

                
       
    }
    
    public void service(HttpServletRequest request,
                        HttpServletResponse response) throws ServletException,
    IOException {
        
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>SOASchedulerServer</title></head>");
        out.println("<body>");
        out.println("<p>The Scheduler server has started and is accepting requests from the client.</p>");
        out.println("</body></html>");
        out.close();
    }
    
    public void destroy() {
        try {

            SchedulerFactory sf = new StdSchedulerFactory("quartzServer.properties");
            Scheduler sched = sf.getScheduler();
        
            if (sched != null){
                sched.shutdown(true);               
            }
            
        } catch (Exception e) {
            StringWriter w = new StringWriter();
            e.printStackTrace(new PrintWriter(w));
            logger.error("Error in shutting down the Scheduler server.");
            logger.error(w.toString());
        }

    }

}
