package eu.tneitzel.argparse4j.global.modifiers;

import eu.tneitzel.argparse4j.inf.Argument;

/**
 * The IArgumentModifier interface can be implemented by classes that configure
 * certain argparse4j modifications like e.g. nargs, choices or types.
 *
 * @author Tobias Neitzel (@qtc_de)
 */
public interface IArgumentModifier
{
    /**
     * Applies the modifier to an argument.
     *
     * @param arg the argument the modifier should be applied to
     */
    public void apply(Argument arg);
}
