package br.csi.apitodolist.service;

import br.csi.apitodolist.infra.exception.ResourceNotFoundException;
import br.csi.apitodolist.model.item.Item;
import br.csi.apitodolist.model.item.ItemRepository;
import br.csi.apitodolist.model.list.List;
import br.csi.apitodolist.model.list.ListRepository;
import br.csi.apitodolist.model.user.User;
import br.csi.apitodolist.model.workspace.Workspace;
import br.csi.apitodolist.model.workspace.WorkspaceRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class ListService {
    private final ListRepository repository;
    private final ItemRepository itemRepository;
    private final WorkspaceRepository workspaceRepository;
    private final UserService userService;

    public ListService(ListRepository repository, ItemRepository itemRepository, WorkspaceRepository workspaceRepository, UserService userService) {
        this.repository = repository;
        this.itemRepository = itemRepository;
        this.workspaceRepository = workspaceRepository;
        this.userService = userService;
    }

    public void create(List list, Long workspaceId) {
        User user = this.userService.getAuthenticatedUser();
        Workspace workspace = this.workspaceRepository.findByIdAndUserId(workspaceId, user.getId());
        if (workspace == null) {
            throw new ResourceNotFoundException("Workspace");
        }
        list.setWorkspace(workspace);
        list.setStatus(true);
        this.repository.save(list);
        this.syncKanbanItemStatus(workspace.getId());
    }

    public java.util.List<List> findListsByWorkspace(Long workspaceId) {
        User user = this.userService.getAuthenticatedUser();
        if (this.workspaceRepository.findByIdAndUserId(workspaceId, user.getId()) == null) {
            throw new ResourceNotFoundException("Workspace");
        }
        return this.repository.findByWorkspaceIdAndWorkspaceUserIdOrderByPositionAsc(workspaceId, user.getId());
    }

    public List findList(Long id) {
        return this.findOwnedList(id);
    }

    public List update(List listData, Long id) {
        List list = this.findOwnedList(id);
        list.setName(listData.getName());
        list.setPosition(listData.getPosition());
        list.setColor(listData.getColor());
        this.repository.save(list);
        this.syncKanbanItemStatus(list.getWorkspace().getId());
        return list;
    }

    public void delete(Long id) {
        List list = this.findOwnedList(id);
        Long workspaceId = list.getWorkspace().getId();
        this.repository.delete(list);
        this.syncKanbanItemStatus(workspaceId);
    }

    private List findOwnedList(Long id) {
        User user = this.userService.getAuthenticatedUser();
        List list = this.repository.findByIdAndWorkspaceUserId(id, user.getId());
        if (list == null) {
            throw new ResourceNotFoundException("List");
        }
        return list;
    }

    private void syncKanbanItemStatus(Long workspaceId) {
        User user = this.userService.getAuthenticatedUser();
        Workspace workspace = this.workspaceRepository.findByIdAndUserId(workspaceId, user.getId());
        if (workspace == null || !workspace.isKanbanViewMode()) {
            return;
        }

        java.util.List<List> lists = this.repository.findByWorkspaceIdAndWorkspaceUserIdOrderByPositionAsc(workspaceId, user.getId());
        List lastList = lists.stream()
                .max(Comparator.comparing(
                        List::getPosition,
                        Comparator.nullsFirst(Integer::compareTo)
                ).thenComparing(List::getId))
                .orElse(null);

        for (List list : lists) {
            boolean isLastList = lastList != null && lastList.getId().equals(list.getId());
            java.util.List<Item> items = this.itemRepository.findByListIdAndListWorkspaceUserIdOrderByPositionAsc(list.getId(), user.getId());
            items.forEach(item -> item.setStatus(isLastList));
            this.itemRepository.saveAll(items);
            list.setStatus(items.stream().noneMatch(item -> !item.isStatus()));
            this.repository.save(list);
        }
    }
}
