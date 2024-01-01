package eu.tneitzel.argparse4j.global.exceptions;

import eu.tneitzel.argparse4j.global.IOption;

/**
 * RequirementFailedException is thrown if a require function was used on
 * an option and the requirement failed.
 */
public class RequirementException extends Exception
{
    protected final IOption[] requiredOptions;
    private static final long serialVersionUID = 1586330823737832680L;

    /**
     * Initialize the exception with the list of required options.
     *
     * @param options list of required options
     */
    public RequirementException(IOption... options)
    {
        requiredOptions = options;
    }

    /**
     * Get the list of required options.
     *
     * @return list of required options.
     */
    public IOption[] getRequiredOptions()
    {
        return requiredOptions;
    }
}
