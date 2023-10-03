package br.csi.apitodolist.model.item;

import br.csi.apitodolist.model.list.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @NotNull
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "list_id")
    private List list;
}
