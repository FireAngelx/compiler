import procedures.Lexical;
import procedures.Syntax;
import utils.FileUtils;

/**
 * @author LiYixiao
 */
public class Compiler {
    /**
     * 入口函数
     * @param args  入口参数
     * @throws Exception    IO以及其他异常
     */
    public static void main(String[] args) throws Exception {
        // 初始化文件
        FileUtils.initFile();
        // 读文件
        FileUtils.readFile();
        // 词法分析
        Lexical.lexicalAnalyse();
        // 语法分析
        Syntax.syntaxAnalyse();
        // 写文件
        FileUtils.writeFile();
    }
}
