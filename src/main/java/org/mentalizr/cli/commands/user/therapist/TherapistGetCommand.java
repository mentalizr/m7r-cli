package org.mentalizr.cli.commands.user.therapist;

import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.M7rCli;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.CommandExecutor;
import org.mentalizr.cli.exceptions.UserAbortedException;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restService.userAdmin.GetTherapistService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSO;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

public class TherapistGetCommand extends CommandExecutor {

    public TherapistGetCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() throws RestServiceHttpException, RestServiceConnectionException, UserAbortedException {

        OptionParserResult optionParserResultSpecific = this.cliContext.getOptionParserResultSpecific();

        if (optionParserResultSpecific.hasOption(M7rCli.ID_USER)) {

            String username = optionParserResultSpecific.getValue(M7rCli.ID_USER);

            RESTCallContext restCallContext = RESTCallContextFactory.getInstance(this.cliContext);
            RestService restService = new GetTherapistService(username);
            String responseBody = RestServiceCaller.call(restCallContext, restService);

            Jsonb jsonb = JsonbBuilder.create();
            TherapistRestoreSO therapistRestoreSO = jsonb.fromJson(responseBody, TherapistRestoreSO.class);

            System.out.println("[OK] Get therapist '" + therapistRestoreSO.getUsername() + "':");

            jsonb = JsonbBuilder.create(new JsonbConfig().withFormatting(true));
            System.out.println(jsonb.toJson(therapistRestoreSO));

        } else if (optionParserResultSpecific.hasOption(M7rCli.ID_UUID)) {
            throw new RuntimeException("Not implemented yet.");
        } else {
            System.out.println("[ERROR] Please specify --user or --uuid option.");
        }
    }

}
