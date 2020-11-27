package org.mentalizr.cli.commands.accessKey;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.ConsoleReader;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.CommandExecutor;
import org.mentalizr.cli.exceptions.UserAbortedException;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.accessKey.AccessKeyCreateService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.AccessKeyCollectionSO;
import org.mentalizr.serviceObjects.userManagement.AccessKeyCreateSO;
import org.mentalizr.serviceObjects.userManagement.AccessKeyRestoreSO;

import java.util.List;

public class AccessKeyCreateCommand extends CommandExecutor {

    public AccessKeyCreateCommand(CliContext cliContext) {
        super(cliContext);
    }

    @Override
    public void execute() throws RestServiceHttpException, RestServiceConnectionException, UserAbortedException {

        AccessKeyCreateSO accessKeyCreateSO = fromPrompt();

        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(this.cliContext);
        AccessKeyCollectionSO accessKeyCollectionSO = new AccessKeyCreateService(accessKeyCreateSO, restCallContext)
                .call();

        System.out.println("[OK] " + accessKeyCreateSO.getNrOfKeys() + " access keys created:");

        List<AccessKeyRestoreSO> collection = accessKeyCollectionSO.getCollection();
        for (AccessKeyRestoreSO accessKeyRestoreSO : collection) {
            System.out.println(accessKeyRestoreSO.getAccessKey());
        }

    }

    private AccessKeyCreateSO fromPrompt() throws UserAbortedException {

        AccessKeyCreateSO accessKeyCreateSO = new AccessKeyCreateSO();

        int nrOfKeys = ConsoleReader.promptForMandatoryInt("number of keys: ");
        accessKeyCreateSO.setNrOfKeys(nrOfKeys);

        String startWith = ConsoleReader.promptForOptionalString("Begin letters of code (optional): ");
        accessKeyCreateSO.setStartWith(startWith);

        boolean active = ConsoleReader.promptForYesOrNo("active? (y/n): ");
        accessKeyCreateSO.setActive(active);

        String programId = ConsoleReader.promptForMandatoryString("Program: ");
        accessKeyCreateSO.setProgramId(programId);

        String therapistId = ConsoleReader.promptForMandatoryString("Therapist ID: ");
        accessKeyCreateSO.setTherapistId(therapistId);

        boolean confirm = ConsoleReader.promptForYesOrNo("Continue? (y/n): ");
        if (!confirm) throw new UserAbortedException();

        return accessKeyCreateSO;
    }

}
