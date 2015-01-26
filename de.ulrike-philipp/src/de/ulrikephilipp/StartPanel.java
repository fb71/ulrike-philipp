/* 
 * Copyright (C) 2014, Falko Bräutigam. All rights reserved.
 */
package de.ulrikephilipp;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.widgets.Composite;

import org.polymap.rhei.batik.DefaultPanel;
import org.polymap.rhei.batik.IAppContext;
import org.polymap.rhei.batik.IPanelSite;
import org.polymap.rhei.batik.PanelIdentifier;
import org.polymap.rhei.batik.toolkit.IPanelSection;
import org.polymap.rhei.batik.toolkit.IPanelToolkit;
import org.polymap.rhei.batik.toolkit.MinWidthConstraint;
import org.polymap.rhei.batik.toolkit.PriorityConstraint;

import org.polymap.cms.ContentProvider;
import org.polymap.cms.ContentProvider.ContentObject;

/**
 * The front page of ulrike-philipp.de :)
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class StartPanel
        extends DefaultPanel {

    private static Log log = LogFactory.getLog( StartPanel.class );

    public static final PanelIdentifier ID = new PanelIdentifier( "start" );

    @Override
    public boolean init( IPanelSite site, IAppContext context ) {
        super.init( site, context );
        return false;
    }


    @Override
    public PanelIdentifier id() {
        return ID;
    }


    @Override
    public void createContents( Composite parent ) {
        getSite().setTitle( "Start" );
        IPanelToolkit tk = getSite().toolkit();        

        ContentProvider cp = ContentProvider.instance();

        IPanelSection welcome = tk.createPanelSection( parent, "Willkommen" );
        welcome.addConstraint( new PriorityConstraint( 10 ), new MinWidthConstraint( 300, 10 ) );
        ContentObject welcomeContent = cp.findContent( "frontpage/willkommen.txt" );
        tk.createFlowText( welcome.getBody(), welcomeContent.content() + "</br>" );
        //tk.createFlowText( welcome.getBody(), "Krisenbegleitung · Strukturberatung · Seminare<br/>Einzelpersonen · Paare · Unternehmen<br/>Leipzig · Bundesweit · Telefonisch " );

//        IPanelSection image = tk.createPanelSection( parent, null );
//        image.addConstraint( new PriorityConstraint( 5 ), 
//                new MinWidthConstraint( 250, 10 ), new MinHeightConstraint( 456, 10 ) );
////        tk.createLabel( image.getBody(), "..." ).setImage( )
////        image.getBody().setLayout( FormLayoutFactory.defaults().create() );
//        Label imageLbl = tk.createFlowText( image.getBody(), "![Portrait](images/ulli-250.jpg)" );
////        imageLbl.setLayoutData( FormDataFactory.filled().clearRight().width( 250 ).height( 465 ).create() );
        
        //
        for (ContentObject co : cp.listContent( "frontpage" )) {
            if (!co.title().startsWith( "willkommen" )) {
                IPanelSection section = tk.createPanelSection( parent, co.title() );
                section.addConstraint( new PriorityConstraint( 0 ) );
                tk.createFlowText( section.getBody(), co.content() + "</br>" );
            }
        }
    }

}
