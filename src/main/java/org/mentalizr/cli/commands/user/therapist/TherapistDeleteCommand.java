package org.mentalizr.cli.commands.user.therapist;

import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.M7rCli;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.CommandExecutor;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restService.userAdmin.DeleteTherapistService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;

public class TherapistDeleteCommand extends CommandExecutor {

    public TherapistDeleteCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() throws RestServiceHttpException, RestServiceConnectionException {

        OptionParserResult optionParserResultSpecific = this.cliContext.getOptionParserResultSpecific();

        if (optionParserResultSpecific.hasOption(M7rCli.ID_USER)) {

            String username = optionParserResultSpecific.getValue(M7rCli.ID_USER).trim();

            System.out.println("Delete user '" + username + "' ...");

            RESTCallContext restCallContext = RESTCallContextFactory.getInstance(this.cliContext);
            RestService restService = new DeleteTherapistService(username);
            String responseBody = RestServiceCaller.call(restCallContext, restService);

//            Jsonb jsonb = JsonbBuilder.create();
//            ResponseSO responseSO = jsonb.fromJson(responseBody, ResponseSO.class);
//
//            if (responseSO.isOk()) {
//                System.out.println("[OK] Therapist '" + username + "' deleted.");
//            } else {
//                throw new CliException(responseSO.getMessage());
//            }

            System.out.println("[OK]");

        } else if (optionParserResultSpecific.hasOption(M7rCli.ID_UUID)) {
            throw new RuntimeException("Not implemented yet.");
        } else {
            System.out.println("[ERROR] Please specify --user or --uuid option.");
        }
    }

}
