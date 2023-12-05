package br.csi.apitodolist.model.user;

public record UserData(Long id, String username, String name, String permission) {
    public UserData(User user) {
        this(user.getId(), user.getUsername(), user.getName(), user.getPermission());
    }
}
