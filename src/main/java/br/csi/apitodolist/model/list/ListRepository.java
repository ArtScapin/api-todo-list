package br.csi.apitodolist.model.list;

import br.csi.apitodolist.model.workspace.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<List, Long> {
    java.util.List<List> findByWorkspaceId(Long workspaceId);
    List findByIdAndWorkspaceUserId(Long listId, Long userId);
}
