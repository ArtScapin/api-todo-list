package br.csi.apitodolist.model.workspace;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
    List<Workspace> findByUserId(Long userId);
    Workspace findByIdAndUserId(Long id, Long userId);
}
