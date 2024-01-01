package eu.tneitzel.argparse4j.global.exceptions;

import eu.tneitzel.argparse4j.global.IOption;

/**
 * Exception that is thrown if missing arguments are encountered in an
 * requireOneOf call.
 */
public class RequirementOneOfException extends RequirementException
{
    public RequirementOneOfException(IOption[] options)
    {
        super(options);
    }

    private static final long serialVersionUID = 796880845020916443L;
}
