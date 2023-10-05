package br.csi.apitodolist.service;

import br.csi.apitodolist.model.list.List;
import br.csi.apitodolist.model.list.ListRepository;
import br.csi.apitodolist.model.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ListService {
    private final ListRepository repository;
    private final UserService userService;

    public ListService(ListRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public void create(List list) {
        this.repository.save(list);
    }

    public java.util.List<List> findListsByWorkspace(Long workspaceId) {
        return this.repository.findByWorkspaceId(workspaceId);
    }
    public List findList(Long id) {
        User user = this.userService.getAuthenticatedUser();
        return this.repository.findByIdAndWorkspaceUserId(id, user.getId());
    }

    public void deleteList(Long id) {
        User user = this.userService.getAuthenticatedUser();
        List list = this.repository.findByIdAndWorkspaceUserId(id, user.getId());
        this.repository.delete(list);
    }
}
