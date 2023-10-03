package br.csi.apitodolist.service;

import br.csi.apitodolist.model.item.Item;
import br.csi.apitodolist.model.item.ItemRepository;
import br.csi.apitodolist.model.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository repository;
    private final UserService userService;

    public ItemService (ItemRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    public void create(Item item) {
        this.repository.save(item);
    }

    public List<Item> findItemsByList(Long id) {
        return this.repository.findByListId(id);
    }

    public Item findItem(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findUser(authentication.getName());
        return this.repository.findByIdAndListWorkspaceUserId(id, user.getId());
    }

    public void deleteItem(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findUser(authentication.getName());
        Item item = this.repository.findByIdAndListWorkspaceUserId(id, user.getId());
        this.repository.delete(item);
    }
}
