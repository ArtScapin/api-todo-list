package br.csi.apitodolist.model.item;

import br.csi.apitodolist.model.list.ListData;

public record ItemData(Long id, String name, Boolean status, ListData list) {
    public ItemData(Item item) {
        this(item.getId(), item.getName(), item.isStatus(), new ListData(item.getList()));
    }
}
