package org.mentalizr.cli.commands.user.patient;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.CommandExecutor;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.userAdmin.PatientShowService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.PatientRestoreCollectionSO;
import org.mentalizr.serviceObjects.userManagement.PatientRestoreSO;

import java.util.List;

public class PatientShowCommand extends CommandExecutor {

    public PatientShowCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() throws RestServiceHttpException, RestServiceConnectionException {

        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(this.cliContext);
        PatientShowService restService = new PatientShowService(restCallContext);
        PatientRestoreCollectionSO patientRestoreCollectionSO = restService.call();

        List<PatientRestoreSO> collection = patientRestoreCollectionSO.getCollection();

        System.out.println("[OK] Show all patients. Found " + collection.size() + ".");

        for (PatientRestoreSO patientRestoreSO : collection) {
            System.out.println(patientRestoreSO.getUuid() + " | " + patientRestoreSO.getUsername());
        }

    }

}
