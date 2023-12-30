package eu.tneitzel.argparse4j.global;

import eu.tneitzel.argparse4j.inf.ArgumentGroup;
import eu.tneitzel.argparse4j.inf.ArgumentParser;

/**
 * An IOptionGroup represents an argument group in the context of argparse4j. It basically
 * only consists out of a group name, but it needs to support an addGroup method. The addGroup
 * method is responsible for adding ArgumentGroups to a parser, or returning groups that have
 * already been added. ArgumentGroups with same name can be present for different actions.
 * Therefore, a new group needs to be created for each action.
 * 
 * @author Tobias Neitzel (@qtc_de)
 */
public interface IOptionGroup
{
	public String getName();
	public ArgumentGroup getGroup(ArgumentParser parser, IAction action);
}
