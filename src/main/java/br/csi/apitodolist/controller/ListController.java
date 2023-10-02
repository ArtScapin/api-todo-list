package br.csi.apitodolist.controller;

import br.csi.apitodolist.model.list.List;
import br.csi.apitodolist.model.workspace.Workspace;
import br.csi.apitodolist.service.ListService;
import br.csi.apitodolist.service.WorkspaceService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/list")
public class ListController {
    private final ListService service;
    private final WorkspaceService workspaceService;

    public ListController(ListService service, WorkspaceService workspaceService) {
        this.service = service;
        this.workspaceService = workspaceService;
    }

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity<List> store(@PathVariable Long id, @RequestBody List list, UriComponentsBuilder uriComponentsBuilder) {
        Workspace workspace = workspaceService.findWorkspace(id);
        if (workspace != null) {
            list.setWorkspace(workspace);
            list.setStatus(false);
            this.service.create(list);
            URI uri = uriComponentsBuilder.path("/list/{id}").buildAndExpand(workspace.getId()).toUri();
            return ResponseEntity.created(uri).body(list);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all/{id}")
    public java.util.List<List> findListsByWorkspace(@PathVariable Long id) {
        Workspace workspace = workspaceService.findWorkspace(id);
        return this.service.findListsByWorkspace(workspace.getId());
    }

    @GetMapping("/{id}")
    public List findById(@PathVariable Long id){
        return this.service.findList(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id){
        this.service.deleteList(id);
        return ResponseEntity.noContent().build();
    }
}
