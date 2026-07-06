package br.csi.apitodolist.model.list;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<List, Long> {
    java.util.List<List> findByWorkspaceIdAndWorkspaceUserIdOrderByPositionAsc(Long workspaceId, Long userId);
    List findByIdAndWorkspaceUserId(Long listId, Long userId);
}
