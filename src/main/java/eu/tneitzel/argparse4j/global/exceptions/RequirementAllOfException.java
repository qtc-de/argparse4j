package eu.tneitzel.argparse4j.global.exceptions;

import eu.tneitzel.argparse4j.global.IOption;

/**
 * Exception that is thrown if missing arguments are encountered in an
 * requireAllOf call.
 */
public class RequirementAllOfException extends RequirementException
{
    private static final long serialVersionUID = 796880845020916653L;

    public RequirementAllOfException(IOption[] options)
    {
        super(options);
    }
}
