package eu.tneitzel.argparse4j.global.modifiers;

import eu.tneitzel.argparse4j.inf.Argument;

/**
 * Type is used to indicate the underlying option type.
 * 
 * @author Tobias Neitzel (@qtc_de)
 */
public class Type implements IArgumentModifier
{
	private final Class<?> cls;
	
	/**
	 * Configure the underlying option type.
	 * 
	 * @param cls the underlying option type
	 */
	public Type(Class<?> cls)
	{
		this.cls = cls;
	}
	
	/**
	 * Return the underlying option type.
	 * 
	 * @return the underlying option type
	 */
	public Class<?> getCls()
	{
		return this.cls;
	}
	
	public void apply(Argument arg)
	{
		arg.type(cls);
	}
}