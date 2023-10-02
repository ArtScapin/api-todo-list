package br.csi.apitodolist.controller;

import br.csi.apitodolist.model.user.User;
import br.csi.apitodolist.model.user.UserData;
import br.csi.apitodolist.model.workspace.Workspace;
import br.csi.apitodolist.service.UserService;
import br.csi.apitodolist.service.WorkspaceService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
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
    public ResponseEntity<Workspace> store(@RequestBody Workspace workspace, UriComponentsBuilder uriComponentsBuilder) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        workspace.setUser(this.userService.findUser(authentication.getName()));
        this.service.create(workspace);
        URI uri = uriComponentsBuilder.path("/workspace/{id}").buildAndExpand(workspace.getId()).toUri();
        return ResponseEntity.created(uri).body(workspace);
    }

    @GetMapping
    public List<Workspace> findMyWorkspaces() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findUser(authentication.getName());
        return this.service.findMyWorkspaces(user.getId());
    }

    @GetMapping("/{id}")
    public Workspace findById(@PathVariable Long id){
        return this.service.findWorkspace(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id){
        this.service.deleteWorkspace(id);
        return ResponseEntity.noContent().build();
    }
}
