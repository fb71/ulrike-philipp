/* 
 * Copyright (C) 2014, Falko Bräutigam. All rights reserved.
 */
package de.ulrikephilipp;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Redirects each and every request to /up
 * 
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class RedirectServlet
        extends HttpServlet {

    private static Log log = LogFactory.getLog( RedirectServlet.class );

    @Override
    protected void service( HttpServletRequest req, HttpServletResponse resp )
            throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        log.info( "REDIRECT: request=" + pathInfo );

        // XXX quick and dirty fix: chrome asks for favicon when opening report page
        // which ends session without this check
        if ("/favicon.ico".equals( pathInfo )) {
            log.warn( "    ...skipped." );
            return;
        }
        
        resp.sendRedirect( "up" );
    }

}
