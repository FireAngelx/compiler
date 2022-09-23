package procedures;

import beans.BackusBean;
import enums.BackusEnum;
import enums.NonTerminalEnum;
import enums.TypeCodeEnum;
import utils.BackusUtils;
import values.NonTerminalValue;
import values.TypeCodeValue;

import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

/**
 * @author LiYixiao
 */
public class Syntax {
    /**
     * 语法分析函数
     */
    public static void syntaxAnalyse() {
        // 分析类别码列表，从而获取非终结符的索引栈和名称栈
        check(NonTerminalEnum.CompUnit, 0);
        // 不是空就出栈，将栈中内容输出到以索引为键的栈中
        while (!NonTerminalValue.INDEX_STACK.isEmpty()) {
            Integer index = NonTerminalValue.INDEX_STACK.pop();
            NonTerminalEnum nonTerminal = NonTerminalValue.NON_TERMINAL_STACK.pop();
            if(!NonTerminalValue.NON_TERMINAL_MAP.containsKey(index)) {
                NonTerminalValue.NON_TERMINAL_MAP.put(index, new Stack<>());
            }
            NonTerminalValue.NON_TERMINAL_MAP.get(index).push(nonTerminal);
        }
    }

    /**
     * 将索引和非终结符压栈
     * @param index       索引
     * @param nonTerminal 非终结符
     */
    public static void pushNonTerminal(Integer index, NonTerminalEnum nonTerminal) {
        NonTerminalValue.INDEX_STACK.push(index);
        NonTerminalValue.NON_TERMINAL_STACK.push(nonTerminal);
    }

    /**
     * 将超过此索引的非终结符弹出栈
     * @param index 索引
     */
    public static void popNonTerminal(Integer index) {
        while (!NonTerminalValue.INDEX_STACK.isEmpty() && NonTerminalValue.INDEX_STACK.peek() > index) {
            NonTerminalValue.INDEX_STACK.pop();
            NonTerminalValue.NON_TERMINAL_STACK.pop();
        }
    }

    /**
     * 检查栈规则
     * @param res   终止索引
     * @param index 起始索引
     * @param obj   非终结符
     * @return  终止索引
     */
    public static Integer stackCheck(Integer res, Integer index, NonTerminalEnum obj) {
        if (res.equals(-1)) {
            popNonTerminal(index);
            return -1;
        }
        pushNonTerminal(res, obj);
        return res;
    }

