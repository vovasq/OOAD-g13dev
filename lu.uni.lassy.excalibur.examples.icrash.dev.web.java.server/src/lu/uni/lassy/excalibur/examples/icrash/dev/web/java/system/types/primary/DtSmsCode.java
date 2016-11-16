package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.primary;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtInteger;

/*
 *  this data type implements an sms code which one 
 *  an actor gets before he logins to the system
 *	created by Vovas
 */
public class DtSmsCode {
	private PtInteger code;
	public DtSmsCode(PtInteger v){
		code = v;
	}
	public  PtInteger getCode(){
		return code;
	}
	// seems to be a bidlocode
	public PtBoolean isTheSame (PtInteger checkCode){

		return new PtBoolean(checkCode.getValue() == code.getValue());
	}

}
