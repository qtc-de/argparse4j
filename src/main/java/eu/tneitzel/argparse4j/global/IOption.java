package eu.tneitzel.argparse4j.global;

import eu.tneitzel.argparse4j.global.exceptions.RequirementAllOfException;
import eu.tneitzel.argparse4j.global.exceptions.RequirementException;
import eu.tneitzel.argparse4j.global.exceptions.RequirementOneOfException;
import eu.tneitzel.argparse4j.global.modifiers.IArgumentModifier;
import eu.tneitzel.argparse4j.global.modifiers.Type;
import eu.tneitzel.argparse4j.inf.Argument;
import eu.tneitzel.argparse4j.inf.ArgumentAction;
import eu.tneitzel.argparse4j.inf.ArgumentGroup;
import eu.tneitzel.argparse4j.inf.ArgumentParser;
import eu.tneitzel.argparse4j.inf.Namespace;

/**
 * IOption is the interface that should be implemented by enums that contain global options.
 * The methods required to implement this interface are required to configure the options with
 * argparse4j. Moreover, this interface contains a bunch of default implementations that are made
 * up of the methods that need to be implemented manually.
 *
 * @author Tobias Neitzel (@qtc_de)
 */
public interface IOption
{
    public String getName();
    public String getDescription();
    public ArgumentAction getArgumentAction();
    public IArgumentModifier[] getArgumentModifiers();

    public <T> void setValue(T value);
    public <T> T getValue();
    public IOptionGroup getGroup();

    /**
     * Return true if the value associated with the option is null. This usually indicates that
     * the corresponding option was not used on the command line.
     *
     * @return true if the value of the option is null
     */
    default boolean isNull()
    {
        if (this.getValue() == null)
        {
            return true;
        }

        return false;
    }

    /**
     * Return true if the value associated with the option is not null. This usually indicates that
     * the corresponding option was used on the command line.
     *
     * @return true if the value of the option is not null
     */
    default boolean notNull()
    {
        return !this.isNull();
    }

    /**
     * Check whether an boolean option is true or false.
     *
     * @return true if boolean option is true
     */
    default boolean getBool()
    {
        return (boolean)getValue();
    }

    /**
     * Set the option value to the specified value or to the specified default, if the value is null.
     *
     * @param <T> the type of the value
     * @param value the specified value
     * @param def the specified default
     */
    default <T> void setValue(T value, T def)
    {
        if (value != null)
        {
            this.setValue(value);
        }

        else
        {
            this.setValue(def);
        }
    }

    /**
     * Set an option value according to the user specified command line.
     *
     * @param <T> the type of the value
     * @param args the user specified command line
     * @param def the specified default
     */
    default <T> void setValue(Namespace args, T def)
    {
        T value = args.get(this.getName().replaceFirst("--", "").replace("-", "_"));
        this.setValue(value, def);
    }

    /**
     * The type of an IOption can be represented by an IArgumentModifier. The corresponding modifier
     * is named Type and can be used to indicate the class of the option. This function iterates over
     * all modifiers and returns the value of the Type modifier if present. If not, it returns null
     *
     * @return the class that is associated with the option (can be null)
     */
    default Class<?> getType()
    {
        for (IArgumentModifier modifier : this.getArgumentModifiers())
        {
            if (modifier instanceof Type)
            {
                Type typeModifier = (Type)modifier;
                return typeModifier.getCls();
            }
        }

        return null;
    }

    /**
     * Add the option to an argument parser.
     *
     * @param parser the parser to add the option to
     * @param action the action that is associated with the option (can be null)
     */
    default void addOption(ArgumentParser parser, IAction action)
    {
        Argument arg = null;
        IOptionGroup optionGroup = this.getGroup();

        if (optionGroup == null)
        {
            arg = parser.addArgument(getName()).help(getDescription()).action(getArgumentAction());
        }

        else
        {
            ArgumentGroup group = parser.getOrCreateArgumentGroup(optionGroup.getName());
            arg = group.addArgument(getName()).help(getDescription()).action(getArgumentAction());
        }

        addModifiers(arg);
    }

    /**
     * IOption can have associated IArgumentModifiers that modify different properties of the option
     * in the context of argparse4j (e.g. nargs, choices, argument type, etc.). This function applies
     * the configured modifiers to an argument. This function is called within the addOption function
     * and should usually not be used manually.
     *
     * @param arg the argument to apply the modifiers to
     */
    default void addModifiers(Argument arg)
    {
        for (IArgumentModifier modifier : this.getArgumentModifiers())
        {
            modifier.apply(arg);
        }
    }

    /**
     * Require an option at runtime. If the corresponding option was not set, an RequirementFailedException
     * is thrown.
     *
     * @param <T> type of the option
     * @return the value that is assigned to the option (if any)
     * @throws RequirementFailedException is raised if no value was set for this option
     */
    default <T> T require() throws RequirementException
    {
        if (isNull())
        {
            throw new RequirementException(this);
        }

        return getValue();
    }

    /**
     * Require an option or one of the other specified options at runtime. The function returns the
     * value of the first option that was set, or raises an RequirementFailedException if none of
     * the options was set.
     *
     * @param <T> type of the option
     * @return the value that is assigned to one of the option (if any)
     * @throws RequirementFailedException is raised if all options have no value assigned
     */
    static <T> T requireOneOf(IOption... options) throws RequirementException
    {
        for (IOption option : options)
        {
            if (option.notNull())
            {
                return option.getValue();
            }
        }

        throw new RequirementOneOfException(options);
    }

    /**
     * Require that each of the specified options has an value assigned. If this is not true, an
     * RequirementFailedException is thrown.
     *
     * @throws RequirementFailedException is raised if at least one option has no value assigned
     */
    static void requireAllOf(IOption... options) throws RequirementException
    {
        for (IOption option : options)
        {
            if (option.isNull())
            {
                throw new RequirementAllOfException(options);
            }
        }
    }
}
