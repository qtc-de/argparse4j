package eu.tneitzel.argparse4j.helper;

import static eu.tneitzel.argparse4j.helper.TypeNameLocalization.localizeTypeNameIfPossible;
import static org.junit.Assert.assertEquals;

import java.util.Locale;

import eu.tneitzel.argparse4j.ArgumentParsers;
import eu.tneitzel.argparse4j.inf.ArgumentParser;

import org.junit.Test;

public class TypeNameLocalizationTest {
    @Test
    public void displayNameOfStandardTypeIsLocalized() {
        ArgumentParser parser = ArgumentParsers.newFor("prog").locale(Locale.US)
                .build();

        String displayName = localizeTypeNameIfPossible(parser, Integer.class);

        assertEquals("integer (32 bits)", displayName);
    }

    @Test
    public void displayNameOfCustomTypeWithoutResourceBundleIsSimpleClassName() {
        ArgumentParser parser = ArgumentParsers.newFor("prog").locale(Locale.US)
                .build();

        String displayName = localizeTypeNameIfPossible(parser,
                CustomTypeWithoutResourceBundle.class);

        assertEquals("CustomTypeWithoutResourceBundle", displayName);
    }

    @Test
    public void displayNameOfCustomTypeWithResourceBundleIsJTextInBundle() {
        ArgumentParser parser = ArgumentParsers.newFor("prog").locale(Locale.US)
                .build();

        String displayName = localizeTypeNameIfPossible(parser,
                CustomTypeWithResourceBundle.class);

        assertEquals("custom type", displayName);
    }

    private static class CustomTypeWithoutResourceBundle {
    }

    private static class CustomTypeWithResourceBundle {
    }
}
