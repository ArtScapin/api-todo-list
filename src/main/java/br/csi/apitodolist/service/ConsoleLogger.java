package br.csi.apitodolist.service;

public class ConsoleLogger implements Observer{
    @Override
    public void log(String message) {
        System.out.println("Log to console: " + message);
    }
}
