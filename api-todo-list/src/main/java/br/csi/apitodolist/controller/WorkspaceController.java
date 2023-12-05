package br.csi.apitodolist.controller;

import br.csi.apitodolist.model.workspace.Workspace;
import br.csi.apitodolist.model.workspace.WorkspaceData;
import br.csi.apitodolist.service.UserService;
import br.csi.apitodolist.service.WorkspaceService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/workspace")
public class WorkspaceController  {
    private final WorkspaceService service;
    private final UserService userService;

    public WorkspaceController(WorkspaceService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<WorkspaceData> store(@RequestBody @Valid Workspace workspace) {
        workspace.setUser(this.userService.getAuthenticatedUser());
        this.service.create(workspace);
        WorkspaceData workspaceData = new WorkspaceData(workspace);
        return ResponseEntity.status(201).body(workspaceData);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<WorkspaceData> update(@PathVariable Long id, @RequestBody @Valid Workspace workspace) {
        WorkspaceData workspaceData = new WorkspaceData(this.service.update(workspace, id));
        return ResponseEntity.status(200).body(workspaceData);
    }

    @GetMapping
    public ResponseEntity<List<WorkspaceData>> findMyWorkspaces() {
        List<Workspace> workspaces = this.service.findMyWorkspaces();
        List<WorkspaceData> workspacesData = workspaces.stream().map(WorkspaceData::new).toList();
        return ResponseEntity.status(200).body(workspacesData);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkspaceData> findById(@PathVariable Long id){
        Workspace workspace = this.service.findWorkspace(id);
        WorkspaceData workspaceData = new WorkspaceData(workspace);
        return ResponseEntity.status(200).body(workspaceData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id){
        this.service.deleteWorkspace(id);
        return ResponseEntity.noContent().build();
    }
}
