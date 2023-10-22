package rasel.aliam;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

import static rasel.aliam.CommandRunner.getAliasList;
import static rasel.aliam.DefaultShell.getDefaultShell;


public class MainController {

    @FXML
    private Label defaultShellText;
    @FXML
    private TableView<Alias> aliasTable;

    @FXML
    protected void initialize() {
        try {
            defaultShellText.setText(
                    "user: " + System.getProperty("user.name") + "\n" +
                            "shell: " + Objects.requireNonNull(getDefaultShell()).name + "\n" +
                            "host: " + InetAddress.getLocalHost().getHostName()
            );
        } catch (UnknownHostException e) {
            System.err.println(e.getMessage());
        }

        aliasTable.setItems(getAliasList());
    }
}
