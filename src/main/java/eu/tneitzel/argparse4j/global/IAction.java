package eu.tneitzel.argparse4j.global;

import eu.tneitzel.argparse4j.inf.Subparser;
import eu.tneitzel.argparse4j.inf.SubparserContainer;
import eu.tneitzel.argparse4j.inf.Subparsers;

/**
 * IAction represents a command line subcommand. IActions can be used to generate new subparsers
 * and to assign options to them.
 *
 * @author Tobias Neitzel (@qtc_de)
 */
public interface IAction
{
    /**
     * Get the name of the action.
     *
     * @return name of the action
     */
    public String getName();

    /**
     * Get the description of the action.
     *
     * @return the description of the action
     */
    public String getDescription();

    /**
     * Get all options associated with an action.
     *
     * @return options associated with an action
     */
    public IOption[] getOptions();

    /**
     * Actions used within the same parser can be grouped as it is the case
     * for options. If an action is supposed to be grouped, it should return
     * the corresponding IActionGroup from this function.
     *
     * @return IActionGroup for the action
     */
    default IActionGroup getGroup()
    {
        return null;
    }

    /**
     * Get nested actions that should be available when using this action.
     * The actual actions are returned within an ActionContext. This is required
     * to configure things like the associated meta variable or destination within
     * the argument parser.
     *
     * @return ActionContext containing the nested actions
     */
    default ActionContext getSubActions()
    {
        return null;
    }

    /**
     * Add a new subparser for the action and assign all options associated with the
     * action to the new subparser. If the action has an IActionGroup assigned, this
     * group is created within the subparsers container.
     *
     * @param subparsers the subparsers container to add the new subparser to
     */
    default void addSuparser(Subparsers subparsers)
    {
         IActionGroup actionGroup = getGroup();
         SubparserContainer parserGroup = subparsers;

         if (actionGroup != null)
         {
             parserGroup = subparsers.getOrCreateSubparserGroup(actionGroup.getName());
         }

        Subparser parser = parserGroup.addParser(getName()).help(getDescription());
        GlobalOption.addOptions(parser, this);

        ActionContext ctx = getSubActions();

        if (ctx != null)
        {
            ctx.addSubparsers(parser);
        }
    }
}
