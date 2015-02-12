/* 
 * Copyright (C) 2014-2015, Falko Bräutigam. All rights reserved.
 */
package de.ulrikephilipp;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import org.polymap.core.ui.FormLayoutFactory;
import org.polymap.core.ui.UIUtils;

import org.polymap.rhei.batik.BatikApplication;
import org.polymap.rhei.batik.IAppContext;
import org.polymap.rhei.batik.app.DefaultActionBar;
import org.polymap.rhei.batik.app.DefaultActionBar.PLACE;
import org.polymap.rhei.batik.app.DefaultAppDesign;
import org.polymap.rhei.batik.app.DefaultAppNavigator;
import org.polymap.rhei.batik.app.IAppDesign;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class UpCmsAppDesign
        extends DefaultAppDesign 
        implements IAppDesign {

    public static final int         MAX_CONTENT_WIDTH = 820;
    
    
    @Override
    public Shell createMainWindow( Display display ) {
        super.createMainWindow( display );
        
        Rectangle bounds = display.getBounds();
        int margins = Math.max( bounds.width - MAX_CONTENT_WIDTH, 0 );
        mainWindow.setLayout( FormLayoutFactory.defaults().margins( 0, margins/2, 10, margins/2 ).create() );

        // FIXME
        UIUtils.activateCallback( "cms-fix-links" );
        
        return mainWindow;
    }

    
    @Override
    protected Composite fillActionArea( Composite parent ) {
        IAppContext context = BatikApplication.instance().getAppManager().getContext();
        DefaultActionBar actionbar = new DefaultActionBar( context, toolkit );
        actionbar.add( navigator = new DefaultAppNavigator(), PLACE.PANEL_NAVI );
//        actionbar.add( statusManager = new StatusManager(), PLACE.STATUS );
        return actionbar.createContents( parent, SWT.NONE );
    }


    @Override
    protected Composite fillHeaderArea( Composite parent ) {
        Composite result = new Composite( parent, SWT.NO_FOCUS );
        UIUtils.setVariant( result, "up-header" );
        result.setLayout( FormLayoutFactory.defaults().margins( 28, 8 ).create() );
        Label l = new Label( result, SWT.NONE );
        UIUtils.setVariant( l, "up-header" );
        l.setText( "ULRIKE PHILIPP" );
        return result;
    }

}
