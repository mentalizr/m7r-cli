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
            userInput = System.console().readLine();
        } while (Strings.isNullOrEmpty(userInput));
        return userInput;
    }

    public static String promptForOptionalString(String prompt) {
        System.out.print(prompt);
        return System.console().readLine();
    }

    public static String promptForOptionString(String prompt, String... options) {
        String userInput;
        do {
            System.out.print(prompt);
            userInput = System.console().readLine();
        } while (!equalsAtLeastOne(userInput, Arrays.asList(options)));
        return userInput;
    }

    public static int promptForMandatoryInt(String prompt) {
        Integer integer = null;
        do {
            System.out.print(prompt);
            String userInput = System.console().readLine();
            try {
                integer = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                // din
            }
        } while (integer == null);
        return integer;
    }

    public static boolean equalsAtLeastOne(String x, List<String> options) {
        for (String string : options) {
            if (x.equals(string)) return true;
        }
        return false;
    }

}
