package br.csi.apitodolist.service;

import br.csi.apitodolist.model.item.Item;
import br.csi.apitodolist.model.item.ItemRepository;
import br.csi.apitodolist.model.list.ListRepository;
import br.csi.apitodolist.model.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository repository;
    private final ListRepository listRepository;
    private final UserService userService;

    public ItemService (ItemRepository repository, UserService userService, ListRepository listRepository) {
        this.repository = repository;
        this.userService = userService;
        this.listRepository = listRepository;
    }

    public void create(Item item) {
        this.repository.save(item);
        br.csi.apitodolist.model.list.List list = item.getList();
        list.setStatus(false);
        this.listRepository.save(list);
    }

    public Item update(Item itemData, Long id) {
        User user = this.userService.getAuthenticatedUser();
        Item item = this.repository.findByIdAndListWorkspaceUserId(id, user.getId());
        item.setName(itemData.getName());
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
        return this.repository.findByListId(id);
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
}
