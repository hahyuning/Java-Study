import javax.imageio.IIOException;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.Socket;

public class TcpIpClient {
    public static void main(String[] args) {

        try {
            String serverIp = "127.0.0.1";
            System.out.println("서버에 연결중입니다. 서버 IP: " + serverIp);

            // 소켓을 생성하여 연결 요청을 한다.
            Socket socket = new Socket(serverIp, 7777);

            // 소켓의 입력 스트림을 얻는다.
            InputStream in = socket.getInputStream();
            DataInputStream dis = new DataInputStream(in);

            // 소켓으로부터 받은 데이터를 출력한다.
            System.out.println("서버로부터 받은 메시지 : " + dis.readUTF());
            System.out.println("연결을 종료합니다.");

            // 스트림과 소켓을 닫는다.
            dis.close();
            socket.close();

        } catch (ConnectException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
