package edu.asu.plp.tool.prototype.devices;

import edu.asu.plp.tool.backend.isa.events.IOEvent;
import plptool.Constants;

/**
 * This class implements the Switch device of PLP.
 * It has only one memory mapped address.
 * Based on which button/switch pressed by the user
 * that binary value will be stored in this address
 * @author Harsha
 *
 */
public class Switches extends PLPToolIOMemoryModule
{
	/**
	 * This is the constructor of Switches class
	 * It takes the memory address mapped to this device
	 * @param addr memory address mapped to this address
	 */
	public Switches(long addr)
	{
		super(addr, addr, true);
		//writeRegister(addr, 0, false);
	}
	
	/**
	 * we are not doing any specific activity on this device
	 * every clock cycle.
	 * @return success
	 */
	@Override
	public int eval() {
		
		return Constants.PLP_OK;
	}

	/**
	 * As of now nothing is done in this function
	 */
	@Override
	public void reset() {
		// TODO:Nothing to reset here. Or should the value in the address should be initialized to 0?
		
	}
	
	/**
	 * Switch is readonly. This function when called will throw an error/exception.
	 * @return should return an error condition.
	 */
	@Override
	public int write(long addr, long data, boolean isInstruction)
	{
		//TODO: throw an error as we cannot write to switch address
		return 0;
	}

	/**
	 * returns a string describing what is this device
	 * @return a string about PLP Switch
	 */
	@Override
	public String introduce() {
		return "PLP Switches";
	}
	
	@Override
	public synchronized void enable() {
		super.enable();
		writeRegister(startAddress(), (long)0, false);
		
	}

	@Override
	public void addListener(IOEvent toAdd) {
		// TODO Auto-generated method stub
		
	}
	
	

}
