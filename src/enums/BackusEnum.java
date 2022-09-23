package enums;

/**
 * @author LiYixiao
 */
public enum BackusEnum {
    /**
     * Backus范式语法类型
     */
    QUEUE("..."),
    OR("...|..."),
    CHOICE("[...]"),
    MULTI("{...}"),
    RECURSION("A::=B|AC");

    private final String explain;
    BackusEnum(String explain) {
        this.explain = explain;
    }
}
