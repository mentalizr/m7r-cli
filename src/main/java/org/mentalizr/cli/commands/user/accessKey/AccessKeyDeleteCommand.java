package org.mentalizr.cli.commands.user.accessKey;

import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.M7rCli;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.CommandExecutor;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.accessKey.AccessKeyDeleteService;
import org.mentalizr.client.restService.userAdmin.PatientDeleteService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.AccessKeyDeleteSO;

public class AccessKeyDeleteCommand extends CommandExecutor {

    public AccessKeyDeleteCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() throws RestServiceHttpException, RestServiceConnectionException {

        OptionParserResult optionParserResultSpecific = this.cliContext.getOptionParserResultSpecific();

        if (optionParserResultSpecific.hasOption(M7rCli.OPTION__ACCESS_KEY)) {

            String accessKey = optionParserResultSpecific.getValue(M7rCli.OPTION__ACCESS_KEY).trim();

            RESTCallContext restCallContext = RESTCallContextFactory.getInstance(this.cliContext);
            AccessKeyDeleteSO accessKeyDeleteSO = new AccessKeyDeleteSO();
            accessKeyDeleteSO.setAccessKey(accessKey);
            new AccessKeyDeleteService(accessKeyDeleteSO, restCallContext).call();

            System.out.println("[OK] Access key [" + accessKey + "] deleted.");

        } else if (optionParserResultSpecific.hasOption(M7rCli.ID_UUID)) {
            throw new RuntimeException("Not implemented yet.");
        } else {
            System.out.println("[ERROR] Please specify --accessKey option.");
        }
    }

}
