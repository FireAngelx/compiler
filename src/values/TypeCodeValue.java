package values;

import enums.TypeCodeEnum;

import java.util.*;

/**
 * @author LiYixiao
 */
public class TypeCodeValue {
    /**
     * 忽略的类别码
     */
    public static final List<TypeCodeEnum> IGNORE_TYPE_CODE_LIST = Arrays.asList(
            TypeCodeEnum.SINGLE_ANNOTATION,
            TypeCodeEnum.DOUBLE_ANNOTATION
    );
    /**
     * 类别码储存
     */
    public static List<TypeCodeEnum> TYPE_CODE_LIST = new ArrayList<>();
    public static List<String> TYPE_CODE_PRINT_LIST = new ArrayList<>();
    /**
     * 字符对类别码顺序表
     */
    public static final Map<Character, List<TypeCodeEnum>> ORDER_MAP = new HashMap<Character, List<TypeCodeEnum>>() {{
        put('_', Arrays.asList(TypeCodeEnum.IDENFR));
        put('0', Arrays.asList(TypeCodeEnum.IDENFR, TypeCodeEnum.INTCON));
        put('1', Arrays.asList(TypeCodeEnum.IDENFR, TypeCodeEnum.INTCON));
        put('2', Arrays.asList(TypeCodeEnum.IDENFR, TypeCodeEnum.INTCON));
        put('3', Arrays.asList(TypeCodeEnum.IDENFR, TypeCodeEnum.INTCON));
        put('4', Arrays.asList(TypeCodeEnum.IDENFR, TypeCodeEnum.INTCON));
        put('5', Arrays.asList(TypeCodeEnum.IDENFR, TypeCodeEnum.INTCON));
        put('6', Arrays.asList(TypeCodeEnum.IDENFR, TypeCodeEnum.INTCON));
        put('7', Arrays.asList(TypeCodeEnum.IDENFR, TypeCodeEnum.INTCON));
        put('8', Arrays.asList(TypeCodeEnum.IDENFR, TypeCodeEnum.INTCON));
        put('9', Arrays.asList(TypeCodeEnum.IDENFR, TypeCodeEnum.INTCON));
        put('a', Arrays.asList(TypeCodeEnum.IDENFR));
        put('b', Arrays.asList(TypeCodeEnum.BREAKTK, TypeCodeEnum.IDENFR));
        put('c', Arrays.asList(TypeCodeEnum.CONSTTK, TypeCodeEnum.CONTINUETK, TypeCodeEnum.IDENFR));
        put('d', Arrays.asList(TypeCodeEnum.IDENFR));
        put('e', Arrays.asList(TypeCodeEnum.ELSETK, TypeCodeEnum.IDENFR));
        put('f', Arrays.asList(TypeCodeEnum.IDENFR));
        put('g', Arrays.asList(TypeCodeEnum.GETINTTK, TypeCodeEnum.IDENFR));
        put('h', Arrays.asList(TypeCodeEnum.IDENFR));
        put('i', Arrays.asList(TypeCodeEnum.INTTK, TypeCodeEnum.IFTK, TypeCodeEnum.IDENFR));
        put('j', Arrays.asList(TypeCodeEnum.IDENFR));
        put('k', Arrays.asList(TypeCodeEnum.IDENFR));
        put('l', Arrays.asList(TypeCodeEnum.IDENFR));
        put('m', Arrays.asList(TypeCodeEnum.MAINTK, TypeCodeEnum.IDENFR));
        put('n', Arrays.asList(TypeCodeEnum.IDENFR));
        put('o', Arrays.asList(TypeCodeEnum.IDENFR));
        put('p', Arrays.asList(TypeCodeEnum.PRINTFTK, TypeCodeEnum.IDENFR));
        put('q', Arrays.asList(TypeCodeEnum.IDENFR));
        put('r', Arrays.asList(TypeCodeEnum.RETURNTK, TypeCodeEnum.IDENFR));
        put('s', Arrays.asList(TypeCodeEnum.IDENFR));
        put('t', Arrays.asList(TypeCodeEnum.IDENFR));
        put('u', Arrays.asList(TypeCodeEnum.IDENFR));
        put('v', Arrays.asList(TypeCodeEnum.VOIDTK, TypeCodeEnum.IDENFR));
        put('w', Arrays.asList(TypeCodeEnum.WHILETK, TypeCodeEnum.IDENFR));
        put('x', Arrays.asList(TypeCodeEnum.IDENFR));
        put('y', Arrays.asList(TypeCodeEnum.IDENFR));
        put('z', Arrays.asList(TypeCodeEnum.IDENFR));
        put('A', Arrays.asList(TypeCodeEnum.IDENFR));
        put('B', Arrays.asList(TypeCodeEnum.IDENFR));
        put('C', Arrays.asList(TypeCodeEnum.IDENFR));
        put('D', Arrays.asList(TypeCodeEnum.IDENFR));
        put('E', Arrays.asList(TypeCodeEnum.IDENFR));
        put('F', Arrays.asList(TypeCodeEnum.IDENFR));
        put('G', Arrays.asList(TypeCodeEnum.IDENFR));
        put('H', Arrays.asList(TypeCodeEnum.IDENFR));
        put('I', Arrays.asList(TypeCodeEnum.IDENFR));
        put('J', Arrays.asList(TypeCodeEnum.IDENFR));
        put('K', Arrays.asList(TypeCodeEnum.IDENFR));
        put('L', Arrays.asList(TypeCodeEnum.IDENFR));
        put('M', Arrays.asList(TypeCodeEnum.IDENFR));
        put('N', Arrays.asList(TypeCodeEnum.IDENFR));
        put('O', Arrays.asList(TypeCodeEnum.IDENFR));
        put('P', Arrays.asList(TypeCodeEnum.IDENFR));
        put('Q', Arrays.asList(TypeCodeEnum.IDENFR));
        put('R', Arrays.asList(TypeCodeEnum.IDENFR));
        put('S', Arrays.asList(TypeCodeEnum.IDENFR));
        put('T', Arrays.asList(TypeCodeEnum.IDENFR));
        put('U', Arrays.asList(TypeCodeEnum.IDENFR));
        put('V', Arrays.asList(TypeCodeEnum.IDENFR));
        put('W', Arrays.asList(TypeCodeEnum.IDENFR));
        put('X', Arrays.asList(TypeCodeEnum.IDENFR));
        put('Y', Arrays.asList(TypeCodeEnum.IDENFR));
        put('Z', Arrays.asList(TypeCodeEnum.IDENFR));
        put('&', Arrays.asList(TypeCodeEnum.AND));
        put('|', Arrays.asList(TypeCodeEnum.OR));
        put('!', Arrays.asList(TypeCodeEnum.NEQ, TypeCodeEnum.NOT));
        put('=', Arrays.asList(TypeCodeEnum.EQL, TypeCodeEnum.ASSIGN));
        put('<', Arrays.asList(TypeCodeEnum.LEQ, TypeCodeEnum.LSS));
        put('>', Arrays.asList(TypeCodeEnum.GEQ, TypeCodeEnum.GRE));
        put('\"', Arrays.asList(TypeCodeEnum.STRCON));
        put('+', Arrays.asList(TypeCodeEnum.PLUS));
        put('-', Arrays.asList(TypeCodeEnum.MINU));
        put('*', Arrays.asList(TypeCodeEnum.MULT));
        put('/', Arrays.asList(TypeCodeEnum.SINGLE_ANNOTATION, TypeCodeEnum.DOUBLE_ANNOTATION, TypeCodeEnum.DIV));
        put('%', Arrays.asList(TypeCodeEnum.MOD));
        put(';', Arrays.asList(TypeCodeEnum.SEMICN));
        put(',', Arrays.asList(TypeCodeEnum.COMMA));
        put('(', Arrays.asList(TypeCodeEnum.LPARENT));
        put(')', Arrays.asList(TypeCodeEnum.RPARENT));
        put('[', Arrays.asList(TypeCodeEnum.LBRACK));
        put(']', Arrays.asList(TypeCodeEnum.RBRACK));
        put('{', Arrays.asList(TypeCodeEnum.LBRACE));
        put('}', Arrays.asList(TypeCodeEnum.RBRACE));
    }};
}
