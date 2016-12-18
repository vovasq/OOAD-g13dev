package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.views;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.vaadin.addon.touchkit.ui.NavigationView;
import com.vaadin.addon.touchkit.ui.VerticalComponentGroup;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.IcrashEnvironment;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.actors.ActCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.CtCoordinator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtCoordinatorID;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtPassword;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;

public class CoordSmsLoginView extends NavigationView implements View, Serializable {
	private static final long serialVersionUID = 1866773510048507541L;
	transient Logger log = Log4JUtils.getInstance().getLogger();
	
	IcrashSystem sys = IcrashSystem.getInstance();
	IcrashEnvironment env = IcrashEnvironment.getInstance();

	PasswordField smsCode = new PasswordField("Sms Code");
	Button loginBtn = new Button("Enter");
	
	public CoordSmsLoginView(String CoordID) {
		
		CtCoordinator ctCoordinator = (CtCoordinator) sys.getCtCoordinator(new DtCoordinatorID(new PtString(CoordID)));
		ActCoordinator actCoordinator = env.getActCoordinator(ctCoordinator.login);
		
		actCoordinator.setActorUI(UI.getCurrent());
		
		env.setActCoordinator(actCoordinator.getName(), actCoordinator);
		
		IcrashSystem.assCtAuthenticatedActAuthenticated.replace(ctCoordinator, actCoordinator);
		IcrashSystem.assCtCoordinatorActCoordinator.replace(ctCoordinator, actCoordinator);
		
		setCaption("Login to Coord " + ctCoordinator.login.toString());
		
		VerticalLayout layout = new VerticalLayout();
		setContent(layout);
		
		VerticalComponentGroup group = new VerticalComponentGroup();
		layout.addComponent(group);
		
		VerticalLayout loginExtLayout = new VerticalLayout();
		loginExtLayout.setSizeFull();
		group.addComponent(loginExtLayout);

		FormLayout loginIntLayout = new FormLayout();
		loginIntLayout.setSizeUndefined();
		loginExtLayout.addComponent(loginIntLayout);
		
		loginExtLayout.setComponentAlignment(loginIntLayout, Alignment.MIDDLE_CENTER);
		
		smsCode.setValue("");
		smsCode.setNullRepresentation("");
		
		loginBtn.setClickShortcut(KeyCode.ENTER);
		
		loginIntLayout.addComponents(smsCode, loginBtn);
		
		///////////////////////////////////////////////////////////////////////////////////
		
		Grid messagesTable = new Grid();
		messagesTable.setContainerDataSource(actCoordinator.getMessagesDataSource());
		messagesTable.setSizeUndefined();
		
		VerticalLayout tableLayout = new VerticalLayout();
		tableLayout.setSizeFull();
		
		group.addComponent(tableLayout);
		tableLayout.addComponent(messagesTable);
		
		tableLayout.setComponentAlignment(messagesTable, Alignment.MIDDLE_CENTER);
		
		///////////////////////////////////////////////////////////////////////////////////
		
		loginBtn.addClickListener(event -> {
			try {
				log.info("we are already in coordsmsLoginView");
				actCoordinator.oeLoginBySms(new DtString( new PtString(smsCode.getValue())));
				
					
			} catch (Exception e) {
				e.printStackTrace();
			}
				
			// refreshing this view forces redirection of the user
			// to the view, where he should go now, be it CoordLoginView or CoordAuthView
			   Page.getCurrent().reload();
			   
			   actCoordinator.setActorUI(UI.getCurrent());
			   env.setActCoordinator(actCoordinator.getName(), actCoordinator);
				
			   IcrashSystem.assCtAuthenticatedActAuthenticated.replace(ctCoordinator, actCoordinator);
			   IcrashSystem.assCtCoordinatorActCoordinator.replace(ctCoordinator, actCoordinator);
		});
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		
		smsCode.setValue(null);
		
	}
}
