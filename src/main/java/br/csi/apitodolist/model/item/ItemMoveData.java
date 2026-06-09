package br.csi.apitodolist.model.item;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ItemMoveData(
        @JsonProperty("list_id") Long listId,
        Integer position
) {
}
