package lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.secondary;

import java.io.Serializable;

import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.system.types.design.JIntIs;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtBoolean;
import lu.uni.lassy.excalibur.examples.icrash.dev.web.java.types.stdlib.PtString;

/**
 * A datatype class that stores SMS messages that have been sent to humans to notify about the status of their crisis. 
 */
public class DtSMSHumanNotify implements Serializable, JIntIs {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 227L;

	/**  The message of the SMS sent out. */
	public PtString value;
	
	/** The maximum length a SMS could be. */
	private int _maxLength = 160;
	
	/**
	 * Instantiates a new datatype SMS with the message provided.
	 *
	 * @param s The primitive type of string to put into the datatype
	 */
	public DtSMSHumanNotify(PtString s){
		value = s;
	}
	
	public PtBoolean is(){
		return new PtBoolean(this.value.getValue().length() <= _maxLength);
	}

	@Override
	public PtString getExpectedDataStructure() {
		return new PtString("Expected structure of the SMS is to have a maximum length of " + _maxLength);
	}
	
	
}
