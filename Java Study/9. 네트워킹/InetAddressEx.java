import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class InetAddressEx {

    public static void main(String[] args) {
        InetAddress ip = null;
        InetAddress[] ipArr = null;

        try {
            // 도메인명을 통해 IP 주소를 얻는다.
            ip = InetAddress.getByName("www.google.com");
            System.out.println("getHostName() : " + ip.getHostName()); // 호스트의 이름 반환
            System.out.println("getHostAddress() : " + ip.getHostAddress()); // 호스트의 IP 주소 반환
            System.out.println("toString() : " + ip.toString());

            // IP 주소를 byte 배열로 반환
            byte[] ipAddress = ip.getAddress();
            System.out.println("getAddress() : " + Arrays.toString(ipAddress));

            String result = "";
            for (int i = 0; i < ipAddress.length; i++) {
                result += (ipAddress[i] < 0) ? ipAddress[i] + 256 : ipAddress[i];
                result += ".";
            }

            System.out.println("getAddress() + 256 : " + result);
            System.out.println();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
            ip = InetAddress.getLocalHost();
            System.out.println("getHostName() : " + ip.getHostName());
            System.out.println("getHostAddress() : " + ip.getHostAddress());
            System.out.println("toString() : " + ip.toString());
            System.out.println();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
            // 도메인명에 지정된 모든 호스트의 IP 주소를 배열에 담아서 반환
            ipArr = InetAddress.getAllByName("www.naver.com");

            for (int i = 0; i < ipArr.length; i++) {
                System.out.println("ipArr[" + i + "] : " + ipArr[i]);
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
