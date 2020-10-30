package org.mentalizr.cli;

import org.mentalizr.cli.config.CliConfiguration;
import org.mentalizr.cli.config.CliConfigurationLoader;
import org.mentalizr.cli.httpClient.HttpClientCreator;
import org.mentalizr.cli.httpClient.HttpRequestCreator;
import org.mentalizr.cli.restService.Login;
import org.mentalizr.cli.restService.Noop;
import org.mentalizr.cli.restService.RestService;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class M7rCli {

    public static void main(String[] args) {

        CliConfiguration cliConfiguration = CliConfigurationLoader.load();
        System.out.println(cliConfiguration.toString());

        try {
            execute(cliConfiguration);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void execute(CliConfiguration cliConfiguration) throws IOException, InterruptedException {

//        RestService restService = new Noop();
        RestService restService = new Login("dummy", "secret");

        HttpClient client = HttpClientCreator.create(cliConfiguration);
        HttpRequest httpRequest = HttpRequestCreator.create(restService, cliConfiguration);

        HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.statusCode());
        System.out.println(response.body());

    }

}