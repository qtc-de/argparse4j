package eu.tneitzel.argparse4j.global;

/**
 * An IActionGroup represents an subparsers group in the context of argparse4j.
 *
 * @author Tobias Neitzel (@qtc_de)
 */
public interface IActionGroup
{
    /**
     * Get the name of the action group.
     *
     * @return name of the action group
     */
    public String getName();
}
