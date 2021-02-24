package org.mentalizr.cli.commands;

import de.arthurpicht.cli.CliCall;
import de.arthurpicht.cli.CommandExecutor;
import de.arthurpicht.cli.CommandExecutorException;
import de.arthurpicht.utils.core.strings.Strings;
import org.mentalizr.cli.config.CliConfigurationFiles;

import java.io.File;
import java.io.IOException;

public class EditConfigCommandExecutor implements CommandExecutor {

    @Override
    public void execute(CliCall cliCall) throws CommandExecutorException {

        CommandExecutorHelper.checkInitStatus();

        String editor = System.getenv("EDITOR");
        if (Strings.isNullOrEmpty(editor)) {
            System.out.println("Environment variable EDITOR not found.");
            System.exit(1);
        }

        File configFile = CliConfigurationFiles.getDefaultConfigFileCLI();
        String callEditorBashCommand = editor + " " + configFile;
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", callEditorBashCommand);
        processBuilder.inheritIO();

        try {
            processBuilder.start().waitFor();
        } catch (IOException | InterruptedException e) {
            throw new CommandExecutorException(e);
        }
    }

}
