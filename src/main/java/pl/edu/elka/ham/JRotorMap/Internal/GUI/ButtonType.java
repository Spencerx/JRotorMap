package pl.edu.elka.ham.JRotorMap.Internal.GUI;

/**
 * Idea of this class is to abstract from working on Button name string to enum - it's simpler and easier to maintain.
 */
public enum ButtonType {
    SAVE_TO_FILE("saveToFile"),
    RESET_MAP("resetMap"),
    GM_QUERY("gmQuery"),
    M_QUERY("mQuery"),
    QRZ_QUERY("qrzQuery"),
    NEW_HOME("newHome");

    private String name;

    /**
     * @param name Sets object name to param.
     */
    ButtonType(String name)
    {
        this.name = name;
    }

    /**
     * @return returns object name string.
     */
    public String getName() {
        return name;
    }
}
