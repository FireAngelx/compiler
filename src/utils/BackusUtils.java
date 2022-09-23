package utils;

import beans.BackusBean;
import enums.NonTerminalEnum;
import procedures.Syntax;

import java.util.List;

/**
 * @author LiYixiao
 */
public class BackusUtils {
    /**
     * 队列执行
     * @param objList 类别码或终结符列表
     * @param tmp 起始索引
     * @return 终止索引
     */
    public static Integer queueCheck(List<Object> objList, Integer tmp) {
        Integer res = tmp;
        for(Object obj: objList) {
            res = objCheck(obj, tmp);
            if(res.equals(-1)) {
                return -1;
            } else {
                tmp = res;
            }
        }
        return res;
    }

    /**
     * 或执行
     * @param objList 类别码或终结符列表
     * @param tmp 起始索引
     * @return 终止索引
     */
    public static Integer orCheck(List<Object> objList, Integer tmp) {
        Integer res;
        for(Object obj: objList) {
            res = objCheck(obj, tmp);
            if(res.equals(-1)) {
                Syntax.stackCheck(res, tmp, null);
            } else {
                return res;
            }
        }
        return -1;
    }

    /**
     * 选择执行
     * @param objList 类别码或终结符列表
     * @param tmp 起始索引
     * @return 终止索引
     */
    public static Integer choiceCheck(List<Object> objList, Integer tmp) {
        Integer res = tmp, temp = tmp;
        for(Object obj: objList) {
            res = objCheck(obj, temp);
            if(res.equals(-1)) {
                Syntax.stackCheck(res, tmp, null);
                return tmp;
            } else {
                temp = res;
            }
        }
        return res;
    }

    /**
     * 多选执行
     * @param objList 类别码或终结符列表
     * @param tmp 起始索引
     * @return 终止索引
     */
    public static Integer multiCheck(List<Object> objList, Integer tmp) {
        Integer res = tmp, temp = tmp;
        while(true) {
            for(Object obj: objList) {
                res = objCheck(obj, temp);
                if(res.equals(-1)) {
                    Syntax.stackCheck(res, tmp, null);
                    return tmp;
                } else {
                    temp = res;
                }
            }
            tmp = res;
        }
    }

    /**
     * 左递归执行
     * @param objList 类别码或终结符列表（0：左递归非终结符，1：头部非终结符，2+：循环非终结符）
     *                A::=B|AC  0:A 1:B 2+:C
     * @param tmp 起始索引
     * @return 终止索引
     */
    public static Integer recursionCheck(List<Object> objList, Integer tmp) {
        Object ter = objList.get(0), head = objList.get(1);
        Integer res = objCheck(head, tmp), temp;
        temp = Syntax.stackCheck(res, tmp, (NonTerminalEnum)ter);
        if(temp.equals(-1)) {
            return -1;
        }
        tmp = temp;
        objList = objList.subList(2, objList.size());
        while(true) {
            for(Object obj: objList) {
                res = objCheck(obj, temp);
                if(res.equals(-1)) {
                    return tmp;
                } else {
                    temp = res;
                }
            }
            tmp = res;
            Syntax.stackCheck(res, tmp, (NonTerminalEnum)ter);
        }
    }

    /**
     * 对象执行
     * @param obj   backus或类别码或终结符
     * @param tmp   起始索引
     * @return 终止索引
     */
    public static Integer objCheck(Object obj, Integer tmp) {
        Integer res;
        if(obj instanceof BackusBean) {
            BackusBean backus = (BackusBean)obj;
            res = backusCheck(backus, tmp);
        } else {
            res = Syntax.check(obj, tmp);
        }
        return res;
    }

    /**
     * backus执行
     * @param backus    backus语法
     * @param tmp       起始索引
     * @return 终止索引
     */
    public static Integer backusCheck(BackusBean backus, Integer tmp) {
        Integer res = -1;
        switch (backus.backus) {
            case QUEUE:
                res = queueCheck(backus.objList, tmp);
                break;
            case OR:
                res = orCheck(backus.objList, tmp);
                break;
            case CHOICE:
                res = choiceCheck(backus.objList, tmp);
                break;
            case MULTI:
                res = multiCheck(backus.objList, tmp);
                break;
            case RECURSION:
                res = recursionCheck(backus.objList, tmp);
                break;
            default:
                break;
        }
        return res;
    }
}
