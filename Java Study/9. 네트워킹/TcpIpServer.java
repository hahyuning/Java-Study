import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TcpIpServer implements Runnable {
    ServerSocket serverSocket;
    Thread[] threads;

    public static void main(String[] args) {
        // 5개의 쓰레드를 생성하는 서버를 생성한다.
        TcpIpServer server = new TcpIpServer(5);
        server.start();
    }

    public TcpIpServer(int num) {
        try {
            // 서버소켓을 생성하여 7777번 포트와 결합시킨다.
            serverSocket = new ServerSocket(7777);
            System.out.println(getTime() + " 서버가 준비되었습니다.");

            threads = new Thread[num];

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(this);
            threads[i].start();
        }
    }

    public void run() {
        while (true) {
            try {
                System.out.println(getTime() + " 연결 요청을 기다립니다.");

                // 요청 대기시간을 5초로 설정한다.
                // 5초동안 접속 요청이 없으면 SocketTimeoutException이 발생한다.
                serverSocket.setSoTimeout(5 * 1000);

                // 서버소켓은 클라이언트의 연결 요청이 올때까지 실행을 멈추고 계속 기다린다.
                // 클라이언트의 연결 요청이 오면 클라이언트 소켓과 통신할 새로운 소켓을 생성한다.
                Socket socket = serverSocket.accept();
                System.out.println(getTime() + socket.getInetAddress() + " 로부터 연결 요청이 들어왔습니다.");

                System.out.println("getPort() : " + socket.getPort());
                System.out.println("getLocalPort() : " + socket.getLocalPort());

                // 소켓의 출력 스트림을 얻는다.
                OutputStream out = socket.getOutputStream();
                DataOutputStream dos = new DataOutputStream(out);

                // 원격 소켓에 데이터를 보낸다.
                dos.writeUTF("[Notice] Test Message1 from Server.");
                System.out.println(getTime() + " 데이터를 전송했습니다.");

                // 스트림과 소켓을 닫아준다.
                dos.close();
                socket.close();

            } catch (SocketTimeoutException e) {
                System.out.println("지정된 시간동안 접속 요청이 없어 서버를 종료합니다.");
                System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static String getTime() {
        SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
        return f.format(new Date());
    }
}