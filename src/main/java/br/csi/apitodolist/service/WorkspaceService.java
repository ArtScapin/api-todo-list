package br.csi.apitodolist.service;

import br.csi.apitodolist.model.user.User;
import br.csi.apitodolist.model.workspace.Workspace;
import br.csi.apitodolist.model.workspace.WorkspaceRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public List<Workspace> findMyWorkspaces(Long userId) {
        return this.repository.findByUserId(userId);
    }

    public Workspace findWorkspace(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findUser(authentication.getName());
        return this.repository.findByIdAndUserId(id, user.getId());
    }

    public void deleteWorkspace(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findUser(authentication.getName());
        Workspace workspace = this.repository.findByIdAndUserId(id, user.getId());
        this.repository.delete(workspace);
    }
}
