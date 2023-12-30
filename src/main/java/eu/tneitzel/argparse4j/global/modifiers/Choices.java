package eu.tneitzel.argparse4j.global.modifiers;

import eu.tneitzel.argparse4j.inf.Argument;

/**
 * Choices is used to configure choices for an argument.
 * 
 * @author Tobias Neitzel (@qtc_de)
 */
public class Choices implements IArgumentModifier
{
	private final String[] choices;
	
	/**
	 * Configure the available choices.
	 * 
	 * @param choices the available choices
	 */
	public Choices(String[] choices)
	{
		this.choices = choices;
	}
	
	public void apply(Argument arg)
	{
		arg.choices(choices);
	}
}