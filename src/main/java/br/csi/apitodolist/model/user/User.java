package br.csi.apitodolist.model.user;

import br.csi.apitodolist.model.workspace.Workspace;
import br.csi.apitodolist.service.ConsoleLogger;
import br.csi.apitodolist.service.Observer;
import br.csi.apitodolist.service.Subject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Users")
@Table(name = "users")
@Setter
@Getter
public class User implements Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String username;

    @NotNull
    private String password;

    private String permission;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Workspace> workspaces;

    public User() {
        this.attach(new ConsoleLogger());
    }

    public User(Long id, String name, String username, String password, String permission, List<Workspace> workspaces) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.permission = permission;
        this.workspaces = workspaces;
        this.attach(new ConsoleLogger());
    }

    @Transient()
    private List<Observer> loggers = new ArrayList<>();

    @Override
    public void attach(Observer logger) {
        loggers.add(logger);
    }

    @Override
    public void detach(Observer logger) {
        loggers.remove(logger);
    }

    @Override
    public void notifyLoggers(String message) {
        for (Observer logger : loggers) {
            logger.log(message);
        }
    }
}
