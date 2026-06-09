package br.csi.apitodolist.model.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ItemMoveData(
        @NotNull
        @JsonProperty("list_id") Long listId,
        @NotNull
        @PositiveOrZero
        Integer position
) {
}
