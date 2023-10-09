package br.csi.apitodolist.service;

import br.csi.apitodolist.model.list.List;
import br.csi.apitodolist.model.list.ListData;
import br.csi.apitodolist.model.list.ListRepository;
import br.csi.apitodolist.model.user.User;
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

    public java.util.List<ListData> findListsByWorkspace(Long workspaceId) {
        return this.repository.findByWorkspaceId(workspaceId).stream().map(ListData::new).toList();
    }
    public List findList(Long id) {
        User user = this.userService.getAuthenticatedUser();
        return this.repository.findByIdAndWorkspaceUserId(id, user.getId());
    }

    public List update(List listData, Long id) {
        User user = this.userService.getAuthenticatedUser();
        List list = this.repository.findByIdAndWorkspaceUserId(id, user.getId());
        list.setName(listData.getName());
        this.repository.save(list);
        return list;
    }

    public void delete(Long id) {
        User user = this.userService.getAuthenticatedUser();
        List list = this.repository.findByIdAndWorkspaceUserId(id, user.getId());
        this.repository.delete(list);
    }
}
