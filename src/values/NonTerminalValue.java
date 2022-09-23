package values;

import enums.NonTerminalEnum;

import java.util.*;

/**
 * @author LiYixiao
 */
public class NonTerminalValue {
    /**
     * 忽略的非终结符
     */
    public static final List<NonTerminalEnum> IGNORE_NON_TERMINAL_LIST = Arrays.asList(
            NonTerminalEnum.BlockItem,
            NonTerminalEnum.Decl,
            NonTerminalEnum.BType
    );
    /**
     * 非终结符储存
     */
    public static Stack<Integer> INDEX_STACK = new Stack<>();
    public static Stack<NonTerminalEnum> NON_TERMINAL_STACK = new Stack<>();
    public static Map<Integer, Stack<NonTerminalEnum>> NON_TERMINAL_MAP = new HashMap<>();
}
