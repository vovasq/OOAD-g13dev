package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.views;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.IcrashEnvironment;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.environment.actors.ActAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.IcrashSystem;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.CtAdministrator;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtLogin;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary.DtPassword;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.AdminActors;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.utils.Log4JUtils;

import com.vaadin.ui.FormLayout;

import java.io.Serializable;

import org.apache.log4j.Logger;


public class AdminSmsLoginView extends HorizontalLayout 
implements View, Button.ClickListener, Serializable {
	private static final long serialVersionUID = -3317915013312630958L;

	IcrashSystem sys = IcrashSystem.getInstance();
	IcrashEnvironment env = IcrashEnvironment.getInstance();
	
	ActAdministrator actAdmin = env.getActAdministrator(new DtLogin(new PtString(AdminActors.values[0].name())));
	CtAdministrator ctAdmin =  (CtAdministrator) sys.getCtAuthenticated(actAdmin);
	
	private Label welcomeText;
	private PasswordField smsCode;
	private Button loginButton;
	transient Logger log = Log4JUtils.getInstance().getLogger();
	public AdminSmsLoginView(){
		actAdmin.setActorUI(UI.getCurrent());
		env.setActAdministrator(actAdmin.getName(), actAdmin);
		IcrashSystem.assCtAuthenticatedActAuthenticated.replace(ctAdmin, actAdmin);
		
		log.debug("CHECK: ADMIN UI is "+actAdmin.getActorUI());
		
		welcomeText = new Label("Please input code from sms to enter iCrash Administrator Console");
		
		// create the username input field
		
		// create the password input field
		smsCode = new PasswordField("Sms code:");
		smsCode.setWidth("120px");
		smsCode.setValue("");
		smsCode.setNullRepresentation("");
		smsCode.setImmediate(true);
					
		// create the login button
		loginButton = new Button("Enter", this);
		loginButton.setClickShortcut(KeyCode.ENTER);
		loginButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		loginButton.setImmediate(true);
				
		
		
		/*********************************************************************************************/
		Table adminMessagesTable = new Table();
		adminMessagesTable.setContainerDataSource(actAdmin.getMessagesDataSource());
		adminMessagesTable.setCaption("Administrator messages");
		//	adminMessagesTable.setVisibleColumns("inputEvent", "message");
		/*********************************************************************************************/
		
		FormLayout FL = new FormLayout(smsCode, loginButton);
		
		VerticalLayout welcomeLayout = new VerticalLayout(welcomeText, /*hintText,*/ FL);
		VerticalLayout leftVL = new VerticalLayout(welcomeLayout); 
		VerticalLayout rightVL = new VerticalLayout(adminMessagesTable);
			
		welcomeLayout.setComponentAlignment(welcomeText, Alignment.MIDDLE_CENTER);
		 // welcomeLayout.setComponentAlignment(hintText, Alignment.MIDDLE_CENTER);
		welcomeLayout.setComponentAlignment(FL, Alignment.MIDDLE_CENTER);
		
		setSizeFull();
		FL.setSizeUndefined();
		welcomeLayout.setSizeUndefined();
		leftVL.setSizeFull();
		rightVL.setSizeFull();
		
		leftVL.setComponentAlignment(welcomeLayout, Alignment.MIDDLE_CENTER);
		rightVL.setComponentAlignment(adminMessagesTable, Alignment.MIDDLE_CENTER);
		
		addComponent(leftVL);
		addComponent(rightVL);
		
		setMargin(true);
		setExpandRatio(leftVL, 6);
		setExpandRatio(rightVL, 4);
	}
	
	// didnt understand where does it use 
	@Override
	public void enter(ViewChangeEvent event) {	
//		username.setValue("");
		smsCode.setValue(null);
//		username.focus();
	}
	
	@Override
	public void buttonClick(ClickEvent event) {	
		ActAdministrator admin = env.getActAdministrator(new DtLogin(new PtString(AdminActors.values[0].name())));
		try {
			actAdmin.setActorUI(UI.getCurrent());
			
				admin.oeLoginBySms(new DtString( new PtString(smsCode.getValue())));
				
		} catch (Exception e) {
			e.printStackTrace();
			log.info("popali blin");
		}
				
		// refreshing this view forces redirection of the user
		// to the view, where he should go now, be it AdminLoginView or AdminAuthView
		Page.getCurrent().reload();
	}

	
}
