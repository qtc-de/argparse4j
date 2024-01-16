package eu.tneitzel.argparse4j.global;

import eu.tneitzel.argparse4j.inf.ArgumentParser;
import eu.tneitzel.argparse4j.inf.Subparsers;

/**
 * The ActionContext class is used to store meta information associated to IAction
 * implementors. Since IAction is an interface, it cannot have properties not can it
 * enforce implementors to implement static methods. Things like the meta variable
 * or destination of an IAction within an argument parser can therefore not be stored
 * within IAction itself. Instead, such information should be stored in an ActionContext.
 * From this context, new Subparsers can then easily be generated.
 *
 * @author Tobias Neitzel (@qtc_de)
 */
public class ActionContext
{
    private final String dest;
    private final String metaVar;
    private final String helpText;

    private final IAction[] actions;

    /**
     * Create a new ActionContext by specifying it's destination in an argument parser and the
     * associated IAction objects.
     *
     * @param dest destination within the argument parser
     * @param actions associated actions
     */
    public ActionContext(String dest, IAction[] actions)
    {
        this(dest, "action", " ", actions);
    }

    /**
     * Create a new ActionContext by specifying it's destination in an argument parser, it's meta variable,
     * it's help text and the associated IAction objects.
     *
     * @param dest destination within the argument parser
     * @param metaVar the associated meta variable
     * @param helpText the associated help text
     * @param actions associated actions
     */
    public ActionContext(String dest, String metaVar, String helpText, IAction[] actions)
    {
        this.dest = dest;
        this.metaVar = metaVar;
        this.helpText = helpText;
        this.actions = actions;
    }

    /**
     * Return the destination within the argument parser.
     *
     * @return destination within the argument parser.
     */
    public String getDest()
    {
        return dest;
    }

    /**
     * Return the meta variable within the argument parser.
     *
     * @return meta variable within the argument parser.
     */
    public String getMetaVar()
    {
        return metaVar;
    }

    /**
     * Return the help text within the argument parser.
     *
     * @return help text within the argument parser.
     */
    public String getHelpText()
    {
        return helpText;
    }

    /**
     * Return the associated IAction objects.
     *
     * @return associated IAction objects
     */
    public IAction[] getActions()
    {
        return actions;
    }

    /**
     * Add new Subparsers based on the properties of the ActionContext.
     *
     * @param parser ArgumentParser to add the Subparsers to
     * @return newly created Subparsers
     */
    public Subparsers addSubparsers(ArgumentParser parser)
    {
        Subparsers nestedParser = parser.addSubparsers().help(getHelpText()).metavar(getMetaVar()).dest(getDest());

        for (IAction subAction : getActions())
        {
            subAction.addSuparser(nestedParser);
        }

        return nestedParser;
    }
}
