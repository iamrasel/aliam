package rasel.aliam;

import java.io.BufferedReader;
import java.io.FileReader;


public class DefaultShell {

    protected String name;
    protected String path;

    // Function to get the default shell
    protected static DefaultShell getDefaultShell() {
        DefaultShell shell = new DefaultShell();

        try (BufferedReader br = new BufferedReader(new FileReader("/etc/passwd"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length >= 7 && parts[0].equals(System.getProperty("user.name"))) {
                    shell.path = parts[6];
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        if (shell.path != null) {
            if (shell.path.contains("/bin/bash"))
                shell.name = "bash";
            else if (shell.path.contains("/bin/zsh"))
                shell.name = "zsh";
            else if (shell.path.contains("/bin/fish"))
                shell.name = "fish";
            else if (shell.path.contains("/bin/sh"))
                shell.name = "dash";

            return shell;
        } else {
            return null;
        }
    }
}
