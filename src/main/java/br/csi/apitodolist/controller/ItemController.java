package br.csi.apitodolist.controller;

import br.csi.apitodolist.model.item.Item;
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
    public ResponseEntity<Item> store(@PathVariable Long id, @RequestBody Item item) {
        List list = listService.findList(id);
        if (list != null) {
            item.setList(list);
            item.setStatus(true);
            this.service.create(item);
            return ResponseEntity.status(200).body(item);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Item> update(@PathVariable Long id, @RequestBody Item item) {
        return ResponseEntity.status(200).body(this.service.update(item, id));
    }

    @PutMapping("/change-status/{id}")
    @Transactional
    public ResponseEntity<Item> changeStatus(@PathVariable Long id) {
        return ResponseEntity.status(200).body(this.service.changeStatus(id));
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<java.util.List<Item>> findItemsByList(@PathVariable Long id) {
        List list = listService.findList(id);
        return ResponseEntity.status(200).body(this.service.findItemsByList(list.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> findById(@PathVariable Long id){
        return ResponseEntity.status(200).body(this.service.findItem(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id){
        this.service.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
