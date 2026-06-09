package br.csi.apitodolist.service;

import br.csi.apitodolist.model.item.Item;
import br.csi.apitodolist.model.item.ItemMoveData;
import br.csi.apitodolist.model.item.ItemRepository;
import br.csi.apitodolist.model.item.Priority;
import br.csi.apitodolist.model.list.ListRepository;
import br.csi.apitodolist.model.user.User;
import br.csi.apitodolist.model.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository repository;
    private final ListRepository listRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public ItemService (ItemRepository repository, UserService userService, ListRepository listRepository, UserRepository userRepository) {
        this.repository = repository;
        this.userService = userService;
        this.listRepository = listRepository;
        this.userRepository = userRepository;
    }

    public void create(Item item) {
        if (item.getPriority() == null) {
            item.setPriority(Priority.MEDIUM);
        }
        if (item.getPosition() == null) {
            item.setPosition(0);
        }
        item.setAssignee(this.resolveAssignee(item));
        this.repository.save(item);
        br.csi.apitodolist.model.list.List list = item.getList();
        list.setStatus(false);
        this.listRepository.save(list);
    }

    public Item update(Item itemData, Long id) {
        User user = this.userService.getAuthenticatedUser();
        Item item = this.repository.findByIdAndListWorkspaceUserId(id, user.getId());
        if (itemData.getName() != null) {
            item.setName(itemData.getName());
        }
        item.setDescription(itemData.getDescription());
        if (itemData.getPriority() != null) {
            item.setPriority(itemData.getPriority());
        }
        if (itemData.getPosition() != null) {
            item.setPosition(itemData.getPosition());
        }
        if (itemData.getAssignee() != null) {
            item.setAssignee(this.resolveAssignee(itemData));
        }
        if (itemData.getList() != null && itemData.getList().getId() != null) {
            br.csi.apitodolist.model.list.List list = this.listRepository.findByIdAndWorkspaceUserId(itemData.getList().getId(), user.getId());
            item.setList(list);
        }
        this.repository.save(item);
        return item;
    }

    public Item move(Long id, ItemMoveData moveData) {
        User user = this.userService.getAuthenticatedUser();
        Item item = this.repository.findByIdAndListWorkspaceUserId(id, user.getId());
        br.csi.apitodolist.model.list.List list = this.listRepository.findByIdAndWorkspaceUserId(moveData.listId(), user.getId());
        item.setList(list);
        if (moveData.position() != null) {
            item.setPosition(moveData.position());
        }
        this.repository.save(item);
        return item;
    }

    public Item changeStatus(Long id) {
        User user = this.userService.getAuthenticatedUser();
        Item item = this.repository.findByIdAndListWorkspaceUserId(id, user.getId());
        br.csi.apitodolist.model.list.List list = item.getList();
        if(item.isStatus()) {
            item.setStatus(false);
            this.repository.save(item);

            if(list.isStatus()) {
                list.setStatus(false);
                this.listRepository.save(list);
            }
        } else {
            item.setStatus(true);
            this.repository.save(item);

            List<Item> items = this.findItemsByList(list.getId());
            if(!items.stream().anyMatch(element -> !element.isStatus())){
                list.setStatus(true);
                this.listRepository.save(list);
            }
        }
        return item;
    }

    public List<Item> findItemsByList(Long id) {
        return this.repository.findByListIdOrderByPositionAsc(id);
    }

    public Item findItem(Long id) {
        User user = this.userService.getAuthenticatedUser();
        return this.repository.findByIdAndListWorkspaceUserId(id, user.getId());
    }

    public void deleteItem(Long id) {
        User user = this.userService.getAuthenticatedUser();
        Item item = this.repository.findByIdAndListWorkspaceUserId(id, user.getId());
        br.csi.apitodolist.model.list.List list = item.getList();
        this.repository.delete(item);
        List<Item> items = this.findItemsByList(list.getId());
        if(!items.stream().anyMatch(element -> !element.isStatus())){
            list.setStatus(true);
            this.listRepository.save(list);
        }
    }

    private User resolveAssignee(Item item) {
        if (item.getAssignee() != null && item.getAssignee().getId() != null) {
            return this.userRepository.findById(item.getAssignee().getId()).orElse(null);
        }
        return this.userService.getAuthenticatedUser();
    }
}
