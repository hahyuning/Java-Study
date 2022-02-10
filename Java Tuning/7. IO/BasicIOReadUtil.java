import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BasicIOReadUtil {
    // 읽은 내용을 일단 StringBuffer에 담고, 줄이 바뀔 경우 ArrayList에 담아서 리턴
    public static ArrayList readCharStream(String filename) throws Exception {
        ArrayList<StringBuffer> list = new ArrayList<>();
        FileReader fr = null;

        try {
            fr = new FileReader(filename);
            int data = 0;

            // 한 줄씩 데이터를 담을 StringBuffer 생성
            StringBuffer sb = new StringBuffer();
            while ((data = fr.read()) != -1) {
                if (data == '\n' || data == '\r') {
                    list.add(sb);
                    sb = new StringBuffer();
                } else {
                    sb.append((char) data);
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw e;
        } finally {
            if (fr != null) {
                fr.close();
            }
        }
        return list;
    }

    // 특정 배열에 읽은 데이터를 저장한 후 사용
    public static String readCharStreamWithBuffer(String filename) throws Exception {
        StringBuffer sb = new StringBuffer();
        FileReader fr = null;

        try {
            fr = new FileReader(filename);
            int bufferSize = 1024 * 1024;
            char[] readBuffer = new char[bufferSize];

            int resultSize = 0;
            while ((resultSize = fr.read(readBuffer)) != -1) {
                if (resultSize == bufferSize) {
                    sb.append(readBuffer);
                } else {
                    for (int loop = 0; loop < resultSize; loop++) {
                        sb.append(readBuffer[loop]);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw e;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw e;
        } finally {
            if (fr != null) {
                fr.close();
            }
        }
        return sb.toString();
    }

    public static ArrayList<String> readBufferedReader(String filename) throws Exception {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(filename));
            String data;
            while ((data = br.readLine()) != null) {
                list.add(data);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw e;
        } finally {
            if (br != null) {
                br.close();
            }
        }
        return list;
    }

    public static void main(String[] args) throws Exception {
        String filename = "C:\\10MBFile";

        long startTime = System.nanoTime();
        ArrayList list = BasicIOReadUtil.readCharStream(filename);
        long endTime = System.nanoTime();

        System.out.println(endTime - startTime);
        System.out.println(list.size());
    }
}
