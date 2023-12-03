package br.csi.apitodolist.controller;

import br.csi.apitodolist.model.item.Item;
import br.csi.apitodolist.model.item.ItemData;
import br.csi.apitodolist.model.list.List;
import br.csi.apitodolist.service.ItemService;
import br.csi.apitodolist.service.ListService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item")
public class ItemController {
    private final ItemService service;
    private final ListService listService;

    public ItemController(ItemService service, ListService listService) {
        this.service = service;
        this.listService = listService;
    }

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity<ItemData> store(@PathVariable Long id, @RequestBody Item item) {
        List list = listService.findList(id);
        if (list != null) {
            item.setList(list);
            item.setStatus(false);
            this.service.create(item);
            ItemData itemData = new ItemData(item);
            return ResponseEntity.status(200).body(itemData);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ItemData> update(@PathVariable Long id, @RequestBody Item item) {
        ItemData itemData = new ItemData(this.service.update(item, id));
        return ResponseEntity.status(200).body(itemData);
    }

    @PutMapping("/change-status/{id}")
    @Transactional
    public ResponseEntity<ItemData> changeStatus(@PathVariable Long id) {
        Item item = this.service.changeStatus(id);
        ItemData itemData = new ItemData(item);
        return ResponseEntity.status(200).body(itemData);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<java.util.List<ItemData>> findItemsByList(@PathVariable Long id) {
        List list = listService.findList(id);
        java.util.List<Item> items = this.service.findItemsByList(list.getId());
        java.util.List<ItemData> itemsData = items.stream().map(ItemData::new).toList();
        return ResponseEntity.status(200).body(itemsData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemData> findById(@PathVariable Long id){
        Item item = this.service.findItem(id);
        ItemData itemData = new ItemData(item);
        return ResponseEntity.status(200).body(itemData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id){
        this.service.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
