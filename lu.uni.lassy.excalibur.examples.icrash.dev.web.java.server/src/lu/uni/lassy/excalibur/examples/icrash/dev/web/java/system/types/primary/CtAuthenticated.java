/*******************************************************************************
 * Copyright (c) 2014 University of Luxembourg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Alfredo Capozucca - initial API and implementation
 ******************************************************************************/
package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary;

import java.io.Serializable;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtInteger;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.DtString;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtInteger;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;

public abstract class CtAuthenticated implements Serializable {

	private static final long serialVersionUID = 4554155096163180856L;
	
	public DtLogin login;
	public DtPassword pwd;
	public PtBoolean vpIsLogged;
	public PtBoolean isPassAndLoginRight;
	public DtString code;
	// here we add a new field contains current sms code
	
	public PtBoolean init(DtLogin aLogin, DtPassword aPwd){
			
			login = aLogin;
			pwd = aPwd;
			vpIsLogged = new PtBoolean(false); 
			isPassAndLoginRight = new PtBoolean(false);
			code = new DtString(new PtString("savov 123"));// it is generated code
			return new PtBoolean(true); 			
	}
}
