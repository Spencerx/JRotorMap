package pl.edu.elka.ham.JRotorMap.Internal;

/**
 * Created by erxyi on 12.01.17.
 */
public enum ButtonType {
    SAVE_TO_FILE("saveToFile"),
    RESET_MAP("resetMap"),
    GM_QUERY("gmQuery"),
    M_QUERY("mQuery"),
    QRZ_QUERY("qrzQuery"),
    NEW_HOME("newHome");

    private String name;
    ButtonType(String name)
    {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
