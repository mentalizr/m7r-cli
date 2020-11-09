package org.mentalizr.cli.commands;

import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.utils.core.strings.Strings;
import org.mentalizr.cli.config.CliCallGlobalConfiguration;
import org.mentalizr.cli.config.CliConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class EditConfigCommand extends CommandExecutor {

    public EditConfigCommand(CliCallGlobalConfiguration cliCallGlobalConfiguration, List<String> commandList, OptionParserResult optionParserResultSpecific) {
        super(cliCallGlobalConfiguration, commandList, optionParserResultSpecific);
        this.checkInitStatus();
    }

    @Override
    public void execute() {

        String editor = System.getenv("EDITOR");
        if (Strings.isNullOrEmpty(editor)) {
            System.out.println("Environment variable EDITOR not found.");
            System.exit(1);
        }

        File configFile = CliConfigurationLoader.getDefaultConfigFile();
        String callEditorBashCommand = editor + " " + configFile;
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", callEditorBashCommand);
        processBuilder.inheritIO();

        try {
            processBuilder.start().waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
