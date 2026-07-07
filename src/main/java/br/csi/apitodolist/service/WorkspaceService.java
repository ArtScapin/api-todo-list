package br.csi.apitodolist.service;

import br.csi.apitodolist.infra.exception.ResourceNotFoundException;
import br.csi.apitodolist.model.user.User;
import br.csi.apitodolist.model.workspace.Workspace;
import br.csi.apitodolist.model.workspace.WorkspaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkspaceService {
    private final WorkspaceRepository repository;
    private final UserService userService;

    public WorkspaceService(WorkspaceRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public void create(Workspace workspace) {
        workspace.setUser(this.userService.getAuthenticatedUser());
        this.repository.save(workspace);
    }

    public List<Workspace> findMyWorkspaces() {
        User user = this.userService.getAuthenticatedUser();
        return this.repository.findByUserId(user.getId());
    }

    public Workspace findWorkspace(Long id){
        return this.findOwnedWorkspace(id);
    }

    public void deleteWorkspace(Long id) {
        Workspace workspace = this.findOwnedWorkspace(id);
        this.repository.delete(workspace);
    }

    public Workspace update(Workspace workspaceData, Long id) {
        Workspace workspace = this.findOwnedWorkspace(id);
        workspace.setName(workspaceData.getName());
        workspace.setKanbanViewMode(workspaceData.isKanbanViewMode());
        this.repository.save(workspace);
        return workspace;
    }

    private Workspace findOwnedWorkspace(Long id) {
        User user = this.userService.getAuthenticatedUser();
        Workspace workspace = this.repository.findByIdAndUserId(id, user.getId());
        if (workspace == null) {
            throw new ResourceNotFoundException("Workspace");
        }
        return workspace;
    }
}
