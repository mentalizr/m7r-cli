package org.mentalizr.cli;

import de.arthurpicht.utils.core.strings.Strings;

import java.util.Arrays;
import java.util.List;

public class ConsoleReader {

    public static boolean promptForYesOrNo(String prompt) {
        String userInput;
        do {
            System.out.print(prompt);
            userInput = System.console().readLine().toLowerCase();
        } while (!(userInput.equals("y") || userInput.equals("n")));
        return userInput.equals("y");
    }

    public static String promptForMandatoryString(String prompt) {
        String userInput;
        do {
            System.out.print(prompt);
            userInput = System.console().readLine().toLowerCase();
        } while (Strings.isNullOrEmpty(userInput));
        return userInput;
    }

    public static String promptForOptionalString(String prompt) {
        System.out.print(prompt);
        return System.console().readLine().toLowerCase();
    }

    public static String promptForOptionString(String prompt, String... options) {
        String userInput;
        do {
            System.out.print(prompt);
            userInput = System.console().readLine().toLowerCase();
        } while (!equalsAtLeastOne(userInput, Arrays.asList(options)));
        return userInput;
    }

    public static boolean equalsAtLeastOne(String x, List<String> options) {
        for (String string : options) {
            if (x.equals(string)) return true;
        }
        return false;
    }

}
