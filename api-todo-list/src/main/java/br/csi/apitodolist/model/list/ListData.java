package br.csi.apitodolist.model.list;


import br.csi.apitodolist.model.workspace.WorkspaceData;

public record ListData(Long id, String name, Boolean status, WorkspaceData workspace) {
    public ListData(List list) {
        this(list.getId(), list.getName(), list.isStatus(), new WorkspaceData(list.getWorkspace()));
    }
}
