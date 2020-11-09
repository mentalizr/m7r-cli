package org.mentalizr.cli.commands;

import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.config.CliCallGlobalConfiguration;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restService.RestServiceCaller;

import java.util.List;

import static org.mentalizr.cli.M7rCli.ID_PASSWORD;
import static org.mentalizr.cli.M7rCli.ID_USER;

public class LoginCommand extends CommandExecutor {

    public LoginCommand(CliCallGlobalConfiguration cliCallGlobalConfiguration,List<String> commandList, OptionParserResult optionParserResultSpecific) {
        super(cliCallGlobalConfiguration, commandList, optionParserResultSpecific);
    }

    @Override
    public void execute() {

        String user = null;
        if (optionParserResultSpecific.hasOption(ID_USER)) {
            user = optionParserResultSpecific.getValue(ID_USER);
        } else {
            System.out.println("Error. No user specified.");
            System.exit(1);
        }

        String password = "";
        boolean hasPassword;
        if (optionParserResultSpecific.hasOption(ID_PASSWORD)) {
            password = optionParserResultSpecific.getValue(ID_PASSWORD);
        } else {
            hasPassword = false;
        }

        // TODO read password from console is not given as option
        RestService restService = new org.mentalizr.client.restService.Login(user, password);
        RestServiceCaller.call(restService);


    }
}