    /**
     * 语义分析递归
     * @param obj   对象
     * @param index 起始索引
     * @return 终止索引
     */
    public static Integer check(Object obj, Integer index) {
        Integer res;
        if (obj instanceof TypeCodeEnum) {
            if (obj.equals(TypeCodeValue.TYPE_CODE_LIST.get(index))) {
                return index + 1;
            } else {
                popNonTerminal(index);
                return -1;
            }
        } else if (obj instanceof NonTerminalEnum) {
            NonTerminalEnum nonTerminal = (NonTerminalEnum) (obj);
            switch (nonTerminal) {
                case CompUnit:
                    // {Decl} {FuncDef} MainFuncDef
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.QUEUE,
                            Arrays.asList(
                                    new BackusBean(BackusEnum.MULTI, Collections.singletonList(
                                            NonTerminalEnum.Decl
                                    )),
                                    new BackusBean(BackusEnum.MULTI, Collections.singletonList(
                                            NonTerminalEnum.FuncDef
                                    )),
                                    NonTerminalEnum.MainFuncDef
                            )
                    ), index);
                    return stackCheck(res, index, nonTerminal);
                case Decl:
                    // ConstDecl | VarDecl
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.OR,
                            Arrays.asList(
                                    NonTerminalEnum.ConstDecl,
                                    NonTerminalEnum.VarDecl
                            )
                    ), index);
                    return stackCheck(res, index, nonTerminal);
                case ConstDecl:
                    // 'const' BType ConstDef {',' ConstDef} ';'
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.QUEUE,
                            Arrays.asList(
                                    TypeCodeEnum.CONSTTK,
                                    NonTerminalEnum.BType,
                                    NonTerminalEnum.ConstDef,
                                    new BackusBean(BackusEnum.MULTI, Arrays.asList(
                                            TypeCodeEnum.COMMA,
                                            NonTerminalEnum.ConstDef
                                    )),
                                    TypeCodeEnum.SEMICN
                            )
                    ), index);
                    return stackCheck(res, index, nonTerminal);
                case BType:
                    // 'int'
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.QUEUE,
                            Collections.singletonList(
                                    TypeCodeEnum.INTTK
                            )
                    ), index);
                    return stackCheck(res, index, nonTerminal);
                case ConstDef:
                    // Ident {'[' ConstExp ']'} '=' ConstInitVal
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.QUEUE,
                            Arrays.asList(
                                    TypeCodeEnum.IDENFR,
                                    new BackusBean(BackusEnum.MULTI, Arrays.asList(
                                            TypeCodeEnum.LBRACK,
                                            NonTerminalEnum.ConstExp,
                                            TypeCodeEnum.RBRACK
                                    )),
                                    TypeCodeEnum.ASSIGN,
                                    NonTerminalEnum.ConstInitVal
                            )
                    ), index);
                    return stackCheck(res, index, nonTerminal);
                case ConstInitVal:
                    // ConstExp | '{' [ConstInitVal {',' ConstInitVal}] '}'
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.OR,
                            Arrays.asList(
                                    NonTerminalEnum.ConstExp,
                                    new BackusBean(BackusEnum.QUEUE, Arrays.asList(
                                            TypeCodeEnum.LBRACE,
                                            new BackusBean(BackusEnum.CHOICE, Arrays.asList(
                                                    NonTerminalEnum.ConstInitVal,
                                                    new BackusBean(BackusEnum.MULTI, Arrays.asList(
                                                            TypeCodeEnum.COMMA,
                                                            NonTerminalEnum.ConstInitVal
                                                    ))
                                            )),
                                            TypeCodeEnum.RBRACE
                                    ))
                            )
                    ), index);
                    return stackCheck(res, index, nonTerminal);
                case VarDecl:
                    // BType VarDef {',' VarDef} ';'
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.QUEUE,
                            Arrays.asList(
                                    NonTerminalEnum.BType,
                                    NonTerminalEnum.VarDef,
                                    new BackusBean(BackusEnum.MULTI, Arrays.asList(
                                            TypeCodeEnum.COMMA,
                                            NonTerminalEnum.VarDef
                                    )),
                                    TypeCodeEnum.SEMICN
                            )
                    ), index);
                    return stackCheck(res, index, nonTerminal);
                case VarDef:
                    // Ident {'[' ConstExp ']'} ['=' InitVal]
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.QUEUE,
                            Arrays.asList(
                                    TypeCodeEnum.IDENFR,
                                    new BackusBean(BackusEnum.MULTI, Arrays.asList(
                                            TypeCodeEnum.LBRACK,
                                            NonTerminalEnum.ConstExp,
                                            TypeCodeEnum.RBRACK
                                    )),
                                    new BackusBean(BackusEnum.CHOICE, Arrays.asList(
                                            TypeCodeEnum.ASSIGN,
                                            NonTerminalEnum.InitVal
                                    ))
                            )
                    ), index);
                    return stackCheck(res, index, nonTerminal);
                case InitVal:
                    // Exp | '{' [InitVal {',' InitVal}] '}'
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.OR,
                            Arrays.asList(
                                    NonTerminalEnum.Exp,
                                    new BackusBean(BackusEnum.QUEUE, Arrays.asList(
                                            TypeCodeEnum.LBRACE,
                                            new BackusBean(BackusEnum.CHOICE, Arrays.asList(
                                                    NonTerminalEnum.InitVal,
                                                    new BackusBean(BackusEnum.MULTI, Arrays.asList(
                                                            TypeCodeEnum.COMMA,
                                                            NonTerminalEnum.InitVal
                                                    ))
                                            )),
                                            TypeCodeEnum.RBRACE
                                    ))
                            )
                    ), index);
                    return stackCheck(res, index, nonTerminal);
                case FuncDef:
                    // FuncType Ident '(' [FuncFParams] ')' Block
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.QUEUE,
                            Arrays.asList(
                                    NonTerminalEnum.FuncType,
                                    TypeCodeEnum.IDENFR,
                                    TypeCodeEnum.LPARENT,
                                    new BackusBean(BackusEnum.CHOICE, Collections.singletonList(
                                            NonTerminalEnum.FuncFParams
                                    )),
                                    TypeCodeEnum.RPARENT,
                                    NonTerminalEnum.Block
                            )
                    ), index);
                    return stackCheck(res, index, nonTerminal);
                case MainFuncDef:
                    // 'int' 'main' '(' ')' Block
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.QUEUE,
                            Arrays.asList(
                                    TypeCodeEnum.INTTK,
                                    TypeCodeEnum.MAINTK,
                                    TypeCodeEnum.LPARENT,
                                    TypeCodeEnum.RPARENT,
                                    NonTerminalEnum.Block
                            )
                    ), index);
                    return stackCheck(res, index, nonTerminal);
                case FuncType:
                    // 'void' | 'int'
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.OR,
                            Arrays.asList(
                                    TypeCodeEnum.VOIDTK,
                                    TypeCodeEnum.INTTK
                            )
                    ), index);
                    return stackCheck(res, index, nonTerminal);
                case FuncFParams:
                    // FuncFParam { ',' FuncFParam }
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.QUEUE,
                            Arrays.asList(
                                    NonTerminalEnum.FuncFParam,
                                    new BackusBean(BackusEnum.MULTI, Arrays.asList(
                                            TypeCodeEnum.COMMA,
                                            NonTerminalEnum.FuncFParam
                                    ))
                            )
                    ), index);
                    return stackCheck(res, index, nonTerminal);
                case FuncFParam:
                    // BType Ident ['[' ']' {'[' ConstExp ']'}]
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.QUEUE,
                            Arrays.asList(
                                    NonTerminalEnum.BType,
                                    TypeCodeEnum.IDENFR,
                                    new BackusBean(BackusEnum.CHOICE, Arrays.asList(
                                            TypeCodeEnum.LBRACK,
                                            TypeCodeEnum.RBRACK,
                                            new BackusBean(BackusEnum.MULTI, Arrays.asList(
                                                    TypeCodeEnum.LBRACK,
                                                    NonTerminalEnum.ConstExp,
                                                    TypeCodeEnum.RBRACK
                                            ))
                                    ))
                            )
                    ), index);
                    return stackCheck(res, index, nonTerminal);
                case Block:
                    // '{' {BlockItem} '}'
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.QUEUE,
                            Arrays.asList(
                                    TypeCodeEnum.LBRACE,
                                    new BackusBean(BackusEnum.MULTI, Collections.singletonList(
                                            NonTerminalEnum.BlockItem
                                    )),
                                    TypeCodeEnum.RBRACE
                            )
                    ), index);
                    return stackCheck(res, index, nonTerminal);
                case BlockItem:
                    // Decl | Stmt
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.OR,
                            Arrays.asList(
                                    NonTerminalEnum.Decl,
                                    NonTerminalEnum.Stmt
                            )
                    ), index);
                    return stackCheck(res, index, nonTerminal);
                case Stmt:
                    // LVal '=' Exp ';'
                    // | [Exp] ';'
                    // | Block
                    // | 'if' '(' Cond ')' Stmt ['else' Stmt]
                    // | 'while' '(' Cond ')' Stmt
                    // | 'break' ';'
                    // | 'continue' ';'
                    // | 'return' [Exp] ';'
                    // | LVal '=' 'getint''('')'';'
                    // | 'printf''('FormatString{','Exp}')'';'
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.OR,
                            Arrays.asList(
                                    new BackusBean(BackusEnum.QUEUE, Arrays.asList(
                                            NonTerminalEnum.LVal,
                                            TypeCodeEnum.ASSIGN,
                                            NonTerminalEnum.Exp,
                                            TypeCodeEnum.SEMICN
                                    )),
                                    new BackusBean(BackusEnum.QUEUE, Arrays.asList(
                                            new BackusBean(BackusEnum.CHOICE, Collections.singletonList(
                                                    NonTerminalEnum.Exp
                                            )),
                                            TypeCodeEnum.SEMICN
                                    )),
                                    NonTerminalEnum.Block,
                                    new BackusBean(BackusEnum.QUEUE, Arrays.asList(
                                            TypeCodeEnum.IFTK,
                                            TypeCodeEnum.LPARENT,
                                            NonTerminalEnum.Cond,
                                            TypeCodeEnum.RPARENT,
                                            NonTerminalEnum.Stmt,
                                            new BackusBean(BackusEnum.CHOICE, Arrays.asList(
                                                    TypeCodeEnum.ELSETK,
                                                    NonTerminalEnum.Stmt
                                            ))
                                    )),
                                    new BackusBean(BackusEnum.QUEUE, Arrays.asList(
                                            TypeCodeEnum.WHILETK,
                                            TypeCodeEnum.LPARENT,
                                            NonTerminalEnum.Cond,
                                            TypeCodeEnum.RPARENT,
                                            NonTerminalEnum.Stmt
                                    )),
                                    new BackusBean(BackusEnum.QUEUE, Arrays.asList(
                                            TypeCodeEnum.BREAKTK,
                                            TypeCodeEnum.SEMICN
                                    )),
                                    new BackusBean(BackusEnum.QUEUE, Arrays.asList(
                                            TypeCodeEnum.CONTINUETK,
                                            TypeCodeEnum.SEMICN
                                    )),
                                    new BackusBean(BackusEnum.QUEUE, Arrays.asList(
                                            TypeCodeEnum.RETURNTK,
                                            new BackusBean(BackusEnum.CHOICE, Collections.singletonList(
                                                    NonTerminalEnum.Exp
                                            )),
                                            TypeCodeEnum.SEMICN
                                    )),
                                    new BackusBean(BackusEnum.QUEUE, Arrays.asList(
                                            NonTerminalEnum.LVal,
                                            TypeCodeEnum.ASSIGN,
                                            TypeCodeEnum.GETINTTK,
                                            TypeCodeEnum.LPARENT,
                                            TypeCodeEnum.RPARENT,
                                            TypeCodeEnum.SEMICN
                                    )),
                                    new BackusBean(BackusEnum.QUEUE, Arrays.asList(
                                            TypeCodeEnum.PRINTFTK,
                                            TypeCodeEnum.LPARENT,
                                            TypeCodeEnum.STRCON,
                                            new BackusBean(BackusEnum.MULTI, Arrays.asList(
                                                    TypeCodeEnum.COMMA,
                                                    NonTerminalEnum.Exp
                                            )),
                                            TypeCodeEnum.RPARENT,
                                            TypeCodeEnum.SEMICN
                                    ))
                            )
                    ), index);
                    return stackCheck(res, index, nonTerminal);
                case Exp:
                    // AddExp
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.QUEUE,
                            Collections.singletonList(
                                    NonTerminalEnum.AddExp
                            )
                    ), index);
                    return stackCheck(res, index, nonTerminal);
                case Cond:
                    // LOrExp
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.QUEUE,
                            Collections.singletonList(
                                    NonTerminalEnum.LOrExp
                            )
                    ), index);
                    return stackCheck(res, index, nonTerminal);
                case LVal:
                    // Ident {'[' Exp ']'}
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.QUEUE,
                            Arrays.asList(
                                    TypeCodeEnum.IDENFR,
                                    new BackusBean(BackusEnum.MULTI, Arrays.asList(
                                            TypeCodeEnum.LBRACK,
                                            NonTerminalEnum.Exp,
                                            TypeCodeEnum.RBRACK
                                    ))
                            )
                    ), index);
                    return stackCheck(res, index, nonTerminal);
                case PrimaryExp:
                    // '(' Exp ')' | LVal | Number
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.OR,
                            Arrays.asList(
                                    new BackusBean(BackusEnum.QUEUE, Arrays.asList(
                                            TypeCodeEnum.LPARENT,
                                            NonTerminalEnum.Exp,
                                            TypeCodeEnum.RPARENT
                                    )),
                                    NonTerminalEnum.LVal,
                                    NonTerminalEnum.Number
                            )
                    ), index);
                    return stackCheck(res, index, nonTerminal);
                case Number:
                    // IntConst
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.QUEUE,
                            Collections.singletonList(
                                    TypeCodeEnum.INTCON
                            )
                    ), index);
                    return stackCheck(res, index, nonTerminal);
                case UnaryExp:
                    // PrimaryExp | Ident '(' [FuncRParams] ')' | UnaryOp UnaryExp
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.OR,
                            Arrays.asList(
                                    new BackusBean(BackusEnum.QUEUE, Arrays.asList(
                                            TypeCodeEnum.IDENFR,
                                            TypeCodeEnum.LPARENT,
                                            new BackusBean(BackusEnum.CHOICE, Collections.singletonList(
                                                    NonTerminalEnum.FuncRParams
                                            )),
                                            TypeCodeEnum.RPARENT
                                    )),
                                    NonTerminalEnum.PrimaryExp,
                                    new BackusBean(BackusEnum.QUEUE, Arrays.asList(
                                            NonTerminalEnum.UnaryOp,
                                            NonTerminalEnum.UnaryExp
                                    ))
                            )
                    ), index);
                    return stackCheck(res, index, nonTerminal);
                case UnaryOp:
                    //  '+' | '−' | '!'
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.OR,
                            Arrays.asList(
                                    TypeCodeEnum.PLUS,
                                    TypeCodeEnum.MINU,
                                    TypeCodeEnum.NOT
                            )
                    ), index);
                    return stackCheck(res, index, nonTerminal);
                case FuncRParams:
                    // Exp {',' Exp}
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.QUEUE,
                            Arrays.asList(
                                    NonTerminalEnum.Exp,
                                    new BackusBean(BackusEnum.MULTI, Arrays.asList(
                                            TypeCodeEnum.COMMA,
                                            NonTerminalEnum.Exp
                                    ))
                            )
                    ), index);
                    return stackCheck(res, index, nonTerminal);
                case MulExp:
                    // UnaryExp | MulExp ('*' | '/' | '%') UnaryExp
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.RECURSION,
                            Arrays.asList(
                                    NonTerminalEnum.MulExp,
                                    NonTerminalEnum.UnaryExp,
                                    new BackusBean(BackusEnum.OR, Arrays.asList(
                                            TypeCodeEnum.MULT,
                                            TypeCodeEnum.DIV,
                                            TypeCodeEnum.MOD
                                    )),
                                    NonTerminalEnum.UnaryExp
                            )
                    ), index);
                    return res;
                case AddExp:
                    // MulExp | AddExp ('+' | '-') MulExp
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.RECURSION,
                            Arrays.asList(
                                    NonTerminalEnum.AddExp,
                                    NonTerminalEnum.MulExp,
                                    new BackusBean(BackusEnum.OR, Arrays.asList(
                                            TypeCodeEnum.PLUS,
                                            TypeCodeEnum.MINU
                                    )),
                                    NonTerminalEnum.MulExp
                            )
                    ), index);
                    return res;
                case RelExp:
                    // AddExp | RelExp ('<' | '>' | '<=' | '>=') AddExp
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.RECURSION,
                            Arrays.asList(
                                    NonTerminalEnum.RelExp,
                                    NonTerminalEnum.AddExp,
                                    new BackusBean(BackusEnum.OR, Arrays.asList(
                                            TypeCodeEnum.LSS,
                                            TypeCodeEnum.GRE,
                                            TypeCodeEnum.LEQ,
                                            TypeCodeEnum.GEQ
                                    )),
                                    NonTerminalEnum.AddExp
                            )
                    ), index);
                    return res;
                case EqExp:
                    // RelExp | EqExp ('==' | '!=') RelExp
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.RECURSION,
                            Arrays.asList(
                                    NonTerminalEnum.EqExp,
                                    NonTerminalEnum.RelExp,
                                    new BackusBean(BackusEnum.OR, Arrays.asList(
                                            TypeCodeEnum.EQL,
                                            TypeCodeEnum.NEQ
                                    )),
                                    NonTerminalEnum.RelExp
                            )
                    ), index);
                    return res;
                case LAndExp:
                    // EqExp | LAndExp '&&' EqExp
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.RECURSION,
                            Arrays.asList(
                                    NonTerminalEnum.LAndExp,
                                    NonTerminalEnum.EqExp,
                                    TypeCodeEnum.AND,
                                    NonTerminalEnum.EqExp
                            )
                    ), index);
                    return res;
                case LOrExp:
                    // LAndExp | LOrExp '||' LAndExp
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.RECURSION,
                            Arrays.asList(
                                    NonTerminalEnum.LOrExp,
                                    NonTerminalEnum.LAndExp,
                                    TypeCodeEnum.OR,
                                    NonTerminalEnum.LAndExp
                            )
                    ), index);
                    return res;
                case ConstExp:
                    // AddExp
                    res = BackusUtils.backusCheck(new BackusBean(BackusEnum.QUEUE,
                            Collections.singletonList(
                                    NonTerminalEnum.AddExp
                            )
                    ), index);
                    return stackCheck(res, index, nonTerminal);
                default:
                    popNonTerminal(index);
                    return -1;
            }
        }
        popNonTerminal(index);
        return -1;
    }
}
