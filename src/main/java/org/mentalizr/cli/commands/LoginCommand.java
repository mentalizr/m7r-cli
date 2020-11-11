package org.mentalizr.cli.commands;

import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.CliContext;
import org.mentalizr.client.ClientContext;
import org.mentalizr.client.restService.LoginService;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;

import static org.mentalizr.cli.M7rCli.ID_PASSWORD;
import static org.mentalizr.cli.M7rCli.ID_USER;

public class LoginCommand extends CommandExecutor {

    public LoginCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() throws RestServiceHttpException, RestServiceConnectionException {

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

        RestService restService = new LoginService(user, password);
        ClientContext clientContext = this.getClientContext();
        String body = RestServiceCaller.call(restService, clientContext);

        System.out.println("[OK] Successfully logged in to " + this.cliContext.getCliConfiguration().getServer());

        if (this.cliContext.getCliCallGlobalConfiguration().isDebug()) {
            System.out.println("body: " + body);
        }

    }
}
