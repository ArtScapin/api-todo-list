package br.csi.apitodolist.infra.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource) {
        super(resource + " not found");
    }
}
