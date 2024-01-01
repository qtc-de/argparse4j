package eu.tneitzel.argparse4j.global;

import eu.tneitzel.argparse4j.inf.Subparser;
import eu.tneitzel.argparse4j.inf.Subparsers;

/**
 * IAction represents a command line subcommand. IActions can be used to generate new subparsers
 * and to assign options to them.
 *
 * @author Tobias Neitzel (@qtc_de)
 */
public interface IAction
{
    public String getName();
    public String getDescription();
    public IOption[] getOptions();

    /**
     * Add a new subparser for the action and assign all options associated with the
     * action to the new subparser.
     *
     * @param argumentParser the parser to add the new subparser to
     */
    default void addSuparser(Subparsers argumentParser)
    {
        Subparser parser = argumentParser.addParser(getName()).help(getDescription());
        GlobalOption.addOptions(parser, this, getOptions());
    }
}
