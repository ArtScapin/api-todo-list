package br.csi.apitodolist.model.list;

import br.csi.apitodolist.model.item.Item;
import br.csi.apitodolist.model.workspace.Workspace;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Lists")
@Table(name = "lists")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class List {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotNull
    private boolean status;

    @PositiveOrZero
    private Integer position = 0;

    @Pattern(regexp = "^#([A-Fa-f0-9]{3}|[A-Fa-f0-9]{6})$")
    private String color;

    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    @OneToMany(mappedBy = "list")
    @JsonIgnore
    private java.util.List<Item> items;
}
