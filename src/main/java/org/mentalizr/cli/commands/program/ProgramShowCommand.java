package org.mentalizr.cli.commands.program;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.CommandExecutor;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restService.userAdmin.ProgramShowService;
import org.mentalizr.client.restService.userAdmin.TherapistShowService;
import org.mentalizr.client.restServiceCaller.RestServiceCaller;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.*;

import java.util.List;

public class ProgramShowCommand extends CommandExecutor {

    public ProgramShowCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() throws RestServiceHttpException, RestServiceConnectionException {

        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(this.cliContext);
        ProgramCollectionSO programCollectionSO = new ProgramShowService(restCallContext).call();
        List<ProgramSO> collection = programCollectionSO.getCollection();

        System.out.println("[OK] Show all programs. Found " + collection.size() + ".");

        for (ProgramSO programSO : collection) {
            System.out.println(programSO.getProgramId());
        }
    }

}
