package eu.tneitzel.argparse4j.internal;

import java.util.Locale;
import java.util.ResourceBundle;

import eu.tneitzel.argparse4j.helper.PrefixPattern;
import eu.tneitzel.argparse4j.helper.TextWidthCounter;
import eu.tneitzel.argparse4j.inf.ArgumentParserConfiguration;

public class ArgumentParserConfigurationImpl
        implements ArgumentParserConfiguration {
    final String prog_;
    final boolean addHelp_;
    final String prefixChars_;
    final PrefixPattern prefixPattern_;
    private final String fromFilePrefix_;
    final PrefixPattern fromFilePrefixPattern_;
    private final ResourceBundle resourceBundle_;
    final TextWidthCounter textWidthCounter_;
    final int formatWidth_;
    final boolean singleMetavar_;
    final boolean noDestConversionForPositionalArgs_;
    final boolean includeArgumentNamesAsKeysInResult_;
    final boolean mustHelpTextIncludeMutualExclusivity_;

    public ArgumentParserConfigurationImpl(String prog, boolean addHelp,
            String prefixChars, String fromFilePrefix, Locale locale,
            TextWidthCounter textWidthCounter, int formatWidth,
            boolean singleMetavar, boolean noDestConversionForPositionalArgs,
            boolean includeArgumentNamesAsKeysInResult,
            boolean mustHelpTextIncludeMutualExclusivity) {
        prog_ = prog;
        addHelp_ = addHelp;
        prefixChars_ = prefixChars;
        prefixPattern_ = new PrefixPattern(prefixChars);
        fromFilePrefix_ = fromFilePrefix;
        fromFilePrefixPattern_ = fromFilePrefix == null ? null : new PrefixPattern(
                fromFilePrefix);
        resourceBundle_ = ResourceBundle
                .getBundle(ArgumentParserImpl.class.getName(), locale);
        textWidthCounter_ = textWidthCounter;
        formatWidth_ = formatWidth;
        singleMetavar_ = singleMetavar;
        noDestConversionForPositionalArgs_ = noDestConversionForPositionalArgs;
        includeArgumentNamesAsKeysInResult_ = includeArgumentNamesAsKeysInResult;
        mustHelpTextIncludeMutualExclusivity_ = mustHelpTextIncludeMutualExclusivity;
    }

    private ArgumentParserConfigurationImpl(String prog, boolean addHelp,
            String prefixChars, String fromFilePrefix,
            ResourceBundle resourceBundle, TextWidthCounter textWidthCounter,
            int formatWidth, boolean singleMetavar,
            boolean noDestConversionForPositionalArgs,
            boolean includeArgumentNamesAsKeysInResult,
            boolean mustHelpTextIncludeMutualExclusivity) {
        prog_ = prog;
        addHelp_ = addHelp;
        prefixChars_ = prefixChars;
        prefixPattern_ = new PrefixPattern(prefixChars);
        fromFilePrefix_ = fromFilePrefix;
        fromFilePrefixPattern_ = fromFilePrefix == null ? null : new PrefixPattern(
                fromFilePrefix);
        resourceBundle_ = resourceBundle;
        textWidthCounter_ = textWidthCounter;
        formatWidth_ = formatWidth;
        singleMetavar_ = singleMetavar;
        noDestConversionForPositionalArgs_ = noDestConversionForPositionalArgs;
        includeArgumentNamesAsKeysInResult_ = includeArgumentNamesAsKeysInResult;
        mustHelpTextIncludeMutualExclusivity_ = mustHelpTextIncludeMutualExclusivity;
    }

    ArgumentParserConfigurationImpl forSubparser(boolean addHelp,
            String prefixChars) {
        return new ArgumentParserConfigurationImpl(prog_, addHelp, prefixChars,
                fromFilePrefix_, resourceBundle_, textWidthCounter_,
                formatWidth_, singleMetavar_,
                noDestConversionForPositionalArgs_,
                includeArgumentNamesAsKeysInResult_,
                mustHelpTextIncludeMutualExclusivity_);
    }

    public ResourceBundle getResourceBundle() {
        return resourceBundle_;
    }

    @Override
    public Locale getLocale() {
        return resourceBundle_.getLocale();
    }
}
