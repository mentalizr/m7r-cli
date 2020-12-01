package org.mentalizr.cli.commands.user.patient;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.CommandExecutor;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.userAdmin.PatientGetAllService;
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

        List<PatientRestoreSO> collection = PatientGetAllService.call(this.cliContext).getCollection();

        // TODO debug out of restoreSO, see AccessKeyShowCommand

        if (collection.size() == 0) {
            System.out.println("No patients found.");
        } else {
            System.out.println(collection.size() + " patient" + (collection.size() > 1 ? "s" : "") + " found:");
            System.out.println("ID                                   | username");
            System.out.println("-------------------------------------+---------------------");
            for (PatientRestoreSO patientRestoreSO : collection) {
                System.out.println(patientRestoreSO.getUuid() + " | " + patientRestoreSO.getUsername());
            }
        }

    }

}
