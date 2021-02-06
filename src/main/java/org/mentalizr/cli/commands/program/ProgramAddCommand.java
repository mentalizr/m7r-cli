package org.mentalizr.cli.commands.program;

import org.mentalizr.cli.CliContext;
import org.mentalizr.cli.ConsoleReader;
import org.mentalizr.cli.RESTCallContextFactory;
import org.mentalizr.cli.commands.AbstractCommandExecutor;
import org.mentalizr.client.RESTCallContext;
import org.mentalizr.client.restService.RestService;
import org.mentalizr.client.restService.userAdmin.ProgramAddService;
import org.mentalizr.client.restServiceCaller.exception.RestServiceConnectionException;
import org.mentalizr.client.restServiceCaller.exception.RestServiceHttpException;
import org.mentalizr.serviceObjects.userManagement.ProgramSO;

public class ProgramAddCommand extends AbstractCommandExecutor {

    public ProgramAddCommand(CliContext cliContext) {
        super(cliContext);
        this.checkedInit();
    }

    @Override
    public void execute() throws RestServiceHttpException, RestServiceConnectionException {

        ProgramSO programSO = new ProgramSO();

        String programId = ConsoleReader.promptForMandatoryString("programId: ");
        programSO.setProgramId(programId);

        RESTCallContext restCallContext = RESTCallContextFactory.getInstance(this.cliContext);
        RestService restService = new ProgramAddService(programSO, restCallContext);
        restService.call();

        System.out.println("[OK] Program [" + programId + "] added.");
    }

}
