package br.csi.apitodolist.service;

import br.csi.apitodolist.infra.exception.ResourceNotFoundException;
import br.csi.apitodolist.model.item.Item;
import br.csi.apitodolist.model.item.ItemMoveData;
import br.csi.apitodolist.model.item.ItemRepository;
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

    public ItemService(ItemRepository repository, UserService userService, ListRepository listRepository, UserRepository userRepository) {
        this.repository = repository;
        this.userService = userService;
        this.listRepository = listRepository;
        this.userRepository = userRepository;
    }

    public void create(Item item, Long listId) {
        br.csi.apitodolist.model.list.List list = this.findOwnedList(listId);
        item.setList(list);
        item.setStatus(false);
        item.setAssignee(this.resolveAssignee(item));
        this.repository.save(item);
        list.setStatus(false);
        this.listRepository.save(list);
    }

    public Item update(Item itemData, Long id) {
        Item item = this.findOwnedItem(id);
        item.setName(itemData.getName());
        item.setDescription(itemData.getDescription());
        item.setPriority(itemData.getPriority());
        item.setPosition(itemData.getPosition());
        if (itemData.getAssignee() != null) {
            item.setAssignee(this.resolveAssignee(itemData));
        }
        if (itemData.getList() != null && itemData.getList().getId() != null) {
            item.setList(this.findOwnedList(itemData.getList().getId()));
        }
        this.repository.save(item);
        return item;
    }

    public Item move(Long id, ItemMoveData moveData) {
        Item item = this.findOwnedItem(id);
        br.csi.apitodolist.model.list.List list = this.findOwnedList(moveData.listId());
        item.setList(list);
        item.setPosition(moveData.position());
        this.repository.save(item);
        return item;
    }

    public Item changeStatus(Long id) {
        Item item = this.findOwnedItem(id);
        br.csi.apitodolist.model.list.List list = item.getList();
        if (item.isStatus()) {
            item.setStatus(false);
            this.repository.save(item);

            if (list.isStatus()) {
                list.setStatus(false);
                this.listRepository.save(list);
            }
        } else {
            item.setStatus(true);
            this.repository.save(item);

            List<Item> items = this.findItemsByList(list.getId());
            if (items.stream().noneMatch(element -> !element.isStatus())) {
                list.setStatus(true);
                this.listRepository.save(list);
            }
        }
        return item;
    }

    public List<Item> findItemsByList(Long id) {
        User user = this.userService.getAuthenticatedUser();
        this.findOwnedList(id);
        return this.repository.findByListIdAndListWorkspaceUserIdOrderByPositionAsc(id, user.getId());
    }

    public Item findItem(Long id) {
        return this.findOwnedItem(id);
    }

    public void deleteItem(Long id) {
        Item item = this.findOwnedItem(id);
        br.csi.apitodolist.model.list.List list = item.getList();
        this.repository.delete(item);
        List<Item> items = this.findItemsByList(list.getId());
        if (items.stream().noneMatch(element -> !element.isStatus())) {
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

    private Item findOwnedItem(Long id) {
        User user = this.userService.getAuthenticatedUser();
        Item item = this.repository.findByIdAndListWorkspaceUserId(id, user.getId());
        if (item == null) {
            throw new ResourceNotFoundException("Item");
        }
        return item;
    }

    private br.csi.apitodolist.model.list.List findOwnedList(Long id) {
        User user = this.userService.getAuthenticatedUser();
        br.csi.apitodolist.model.list.List list = this.listRepository.findByIdAndWorkspaceUserId(id, user.getId());
        if (list == null) {
            throw new ResourceNotFoundException("List");
        }
        return list;
    }
}
