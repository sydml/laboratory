package sydml.mybatis.base;

import java.util.List;
import java.util.Map;

/**
 * Created by Yuming-Liu
 * 日期： 2019-03-18
 * 时间： 20:08
 */
public class Result {
    private List<Map<String,Object>> result;

    public List<Map<String, Object>> getResult() {
        return result;
    }

    public void setResult(List<Map<String, Object>> result) {
        this.result = result;
    }
}
