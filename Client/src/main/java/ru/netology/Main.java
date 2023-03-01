package ru.netology;

import client.Client;

public class Main {
    public static void main(String[] args) {

        Client client = new Client();

        Runnable prepareInput = client::inputMessage;

        Runnable prepareOutput = () -> {
            try {
                client.outputMessage();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        client.inputMessage();
        //Thread inputThread = new Thread(prepareInput);
        Thread outputThread = new Thread(prepareOutput);
    }

}