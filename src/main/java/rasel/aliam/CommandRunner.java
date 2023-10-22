package rasel.aliam;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.Objects;

import static rasel.aliam.DefaultShell.getDefaultShell;


@SuppressWarnings("ResultOfMethodCallIgnored")
public class CommandRunner {

    protected static ObservableList<Alias> getAliasList() {
        ObservableList<Alias> list = FXCollections.observableArrayList();
        int index = 1;

        try {
            // Create a temporary file to store the script
            File scriptFile = File.createTempFile("list_aliases", ".sh");

            Process process = getProcess(scriptFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                if (line.contains("=")) {
                    String[] parts = line.split("=");
                    if (parts[0].equals(parts[0].toLowerCase())) {
                        list.add(new Alias(index, parts[0].replace("alias ", ""), parts[1].replace("'", ""), "✔")); // ✗✚✎
                        index++;
                    }
                }
            }

            // Wait for the process to finish and,
            // delete the temporary script file
            process.waitFor();
            scriptFile.delete();
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }

        return list;
    }

    private static Process getProcess(File scriptFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scriptFile))) {

            writer.write("    #!" + Objects.requireNonNull(getDefaultShell()).path + "\n");
            // Determine the user's default shell
            writer.write("    user_shell=$(getent passwd $USER | cut -d: -f7)         \n");
            // Load the user's shell configuration file based on the detected shell
            writer.write("    if [ -n \"$user_shell\" ]; then                         \n");
            writer.write("        case \"$user_shell\" in                             \n");
            writer.write("            */bash)                                         \n");
            writer.write("                config_file=\"$HOME/.bashrc\"               \n");
            writer.write("                ;;                                          \n");
            writer.write("            */zsh)                                          \n");
            writer.write("                config_file=\"$HOME/.zshrc\"                \n");
            writer.write("                ;;                                          \n");
            writer.write("        esac                                                \n");
            writer.write("        if [ -f \"$config_file\" ]; then                    \n");
            writer.write("            source \"$config_file\"                         \n");
            writer.write("        fi                                                  \n");
            writer.write("    fi                                                      \n");
            // Finally get the aliases
            writer.write("    alias                                                   \n");

        }

        scriptFile.setExecutable(true);

        ProcessBuilder processBuilder = new ProcessBuilder(Objects.requireNonNull(getDefaultShell()).path, "-i", scriptFile.getAbsolutePath());
        processBuilder.redirectErrorStream(true);

        return processBuilder.start();
    }
}
