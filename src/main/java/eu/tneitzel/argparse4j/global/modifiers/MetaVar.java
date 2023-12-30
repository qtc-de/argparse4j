package eu.tneitzel.argparse4j.global.modifiers;

import eu.tneitzel.argparse4j.inf.Argument;

/**
 * MetaVar is used to configure meta variables displayed by argparse4j.
 * 
 * @author Tobias Neitzel (@qtc_de)
 */
public class MetaVar implements IArgumentModifier
{
	private final String name;
	
	/**
	 * Configure the meta variable.
	 * 
	 * @param name the name of the meta variable
	 */
	public MetaVar(String name)
	{
		this.name = name;
	}
	
	public void apply(Argument arg)
	{
		arg.metavar(name);
	}
}
