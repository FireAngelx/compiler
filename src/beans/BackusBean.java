package beans;

import enums.BackusEnum;

import java.util.List;

/**
 * @author LiYixiao
 */
public class BackusBean {
    /**
     * Backus范式类型
     */
    public BackusEnum backus;
    /**
     * 递归语句
     */
    public List<Object> objList;

    public BackusBean(BackusEnum backus, List<Object> objList) {
        this.backus = backus;
        this.objList = objList;
    }
}
