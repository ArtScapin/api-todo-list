package br.csi.apitodolist.controller;

import br.csi.apitodolist.model.item.Item;
import br.csi.apitodolist.model.list.List;
import br.csi.apitodolist.model.workspace.Workspace;
import br.csi.apitodolist.service.ItemService;
import br.csi.apitodolist.service.ListService;
import br.csi.apitodolist.service.WorkspaceService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/item")
public class ItemController {
    private final ItemService service;
    private final ListService listService;
    private final WorkspaceService workspaceService;

    public ItemController(ItemService service, ListService listService, WorkspaceService workspaceService) {
        this.service = service;
        this.listService = listService;
        this.workspaceService = workspaceService;
    }

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity<Item> store(@PathVariable Long id, @RequestBody Item item, UriComponentsBuilder uriComponentsBuilder) {
        List list = listService.findList(id);
        Workspace workspace = workspaceService.findWorkspace(list.getWorkspace().getId());
        if (workspace != null) {
            item.setList(list);
            item.setStatus(false);
            this.service.create(item);
            URI uri = uriComponentsBuilder.path("/item/{id}").buildAndExpand(item.getId()).toUri();
            return ResponseEntity.created(uri).body(item);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all/{id}")
    public java.util.List<Item> findItemsBylist(@PathVariable Long id) {
        List list = listService.findList(id);
        Workspace workspace = workspaceService.findWorkspace(list.getWorkspace().getId());
        return this.service.findItemsByList(list.getId());
    }

    @GetMapping("/{id}")
    public Item findById(@PathVariable Long id){
        return this.service.findItem(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id){
        this.service.deleteItem(id);
        return ResponseEntity.noContent().build();
    }
}
