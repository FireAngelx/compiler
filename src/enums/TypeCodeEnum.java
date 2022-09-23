package enums;

/**
 * @author LiYixiao
 */
public enum TypeCodeEnum {
    /**
     * 类别码对单词正则
     */
    IDENFR("^[a-zA-Z_]\\w*"),
    INTCON("^([1-9]\\d*|0)"),
    STRCON("^\".*?\""),
    MAINTK("^main(?![\\w])"),
    CONSTTK("^const(?![\\w])"),
    INTTK("^int(?![\\w])"),
    BREAKTK("^break(?![\\w])"),
    CONTINUETK("^continue(?![\\w])"),
    IFTK("^if(?![\\w])"),
    ELSETK("^else(?![\\w])"),
    NOT("^!"),
    AND("^&&"),
    OR("^\\|\\|"),
    WHILETK("^while(?![\\w])"),
    GETINTTK("^getint(?![\\w])"),
    PRINTFTK("^printf(?![\\w])"),
    RETURNTK("^return(?![\\w])"),
    PLUS("^\\+"),
    MINU("-"),
    VOIDTK("^void(?![\\w])"),
    MULT("^\\*"),
    DIV("^/"),
    MOD("^%"),
    LSS("^<"),
    LEQ("^<="),
    GRE("^>"),
    GEQ("^>="),
    EQL("^=="),
    NEQ("^!="),
    ASSIGN("^="),
    SEMICN("^;"),
    COMMA("^,"),
    LPARENT("^\\("),
    RPARENT("^\\)"),
    LBRACK("^\\["),
    RBRACK("^\\]"),
    LBRACE("^\\{"),
    RBRACE("^\\}"),
    SINGLE_ANNOTATION("^//[\\s\\S]*?(?=[\\n])"),
    DOUBLE_ANNOTATION("^/\\*[\\s\\S]*?\\*/");

    /**
     * 正则表达式
     */
    private final String re;

    TypeCodeEnum(String re) {
        this.re = re;
    }

    public String getRe() {
        return re;
    }
}
