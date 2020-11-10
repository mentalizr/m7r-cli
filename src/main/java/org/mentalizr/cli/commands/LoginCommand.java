package org.mentalizr.cli.commands;

import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.CliContext;
import org.mentalizr.client.restService.RestService;

import static org.mentalizr.cli.M7rCli.ID_PASSWORD;
import static org.mentalizr.cli.M7rCli.ID_USER;

public class LoginCommand extends CommandExecutor {

    public LoginCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() {

        OptionParserResult optionParserResultSpecific = this.cliContext.getOptionParserResultSpecific();

        String user = null;
        if (optionParserResultSpecific.hasOption(ID_USER)) {
            user = optionParserResultSpecific.getValue(ID_USER);
        } else {
            System.out.print("user: ");
            user = System.console().readLine();
        }

        String password = "";
        if (optionParserResultSpecific.hasOption(ID_PASSWORD)) {
            password = optionParserResultSpecific.getValue(ID_PASSWORD);
        } else {
            System.out.print("password: ");
            password = new String(System.console().readPassword());
        }

        RestService restService = new org.mentalizr.client.restService.Login(user, password);
        String body = this.checkedCall(restService);

        System.out.println("[OK] Successfully logged in to " + this.cliConfiguration.getServer());

        if (this.cliContext.getCliCallGlobalConfiguration().isDebug()) {
            System.out.println("body: " + body);
        }

    }
}
