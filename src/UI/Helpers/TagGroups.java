package UI.Helpers;

import java.util.HashSet;
import java.util.Set;

public class TagGroups {
    private static final Set<String> TEXT_TAGS = new HashSet<>();
    private static final Set<String> BLOCK_TAGS = new HashSet<>();
    private static final Set<String> BUTTON_TAGS = new HashSet<>();

    static {
        // text tags
        TEXT_TAGS.add("H1");
        TEXT_TAGS.add("H2");
        TEXT_TAGS.add("H3");
        TEXT_TAGS.add("P");
        TEXT_TAGS.add("LABEL");

        // block tags
        BLOCK_TAGS.add("DIV");
        BLOCK_TAGS.add("FORM");
        BLOCK_TAGS.add("HEADER");
 
        BUTTON_TAGS.add("INPUT");
        BUTTON_TAGS.add("BUTTON");
    }

    public static boolean isTextTag(String tagName) {
        return TEXT_TAGS.contains(tagName);
    }

    public static boolean isBlockTag(String tagName) {
        return BLOCK_TAGS.contains(tagName);
    }
    public static boolean isButtonTag(String tagName) {
        return BUTTON_TAGS.contains(tagName);
    }
}
