package procedures;

import enums.TypeCodeEnum;
import values.FileValue;
import values.TypeCodeValue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author LiYixiao
 */
public class Lexical {
    /**
     * 词法分析方法
     */
    public static void lexicalAnalyse() {
        // 剩余文件内容
        String restString = FileValue.TEST_STRING;
        // 当前行数
        int currentLine = 1;
        for (int i = 0; i < FileValue.TEST_STRING.length(); ) {
            char c = FileValue.TEST_STRING.charAt(i);
            // 如果是应有字头，则查找该字符串含义
            if (TypeCodeValue.ORDER_MAP.containsKey(c)) {
                // 是否找到对应对象
                boolean flag = false;
                for (TypeCodeEnum typeCode : TypeCodeValue.ORDER_MAP.get(c)) {
                    Pattern pattern = Pattern.compile(typeCode.getRe());
                    Matcher matcher = pattern.matcher(restString);
                    if (matcher.find()) {
                        String object = matcher.group();
                        // 如果不是要忽略的对象
                        if (!TypeCodeValue.IGNORE_TYPE_CODE_LIST.contains(typeCode)) {
                            TypeCodeValue.TYPE_CODE_LIST.add(typeCode);
                            TypeCodeValue.TYPE_CODE_PRINT_LIST.add(typeCode.name() + " " + object + "\n");
                        } else {
                            // 如果是注释，检测其中换行符数量
                            currentLine += (object.length() - object.replaceAll("\\n", "").length());
                        }
                        // 剪切剩余的字串
                        restString = restString.substring(matcher.end());
                        // 字串平移
                        i += matcher.end();
                        // 找到对象
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    // 如果没找到，防止死循环
                    i++;
                }
            } else {
                // 如果遇到换行符，当前行数增加
                if (c == '\n') {
                    currentLine++;
                }
                i++;
                restString = restString.substring(1);
            }
        }
    }
}
