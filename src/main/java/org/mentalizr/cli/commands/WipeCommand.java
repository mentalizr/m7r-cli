package org.mentalizr.cli.commands;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.ConsoleReader;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.config.Init;
import org.mentalizr.cli.exceptions.UserAbortedException;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.userAdmin.ProgramDeleteService;
import org.mentalizr.client.restService.userAdmin.ProgramShowService;
import org.mentalizr.client.restService.userAdmin.TherapistDeleteService;
import org.mentalizr.client.restService.userAdmin.TherapistShowService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.ProgramCollectionSO;
import org.mentalizr.serviceObjects.userManagement.ProgramSO;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreCollectionSO;
import org.mentalizr.serviceObjects.userManagement.TherapistRestoreSO;

import java.util.List;

public class WipeCommand extends CommandExecutor {

    public WipeCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() throws UserAbortedException, RestServiceHttpException, RestServiceConnectionException {

        System.out.println("[WARNING] This will DELETE ALL database content!");
        boolean confirm = ConsoleReader.promptForYesOrNo("Continue? (y/n): ");
        if (!confirm) throw new UserAbortedException();

        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(this.cliContext);

        deleteAllTherapists(restCallContext);
        deleteAllPrograms(restCallContext);

        System.out.println("[OK] User database wiped out.");
    }

    private void deleteAllTherapists(RESTCallContext restCallContext) throws RestServiceHttpException, RestServiceConnectionException {
        TherapistRestoreCollectionSO therapistRestoreCollectionSO = new TherapistShowService(restCallContext).call();
        List<TherapistRestoreSO> collection = therapistRestoreCollectionSO.getCollection();
        for (TherapistRestoreSO therapistRestoreSO : collection) {
            new TherapistDeleteService(therapistRestoreSO.getUsername(), restCallContext).call();
            System.out.println("Therapist [" + therapistRestoreSO.getUsername() + "] deleted.");
        }
    }

    private void deleteAllPrograms(RESTCallContext restCallContext) throws RestServiceHttpException, RestServiceConnectionException {
        ProgramCollectionSO programCollectionSO = new ProgramShowService(restCallContext).call();
        List<ProgramSO> collection = programCollectionSO.getCollection();
        for (ProgramSO programSO : collection) {
            new ProgramDeleteService(programSO.getProgramId(), restCallContext).call();
            System.out.println("Program [" + programSO.getProgramId() + "] deleted.");
        }
    }


}
