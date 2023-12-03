package br.csi.apitodolist.model.workspace;

public record WorkspaceData(Long id, String name) {
    public WorkspaceData(Workspace workspace) {
        this(workspace.getId(), workspace.getName());
    }
}
