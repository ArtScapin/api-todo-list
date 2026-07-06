package br.csi.apitodolist.controller;

import br.csi.apitodolist.model.list.List;
import br.csi.apitodolist.service.ListService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/list")
public class ListController {
    private final ListService service;

    public ListController(ListService service) {
        this.service = service;
    }

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity<List> store(@PathVariable Long id, @RequestBody @Valid List list) {
        this.service.create(list, id);
        return ResponseEntity.status(201).body(list);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<List> update(@PathVariable Long id, @RequestBody @Valid List list) {
        return ResponseEntity.status(201).body(this.service.update(list, id));
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<java.util.List<List>> findListsByWorkspace(@PathVariable Long id) {
        return ResponseEntity.status(200).body(this.service.findListsByWorkspace(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List> findById(@PathVariable Long id){
        return ResponseEntity.status(200).body(this.service.findList(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id){
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
