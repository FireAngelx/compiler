package utils;

import enums.NonTerminalEnum;
import values.FileValue;
import values.NonTerminalValue;
import values.TypeCodeValue;

import javax.annotation.processing.FilerException;
import java.io.*;
import java.util.Stack;

/**
 * @author LiYixiao
 */
public class FileUtils {
    /**
     * 初始化文件
     */
    public static void initFile() {
        FileValue.INPUT_FILE = new File(FileValue.INPUT_PATH);
        FileValue.OUTPUT_FILE = new File(FileValue.OUTPUT_PATH);
    }

    /**
     * 读文件
     */
    public static void readFile() throws Exception{
        InputStream is = new FileInputStream(FileValue.INPUT_FILE);
        byte[] buffer = new byte[is.available()];
        if (is.read(buffer) == -1) {
            throw new FilerException("File not found!");
        }
        is.close();
        FileValue.TEST_STRING = new String(buffer);
    }

    /**
     * 写文件
     */
    public static void writeFile() throws Exception {
        FileWriter writer = new FileWriter(FileValue.OUTPUT_FILE);
        BufferedWriter bufferWriter = new BufferedWriter(writer);
        int i;
        for (i = 0; i <= TypeCodeValue.TYPE_CODE_PRINT_LIST.size(); i++) {
            // 判断是否存在栈
            if (NonTerminalValue.NON_TERMINAL_MAP.containsKey(i)) {
                Stack<NonTerminalEnum> nonTerminalStack = NonTerminalValue.NON_TERMINAL_MAP.get(i);
                // 弹空栈
                while(!nonTerminalStack.isEmpty()) {
                    NonTerminalEnum nonTerminal = nonTerminalStack.pop();
                    if (!NonTerminalValue.IGNORE_NON_TERMINAL_LIST.contains(nonTerminal)) {
                        bufferWriter.write("<" + nonTerminal + ">\n");
                    }
                }
            }
            if(i != TypeCodeValue.TYPE_CODE_PRINT_LIST.size()) {
                bufferWriter.write(TypeCodeValue.TYPE_CODE_PRINT_LIST.get(i));
            }
        }
        bufferWriter.close();
        writer.close();
    }
}
