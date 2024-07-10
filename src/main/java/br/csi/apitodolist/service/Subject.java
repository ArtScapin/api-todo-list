package br.csi.apitodolist.service;

public interface Subject {
    void attach(Observer logger);
    void detach(Observer logger);
    void notifyLoggers(String message);
}

