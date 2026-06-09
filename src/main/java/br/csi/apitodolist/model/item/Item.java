package br.csi.apitodolist.model.item;

import br.csi.apitodolist.model.list.List;
import br.csi.apitodolist.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "Items")
@Table(name = "items")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.MEDIUM;

    @NotNull
    private boolean status;

    @NotNull
    private Integer position = 0;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"password", "workspaces", "loggers"})
    private User assignee;

    @ManyToOne
    @JoinColumn(name = "list_id")
    private List list;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @JsonProperty("user_id")
    public Long getUserId() {
        return this.assignee != null ? this.assignee.getId() : null;
    }

    @JsonProperty("user_id")
    public void setUserId(Long userId) {
        if (userId == null) {
            this.assignee = null;
            return;
        }

        User user = new User();
        user.setId(userId);
        this.assignee = user;
    }
}
