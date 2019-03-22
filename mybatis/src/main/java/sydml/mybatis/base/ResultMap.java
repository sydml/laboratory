package sydml.mybatis.base;

import com.sydml.common.utils.StringUtil;

import java.util.HashMap;

/**
 * @author Liuym
 * @date 2019/3/22 0022
 */
public class ResultMap extends HashMap {

    private static final String UNDERLINE = "_";

    @Override
    public Object put(Object key, Object value) {
        if (key instanceof String) {
            return super.put(underlineToCamel((String) key), value);
            //class java.sql.Timestamp,class java.sql.Timestamp,class java.sql.Date
        }else{
            return super.put(key, value);
        }
    }

    private static String underlineToCamel(String key) {
        if (StringUtil.isEmpty(key)) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();

        if (key.contains(UNDERLINE)) {
            String[] split = key.split(UNDERLINE);
            for (String s : split) {
                stringBuilder.append(s.substring(0, 1).toUpperCase()).append(s.substring(1));
            }
        }else{
            stringBuilder.append(key);
        }
        String result = stringBuilder.replace(0, 1, stringBuilder.substring(0, 1).toLowerCase()).toString();
        return result;
    }

    public static void main(String[] args) {
        String wqer_fdas_fads = underlineToCamel("asdf");
        System.out.println(wqer_fdas_fads);
    }
}
