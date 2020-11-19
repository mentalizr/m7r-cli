package org.mentalizr.cli.commands.user.therapist;

import de.arthurpicht.cli.option.OptionParserResult;
import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.ConsoleReader;
import org.mentalizr.cli.M7rCli;
import org.mentalizr.cli.commands.CommandExecutor;
import org.mentalizr.cli.exceptions.UserAbortedException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.TherapistAddSO;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

public class TherapistRestoreCommand extends CommandExecutor {

    public TherapistRestoreCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() throws RestServiceHttpException, RestServiceConnectionException, UserAbortedException {

        OptionParserResult optionParserResultSpecific = this.cliContext.getOptionParserResultSpecific();

        TherapistAddSO therapistAddSO;

        System.out.println("Execute TherapistRestoreCommand");

        if (optionParserResultSpecific.hasOption(M7rCli.ID_FROM_FILE)) {
            // TODO
            System.out.println("Restore Therapist from file: " + optionParserResultSpecific.getValue(M7rCli.ID_FROM_FILE));
        }
//        else if (optionParserResultSpecific.hasOption(M7rCli.ID_SHOW_TEMPLATE)) {
//            System.out.println(getTemplate());
//            return;
//        } else {
//            therapistSO = fromPrompt();
//        }

//        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(this.cliContext);
//        RestService restService = new AddTherapistService(therapistSO);
//        String body = RestServiceCaller.call(restCallContext, restService);
//
//        Jsonb jsonb = JsonbBuilder.create();
//        TherapistSO therapistSOBack = jsonb.fromJson(body, TherapistSO.class);
//
//        System.out.println("[OK] User + '" + therapistSOBack.getUsername() + "' added as therapist with UUID: " + therapistSOBack.getUuid());

    }


    private TherapistAddSO fromPrompt() throws UserAbortedException {

        TherapistAddSO therapistAddSO = new TherapistAddSO();

        boolean active = ConsoleReader.promptForYesOrNo("active? (y/n): ");
        therapistAddSO.setActive(active);

        String username = ConsoleReader.promptForMandatoryString("Username: ");
        therapistAddSO.setUsername(username);

        String title = ConsoleReader.promptForOptionalString("Title (optional): ");
        therapistAddSO.setTitle(title);

        String firstname = ConsoleReader.promptForMandatoryString("Firstname: ");
        therapistAddSO.setFirstname(firstname);

        String lastname = ConsoleReader.promptForMandatoryString("Lastname: ");
        therapistAddSO.setLastname(lastname);

        String email = ConsoleReader.promptForMandatoryString("Email: ");
        therapistAddSO.setEmail(email);

        String genderString = ConsoleReader.promptForOptionString("Gender (m,f,x): ", "m", "f", "x");
        int gender = 0;
        switch (genderString) {
            case "f":
                gender=0;
                break;
            case "m":
                gender=1;
                break;
            case "x":
                gender=2;
                break;
        }
        therapistAddSO.setGender(gender);

        String password = ConsoleReader.promptForMandatoryString("Password: ");
        therapistAddSO.setPassword(password);

        boolean confirm = ConsoleReader.promptForYesOrNo("Continue? (y/n): ");
        if (!confirm) throw new UserAbortedException();

        return therapistAddSO;
    }

    private String getTemplate() {

        TherapistAddSO therapistAddSO = new TherapistAddSO();
        therapistAddSO.setActive(true);
        therapistAddSO.setTitle("M.Sc.");
        therapistAddSO.setUsername("jdummy");
        therapistAddSO.setFirstname("Joe");
        therapistAddSO.setLastname("Dummy");
        therapistAddSO.setEmail("joe.dummy@example.org");
        therapistAddSO.setGender(1);
        therapistAddSO.setPassword("topsecret");

        Jsonb jsonb = JsonbBuilder.create(new JsonbConfig().withFormatting(true));

        return jsonb.toJson(therapistAddSO);
    }
}
