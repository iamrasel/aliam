package rasel.aliam;


public class Alias {

    private Integer index;
    private String alias;
    private String command;
    private String status;

    public Alias() {}

    public Alias(Integer index, String alias, String command, String status) {
        this.index = index;
        this.alias = alias;
        this.command = command;
        this.status = status;
    }

    public Integer getIndex() { return index; }
    public void setIndex(Integer index) { this.index = index; }

    public String getAlias() { return alias; }
    public void setAlias(String alias) { this.alias = alias; }

    public String getCommand() { return command; }
    public void setCommand(String command) { this.command = command; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
