package org.mentalizr.cli.commands.sessionManagement;

import de.arthurpicht.cli.option.OptionParserResult;
import de.arthurpicht.utils.io.textfile.TextFile;
import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.CommandExecutor;
import org.mentalizr.cli.config.CliConfigurationFiles;
import org.mentalizr.cli.config.CliConfigurationLoader;
import org.mentalizr.cli.exceptions.CliException;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restService.sessionManagement.LoginService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.mentalizr.cli.M7rCli.*;

public class LoginCommand extends CommandExecutor {

    private String user;
    private String password;

    public LoginCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() throws RestServiceHttpException, RestServiceConnectionException {

        OptionParserResult optionParserResultSpecific = this.cliContext.getOptionParserResultSpecific();

        if (optionParserResultSpecific.hasOption(OPTION__CREDENTIAL_FILE)) {
            obtainCredentialsFromFile();
        } else {
            obtainCredentialsFromCommandLine(optionParserResultSpecific);
        }

        String body = callLoginService();

        System.out.println("[OK] Successfully logged in to " + this.cliContext.getCliConfiguration().getServer());

        debugOut(body);
    }

    private void obtainCredentialsFromFile() {
        File credentialFile = new File(CliConfigurationFiles.getConfigDirCLI(), "credentials.txt");
        if (!credentialFile.exists())
            throw new CliException("Credential file '" + credentialFile.getAbsolutePath() + "' not found.");
        try {
            List<String> credentialFileStrings = TextFile.getLinesAsStrings(credentialFile);
            if (credentialFileStrings.size() < 2)
                throw new CliException("Could not read credential file. Content is malformed.");
            this.user = credentialFileStrings.get(0);
            this.password = credentialFileStrings.get(1);

        } catch (IOException e) {
            throw new CliException("Could not read credential file '" + credentialFile + "'. " + e.getMessage(), e);
        }
    }

    private void obtainCredentialsFromCommandLine(OptionParserResult optionParserResultSpecific) {
        if (optionParserResultSpecific.hasOption(ID_USER)) {
            user = optionParserResultSpecific.getValue(ID_USER);
        } else {
            System.out.print("user: ");
            user = System.console().readLine();
        }

        if (optionParserResultSpecific.hasOption(ID_PASSWORD)) {
            password = optionParserResultSpecific.getValue(ID_PASSWORD);
        } else {
            System.out.print("password: ");
            password = new String(System.console().readPassword());
        }
    }

    private String callLoginService() throws RestServiceHttpException, RestServiceConnectionException {
        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(this.cliContext);
        RestService restService = new LoginService(user, password);
        return RestServiceCaller.call(restCallContext, restService);
    }

    private void debugOut(String body) {
        if (this.cliContext.getCliCallGlobalConfiguration().isDebug()) {
            System.out.println("body: " + body);
        }
    }

}
