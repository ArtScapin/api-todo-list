package br.csi.apitodolist.service;

import br.csi.apitodolist.model.user.User;
import br.csi.apitodolist.model.workspace.Workspace;
import br.csi.apitodolist.model.workspace.WorkspaceData;
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
        this.repository.save(workspace);
    }

    public List<Workspace> findMyWorkspaces() {
        User user = this.userService.getAuthenticatedUser();
        return this.repository.findByUserId(user.getId());
    }

    public Workspace findWorkspace(Long id){
        User user = this.userService.getAuthenticatedUser();
        return this.repository.findByIdAndUserId(id, user.getId());
    }

    public void deleteWorkspace(Long id) {
        User user = this.userService.getAuthenticatedUser();
        Workspace workspace = this.repository.findByIdAndUserId(id, user.getId());
        this.repository.delete(workspace);
    }

    public Workspace update(Workspace workspaceData, Long id) {
        User user = this.userService.getAuthenticatedUser();
        Workspace workspace = this.repository.findByIdAndUserId(id, user.getId());
        workspace.setName(workspaceData.getName());
        this.repository.save(workspace);
        return workspace;
    }
}
