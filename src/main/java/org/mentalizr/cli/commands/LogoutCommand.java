package org.mentalizr.cli.commands;

import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.config.CliCallGlobalConfiguration;
import org.mentalizr.client.restService.Logout;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restService.RestServiceCaller;

import java.util.List;

public class LogoutCommand extends CommandExecutor {

    public LogoutCommand(CliCallGlobalConfiguration cliCallGlobalConfiguration, List<String> commandList, OptionParserResult optionParserResultSpecific) {
        super(cliCallGlobalConfiguration, commandList, optionParserResultSpecific);
        this.checkedInit();
    }

    @Override
    public void execute() {
        RestService restService = new Logout();
        RestServiceCaller.call(restService, cliConfiguration);
    }

}
