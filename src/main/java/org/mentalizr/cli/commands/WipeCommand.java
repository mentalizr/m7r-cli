package org.mentalizr.cli.commands;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.ConsoleReader;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.exceptions.UserAbortedException;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.accessKey.AccessKeyDeleteService;
import org.mentalizr.client.restService.accessKey.AccessKeyGetAllService;
import org.mentalizr.client.restService.userAdmin.*;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.*;

import java.util.List;

public class WipeCommand extends AbstractCommandExecutor {

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

        System.out.println("Delete all patients ...");
        deleteAllPatients(restCallContext);

        System.out.println("Delete all access keys ...");
        deleteAllAccessKeys(restCallContext);

        System.out.println("Delete all therapists ...");
        deleteAllTherapists(restCallContext);

        System.out.println("Delete all programs ...");
        deleteAllPrograms(restCallContext);

        System.out.println("[OK] User database wiped out.");
    }

    private void deleteAllPatients(RESTCallContext restCallContext) throws RestServiceHttpException, RestServiceConnectionException {
        PatientRestoreCollectionSO patientRestoreCollectionSO = new PatientGetAllService(restCallContext).call();
        List<PatientRestoreSO> collection = patientRestoreCollectionSO.getCollection();
        if (collection.isEmpty()) System.out.println("No patients found.");
        for (PatientRestoreSO patientRestoreSO : collection) {
            new PatientDeleteService(patientRestoreSO.getUsername(), restCallContext).call();
            System.out.println("Patient [" + patientRestoreSO.getUsername() + "] deleted.");
        }
    }

    private void deleteAllAccessKeys(RESTCallContext restCallContext) throws RestServiceHttpException, RestServiceConnectionException {
        AccessKeyCollectionSO accessKeyCollectionSO = new AccessKeyGetAllService(restCallContext).call();
        List<AccessKeyRestoreSO> collection = accessKeyCollectionSO.getCollection();
        if (collection.isEmpty()) System.out.println("No access keys found.");
        for (AccessKeyRestoreSO accessKeyRestoreSO : collection) {
            AccessKeyDeleteSO accessKeyDeleteSO = new AccessKeyDeleteSO();
            accessKeyDeleteSO.setAccessKey(accessKeyRestoreSO.getAccessKey());
            new AccessKeyDeleteService(accessKeyDeleteSO, restCallContext).call();
            System.out.println("Access key [" + accessKeyRestoreSO.getAccessKey() + "] deleted.");
        }
    }

    private void deleteAllTherapists(RESTCallContext restCallContext) throws RestServiceHttpException, RestServiceConnectionException {
        TherapistRestoreCollectionSO therapistRestoreCollectionSO = new TherapistGetAllService(restCallContext).call();
        List<TherapistRestoreSO> collection = therapistRestoreCollectionSO.getCollection();
        if (collection.isEmpty()) System.out.println("No therapists found.");
        for (TherapistRestoreSO therapistRestoreSO : collection) {
            new TherapistDeleteService(therapistRestoreSO.getUsername(), restCallContext).call();
            System.out.println("Therapist [" + therapistRestoreSO.getUsername() + "] deleted.");
        }
    }

    private void deleteAllPrograms(RESTCallContext restCallContext) throws RestServiceHttpException, RestServiceConnectionException {
        ProgramCollectionSO programCollectionSO = new ProgramGetAllService(restCallContext).call();
        List<ProgramSO> collection = programCollectionSO.getCollection();
        if (collection.isEmpty()) System.out.println("No programs found.");
        for (ProgramSO programSO : collection) {
            new ProgramDeleteService(programSO.getProgramId(), restCallContext).call();
            System.out.println("Program [" + programSO.getProgramId() + "] deleted.");
        }
    }

}
