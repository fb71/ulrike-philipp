/* 
 * polymap.org
 * Copyright (C) 2014, Falko Bräutigam. All rights reserved.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3.0 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */
package de.ulrikephilipp;

import java.util.Locale;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.osgi.util.tracker.ServiceTracker;

import org.eclipse.ui.plugin.AbstractUIPlugin;

import org.polymap.core.security.SecurityContext;
import org.polymap.core.security.StandardConfiguration;

import org.polymap.cms.ContentProvider;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class UpPlugin
        extends AbstractUIPlugin {

    public static final String PLUGIN_ID = "de.ulrike-philipp";

    private static UpPlugin      instance;
    
    
    public static UpPlugin instance() {
        return instance;
    }


    private ServiceTracker httpServiceTracker;


    // instance *******************************************
    
    public void start( BundleContext context ) throws Exception {
        super.start( context );
        instance = this;

        // JAAS config: no dialog; let LoginPanel create UI
        SecurityContext.registerConfiguration( () -> new StandardConfiguration() {
            @Override
            public String getConfigName() {
                return SecurityContext.SERVICES_CONFIG_NAME;
            }
        });
        
        ContentProvider.instance().defaultLocale.set( Locale.GERMAN );
        
        // register HTTP resource
        httpServiceTracker = new ServiceTracker( context, HttpService.class.getName(), null ) {
            public Object addingService( ServiceReference reference ) {
                HttpService httpService = (HttpService)super.addingService( reference );                
                if (httpService != null) {
                    try {
                        httpService.registerResources( "/upres", "/resources", null );
                    }
                    catch (NamespaceException e) {
                        throw new RuntimeException( e );
                    }
                }
                return httpService;
            }
        };
        httpServiceTracker.open();

    }


    public void stop( BundleContext context ) throws Exception {
        httpServiceTracker.close();
        instance = null;
        super.stop( context );
    }

}
