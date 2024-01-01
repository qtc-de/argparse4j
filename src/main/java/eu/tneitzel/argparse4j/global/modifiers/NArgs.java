package eu.tneitzel.argparse4j.global.modifiers;

import eu.tneitzel.argparse4j.inf.Argument;

/**
 * NArgs is used to indicate the argument count.
 *
 * @author Tobias Neitzel (@qtc_de)
 */
public class NArgs implements IArgumentModifier
{
    private final String nargs;

    /**
     * Configure the argument cound modifier.
     *
     * @param nargs the argument cound modifier
     */
    public NArgs(String nargs)
    {
        this.nargs = nargs;
    }

    public void apply(Argument arg)
    {
        arg.nargs(nargs);
    }
}
