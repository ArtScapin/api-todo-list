package br.csi.apitodolist.model.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByListId(Long listId);
    Item findByIdAndListWorkspaceUserId(Long id, Long userId);
}
