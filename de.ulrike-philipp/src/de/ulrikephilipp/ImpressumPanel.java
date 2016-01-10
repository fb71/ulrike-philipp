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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.widgets.Composite;

import org.polymap.rhei.batik.DefaultPanel;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.toolkit.IPanelSection;
import org.polymap.rhei.batik.toolkit.IPanelToolkit;
import org.polymap.rhei.batik.toolkit.PriorityConstraint;

import org.polymap.cms.ContentProvider;
import org.polymap.cms.ContentProvider.ContentObject;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class ImpressumPanel
        extends DefaultPanel {

    private static Log log = LogFactory.getLog( ImpressumPanel.class );

    public static final PanelIdentifier ID = new PanelIdentifier( "impressum" );

    
    @Override
    public boolean wantsToBeShown() {
        getSite().setTitle( "Impressum" );
        return getSite().getPath().size() == 2;
    }


    @Override
    public void createContents( Composite parent ) {
        IPanelToolkit tk = getSite().toolkit();
        ContentProvider cp = ContentProvider.instance();

        IPanelSection section = tk.createPanelSection( parent, "Impressum" );
        section.addConstraint( new PriorityConstraint( 10 ) );
        ContentObject co = cp.findContent( "Impressum.txt" );
        tk.createFlowText( section.getBody(), co.content() + "</br>" );
    }
    
}
