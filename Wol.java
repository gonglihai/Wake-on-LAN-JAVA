import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * Wol网络唤醒
 * @author GongLiHai
 */
public class Wol {
    /**
     * 端口号
     */
    private final static int PORT = 9;
    /**
     * MAC地址
     */
    private final static String MAC_ADDRESS = "74:56:3c:43:2d:bc";
    /**
     * 广播地址
     */
    private final static String BROADCAST_ADDRESS = "192.168.2.255";

    /**
     * main程序入口
     * @param args 参数
     */
    public static void main(String[] args) {
        System.out.print("正在向" + MAC_ADDRESS + "发送唤醒魔术包,");
        if (sendMagicPackage()) {
            System.out.println("发送成功");
        } else {
            System.out.println("发送失败");
        }
    }

    /**
     * 发送唤醒魔术包
     */
    public static boolean sendMagicPackage() {
        InetAddress destHost = null;
        try {
            destHost = InetAddress.getByName(BROADCAST_ADDRESS);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        //验证MAC地址并转换为二进制
        byte[] destMac = getMacBytes();

        // 创建开机指令包
        byte[] magic = new byte[102];
        // 将数据包的前6位放入0xFF即 "FF"的二进制
        for (int i = 0; i < 6; i++) {
            magic[i] = (byte) 0xFF;
        }
        // 从第7个位置开始把MAC地址放入16次
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < destMac.length; j++) {
                magic[6 + destMac.length * i + j] = destMac[j];
            }
        }

        DatagramPacket dp = new DatagramPacket(magic, magic.length, destHost, PORT);
        DatagramSocket ds;
        try {
            ds = new DatagramSocket();
            ds.send(dp);
            ds.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 验证MAC地址并转换为二进制
     */
    private static byte[] getMacBytes() throws IllegalArgumentException {
        byte[] bytes = new byte[6];
        String[] hex = Wol.MAC_ADDRESS.split("(\\:|\\-)");
        if (hex.length != 6) {
            throw new IllegalArgumentException("无效的MAC地址");
        }
        try {
            for (int i = 0; i < 6; i++) {
                bytes[i] = (byte) Integer.parseInt(hex[i], 16);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("无效的MAC地址");
        }
        return bytes;
    }
}
