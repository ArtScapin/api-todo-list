package br.csi.apitodolist.controller;

import br.csi.apitodolist.model.item.Item;
import br.csi.apitodolist.model.item.ItemMoveData;
import br.csi.apitodolist.service.ItemService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item")
public class ItemController {
    private final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity<Item> store(@PathVariable Long id, @RequestBody @Valid Item item) {
        this.service.create(item, id);
        return ResponseEntity.status(200).body(item);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Item> update(@PathVariable Long id, @RequestBody @Valid Item item) {
        return ResponseEntity.status(200).body(this.service.update(item, id));
    }

    @PatchMapping("/{id}/move")
    @Transactional
    public ResponseEntity<Item> move(@PathVariable Long id, @RequestBody @Valid ItemMoveData moveData) {
        return ResponseEntity.status(200).body(this.service.move(id, moveData));
    }

    @PutMapping("/change-status/{id}")
    @Transactional
    public ResponseEntity<Item> changeStatus(@PathVariable Long id) {
        return ResponseEntity.status(200).body(this.service.changeStatus(id));
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<java.util.List<Item>> findItemsByList(@PathVariable Long id) {
        return ResponseEntity.status(200).body(this.service.findItemsByList(id));
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
