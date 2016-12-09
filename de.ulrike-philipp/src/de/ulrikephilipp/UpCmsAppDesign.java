/* 
 * Copyright (C) 2014-2016, Falko Bräutigam. All rights reserved.
 */
package de.ulrikephilipp;

import static org.polymap.core.ui.FormDataFactory.on;
import static org.polymap.core.ui.UIUtils.setVariant;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import org.polymap.core.ui.FormDataFactory;
import org.polymap.core.ui.FormLayoutFactory;
import org.polymap.core.ui.UIUtils;

import org.polymap.rhei.batik.BatikApplication;
import org.polymap.rhei.batik.IAppContext;
import org.polymap.rhei.batik.IPanel;
import org.polymap.rhei.batik.PanelPath;
import org.polymap.rhei.batik.toolkit.md.MdAppDesign;

/**
 * 
 *
 * @author <a href="http://www.polymap.de">Falko Bräutigam</a>
 */
public class UpCmsAppDesign
        extends MdAppDesign {

    private static Log log = LogFactory.getLog( UpCmsAppDesign.class );

    public static final int         MAX_CONTENT_WIDTH = 820;
    
    
    @Override
    public Shell createMainWindow( @SuppressWarnings("hiding") Display display ) {
        super.createMainWindow( display );
        
//        // XXX
//        UIUtils.activateCallback( "cms-fix-links" );
        
        return mainWindow;
    }

    
    @Override
    protected void updateMainWindowLayout() {
        Rectangle displayArea = Display.getCurrent().getBounds();

        int marginsWidth = Math.max( (displayArea.width - MAX_CONTENT_WIDTH)/2, 10 );
        int width = displayArea.width - (marginsWidth*2);
        int spacing = (int)(width * 0.04);
        log.info( "adaptLayout(): display width=" + displayArea.width + " -> spacing=" + spacing );
        
        // panel layout
        panelLayoutSettings.spacing = spacing;
        
        // app layout
        appLayoutSettings.spacing = spacing;
        appLayoutSettings.marginLeft = appLayoutSettings.marginRight = marginsWidth;
        
        mainWindow.setLayout( FormLayoutFactory.defaults().margins( 
                appLayoutSettings.marginTop, appLayoutSettings.marginRight, 
                appLayoutSettings.marginBottom, appLayoutSettings.marginLeft ).create() );
        
        mainWindow.layout( true );
    }


    @Override
    protected void createPanelDecoration( IPanel panel, Composite head ) {
        super.createPanelDecoration( panel, head );

        head.setLayout( FormLayoutFactory.defaults().margins( 0 ).spacing( 5 ).create() );

        // closeBtn
        Control closeBtn = head.getChildren()[0];
        if (closeBtn instanceof Button) {
            ((Button)closeBtn).setText( "< Zurück" );
            ((Button)closeBtn).setImage( null );
            closeBtn.setLayoutData( FormDataFactory.filled().clearRight().create() );
        }
    }


    @Override
    protected Button createSwitcherButton( Composite switcher, IPanel panel ) {
        int btnCount = switcher.getChildren().length;

        Button result = super.createSwitcherButton( switcher, panel );
        
        // set width to avoid layout problems
        result.setLayoutData( btnCount == 0
                ? FormDataFactory.filled().width( 75 ).clearRight().create()
                : FormDataFactory.filled().width( 75 ).clearRight().left( switcher.getChildren()[btnCount-1] ).create() );

        return result;
    }


    @Override
    protected Composite fillHeaderArea( Composite parent ) {
        Composite result = new Composite( parent, SWT.NO_FOCUS );
        UIUtils.setVariant( result, "up-header" );
        result.setLayout( FormLayoutFactory.defaults().margins( 12, 0, 0, 0 ).create() );
        
        Button l = on( setVariant( new Button( result, SWT.PUSH ), "up-header" ) ).fill().noRight().control();
        l.setText( "ULRIKE PHILIPP" );
        l.setToolTipText( "Zurück zum Start" );
        l.addSelectionListener( new SelectionAdapter() {
            @Override
            public void widgetSelected( SelectionEvent ev ) {
                IAppContext context = BatikApplication.instance().getContext();
                context.openPanel( PanelPath.ROOT, StartPanel.ID );
            }
        });
        
//        Label tel = on( new Label( result, SWT.NONE ) ).fill().noTop().noLeft().control();
//        tel.setText( "(0179-731 31 56, 0341-228 75 37) " );
        return result;
    }

}
