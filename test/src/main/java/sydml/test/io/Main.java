package sydml.test.io;

import com.sydml.common.utils.StreamUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Liuym
 * @date 2019/3/22 0022
 */
public class Main {
    public static void main(String[] args) throws IOException{
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("file/javaList.txt");
//        File dir = new File("D:\\stariboss\\feature1");
        String string = StreamUtil.getString(inputStream);
        byte[] buffer = new byte[1024*4];
        int n = 0;
        n = inputStream.read(buffer);
        System.out.println();
    }
    public static long copyLarge(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024*4];
        long count = 0;
        int n = 0;
        n = input.read(buffer);
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
}
