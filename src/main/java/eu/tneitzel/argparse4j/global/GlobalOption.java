package eu.tneitzel.argparse4j.global;

import java.util.Properties;

import eu.tneitzel.argparse4j.inf.ArgumentParser;
import eu.tneitzel.argparse4j.inf.Namespace;
import eu.tneitzel.argparse4j.inf.SubparserContainer;
import eu.tneitzel.argparse4j.inf.Subparsers;

/**
 * The GlobalOption class contains methods to add and assign global options from and to an
 * argument parser. Global options allow the developer to configure options globally within
 * an enum and to retrieve the user specified values from anywhere within the program. This
 * form of option access is generally not recommended, as pure functions should usually be
 * preferred. However, for personal tooling and quick development, global option access can
 * be very handy.
 *
 * @author Tobias Neitzel (@qtc_de)
 */
public class GlobalOption
{
    /**
     * Take an argparse4j namespace and assign the contained options to the specified list of
     * IOption instances. Optionally, a Properties object can be provided that is used to set
     * default values.
     *
     * @param args argparse4j namespace that contains the parsed command line
     * @param options options to assign from the command line
     * @param config Properties object that holds default values
     */
    public static void parseOptions(Namespace args, Properties config, IOption... options)
    {
        if (config == null)
        {
            config = new Properties();
        }

        for (IOption option : options)
        {
            String configValue = config.getProperty(option.getPlainName());

            if (configValue != null && !configValue.isEmpty())
            {
                Class<?> optionType = option.getType();

                if (optionType == Integer.class)
                {
                    option.setValue(args, Integer.valueOf(configValue));
                }

                else if (optionType == Long.class)
                {
                    option.setValue(args, Long.valueOf(configValue));
                }

                else if (optionType == Boolean.class)
                {
                    option.setValue(args, Boolean.valueOf(configValue));
                }

                else
                {
                    option.setValue(args, configValue);
                }
            }

            else
            {
                option.setValue(args, null);
            }
        }
    }

    /**
     * Add all options from an action to an argument parser.
     *
     * @param parser the parser to add the options to
     * @param action the action to obtain the options from
     */
    public static void addOptions(ArgumentParser parser, IAction action)
    {
        for (IOption option : action.getOptions())
        {
            option.addOption(parser, action);
        }
    }

    /**
     * Add a list of IAction objects to an Subparsers container. This function creates a new
     * parser entry for each action and respects eventually configured SubparserGroups.
     *
     * @param container Subparsers container to create new action entries in
     * @param actions IAction objects to include into the Subparsers container
     */
    public static void addActions(Subparsers container, IAction... actions)
    {
        for (IAction subAction : actions)
        {
            IActionGroup actionGroup = subAction.getGroup();
            SubparserContainer parserGroup = container;

            if (actionGroup != null)
            {
                parserGroup = container.getOrCreateSubparserGroup(actionGroup.getName());
            }

            subAction.addSuparser(parserGroup);
        }
    }
}
