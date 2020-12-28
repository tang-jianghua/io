package com.tangjianghua.io;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

/**
 * @author tangjianghua
 * 2020/6/21
 */
public class SocketClient {
    public static void main(String[] args) throws IOException {
        m();
    }

    public static void m() {
        //try (final Socket socket = new Socket("192.168.186.1", 9090)) {
        //try (final Socket socket = new Socket("192.168.186.66", 9090)) {
        try (final Socket socket = new Socket("localhost", 8080)) {
        //try (final Socket socket = new Socket("192.168.25.66", 9090)) {
            socket.setTcpNoDelay(true);
            socket.setOOBInline(true);
            socket.setSoTimeout(0);
            socket.setSendBufferSize(1024);
            final OutputStream outputStream = socket.getOutputStream();
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            //while (true) {
            String s = "00004945<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<service>\n" +
                    "  <sys-header>\n" +
                    "    <data name=\"SYS_HEAD\">\n" +
                    "      <struct>\n" +
                    "        <data name=\"SERVICE_VER\">\n" +
                    "          <field type=\"string\" length=\"30\" scale=\"0\"/>\n" +
                    "        </data>\n" +
                    "        <data name=\"SERVICE_CODE\">\n" +
                    "          <field type=\"string\" length=\"12\" scale=\"0\">6012000003</field>\n" +
                    "        </data>\n" +
                    "        <data name=\"SERVICE_SCENE\">\n" +
                    "          <field type=\"string\" length=\"2\" scale=\"0\">01</field>\n" +
                    "        </data>\n" +
                    "        <data name=\"CONSUMER_SYS_ID\">\n" +
                    "          <field type=\"string\" length=\"5\" scale=\"0\">31800</field>\n" +
                    "        </data>\n" +
                    "        <data name=\"ORIG_SYS_ID\">\n" +
                    "          <field type=\"string\" length=\"5\" scale=\"0\"/>\n" +
                    "        </data>\n" +
                    "        <data name=\"ORIG_SYS_TMNL_NO\">\n" +
                    "          <field type=\"string\" length=\"50\" scale=\"0\"/>\n" +
                    "        </data>\n" +
                    "        <data name=\"ORIG_SYS_SERVER_ID\">\n" +
                    "          <field type=\"string\" length=\"50\" scale=\"0\"/>\n" +
                    "        </data>\n" +
                    "        <data name=\"CONSUMER_SYS_SERVER_ID\">\n" +
                    "          <field type=\"string\" length=\"50\" scale=\"0\"/>\n" +
                    "        </data>\n" +
                    "        <data name=\"CONSUMER_SYS_SEQ_NO\">\n" +
                    "          <field type=\"string\" length=\"21\" scale=\"0\">318002020122300005221</field>\n" +
                    "        </data>\n" +
                    "        <data name=\"TRAN_MODE\">\n" +
                    "          <field type=\"string\" length=\"10\" scale=\"0\">ONLINE</field>\n" +
                    "        </data>\n" +
                    "        <data name=\"SOURCE_TYPE\">\n" +
                    "          <field type=\"string\" length=\"2\" scale=\"0\">LCDX</field>\n" +
                    "        </data>\n" +
                    "        <data name=\"TRAN_DATE\">\n" +
                    "          <field type=\"string\" length=\"8\" scale=\"0\">20190809</field>\n" +
                    "        </data>\n" +
                    "        <data name=\"TRAN_TIMESTAMP\">\n" +
                    "          <field type=\"string\" length=\"9\" scale=\"0\">174056000</field>\n" +
                    "        </data>\n" +
                    "        <data name=\"WS_ID\">\n" +
                    "          <field type=\"string\" length=\"30\" scale=\"0\"/>\n" +
                    "        </data>\n" +
                    "        <data name=\"USER_LANG\">\n" +
                    "          <field type=\"string\" length=\"20\" scale=\"0\">CHINESE</field>\n" +
                    "        </data>\n" +
                    "        <data name=\"SOURCE_BRANCH_NO\">\n" +
                    "          <field type=\"string\" length=\"6\" scale=\"0\"/>\n" +
                    "        </data>\n" +
                    "        <data name=\"DEST_BRANCH_NO\">\n" +
                    "          <field type=\"string\" length=\"6\" scale=\"0\"/>\n" +
                    "        </data>\n" +
                    "        <data name=\"FILE_FLAG\">\n" +
                    "          <field type=\"string\" length=\"1\" scale=\"0\"/>\n" +
                    "        </data>\n" +
                    "        <data name=\"FILE_PATH\">\n" +
                    "          <field type=\"string\" length=\"512\" scale=\"0\"/>\n" +
                    "        </data>\n" +
                    "        <data name=\"GLOBAL_SEQ_NO\">\n" +
                    "          <field type=\"string\" length=\"21\" scale=\"0\"/>\n" +
                    "        </data>\n" +
                    "      </struct>\n" +
                    "    </data>\n" +
                    "  </sys-header>\n" +
                    "  <app-header>\n" +
                    "    <data name=\"APP_HEAD\">\n" +
                    "      <struct>\n" +
                    "        <data name=\"BRANCH_ID\">\n" +
                    "          <field type=\"string\" length=\"30\" scale=\"0\">60001</field>\n" +
                    "        </data>\n" +
                    "        <data name=\"USER_ID\">\n" +
                    "          <field type=\"string\" length=\"30\" scale=\"0\">MF60001</field>\n" +
                    "        </data>\n" +
                    "        <data name=\"AUTH_USER_ID\">\n" +
                    "          <field type=\"string\" length=\"30\" scale=\"0\"/>\n" +
                    "        </data>\n" +
                    "        <data name=\"AUTH_PASSWORD\">\n" +
                    "          <field type=\"string\" length=\"30\" scale=\"0\"/>\n" +
                    "        </data>\n" +
                    "        <data name=\"AUTH_FLAG\">\n" +
                    "          <field type=\"string\" length=\"1\" scale=\"0\"/>\n" +
                    "        </data>\n" +
                    "        <data name=\"APPR_USER_ID\">\n" +
                    "          <field type=\"string\" length=\"30\" scale=\"0\"/>\n" +
                    "        </data>\n" +
                    "        <data name=\"APPR_FLAG\">\n" +
                    "          <field type=\"string\" length=\"1\" scale=\"0\"/>\n" +
                    "        </data>\n" +
                    "      </struct>\n" +
                    "    </data>\n" +
                    "  </app-header>\n" +
                    "  <local-header>\n" +
                    "    <data>\n" +
                    "      <struct>\n" +
                    "        <data name=\"ENCRYPTION_WAY_FLAG\">\n" +
                    "          <field length=\"1\" scale=\"0\" type=\"string\"/>\n" +
                    "        </data>\n" +
                    "        <data name=\"USER_NAME\">\n" +
                    "          <field length=\"50\" scale=\"0\" type=\"string\"/>\n" +
                    "        </data>\n" +
                    "        <data name=\"INFO_CLSF\">\n" +
                    "          <field length=\"50\" scale=\"0\" type=\"string\">420</field>\n" +
                    "        </data>\n" +
                    "      </struct>\n" +
                    "    </data>\n" +
                    "    <data name=\"RET_AUTH_ARRAY\">\n" +
                    "      <array>\n" +
                    "        <struct>\n" +
                    "          <data name=\"RET_CODE\">\n" +
                    "            <field length=\"6\" scale=\"0\" type=\"string\"/>\n" +
                    "          </data>\n" +
                    "          <data name=\"RET_MSG\">\n" +
                    "            <field length=\"512\" scale=\"0\" type=\"string\"/>\n" +
                    "          </data>\n" +
                    "        </struct>\n" +
                    "      </array>\n" +
                    "    </data>\n" +
                    "  </local-header>\n" +
                    "  <body>\n" +
                    "    <data name=\"CAPTCHA\">\n" +
                    "      <field type=\"string\" length=\"50\" scale=\"0\"/>\n" +
                    "    </data>\n" +
                    "    <data name=\"STAMP\">\n" +
                    "      <field type=\"string\" length=\"20\" scale=\"0\"/>\n" +
                    "    </data>\n" +
                    "    <data name=\"MSG_TYPE\">\n" +
                    "      <field type=\"string\" length=\"3\" scale=\"0\">0</field>\n" +
                    "    </data>\n" +
                    "    <data name=\"TRGT_ADDR\">\n" +
                    "      <field type=\"string\" length=\"1300\" scale=\"0\">13148491111</field>\n" +
                    "    </data>\n" +
                    "    <data name=\"TEMPLATE_FLAG\">\n" +
                    "      <field type=\"string\" length=\"1\" scale=\"0\">0</field>\n" +
                    "    </data>\n" +
                    "    <data name=\"CNTNT\">\n" +
                    "      <field type=\"string\" length=\"2000\" scale=\"0\">2020年09月18日17时40分,批量执行成功</field>\n" +
                    "    </data>\n" +
                    "    <data name=\"SEND_TIME\">\n" +
                    "      <field type=\"string\" length=\"20\" scale=\"0\"/>\n" +
                    "    </data>\n" +
                    "    <data name=\"LONG_NO\">\n" +
                    "      <field type=\"string\" length=\"20\" scale=\"0\"/>\n" +
                    "    </data>\n" +
                    "    <data name=\"PRIORITY_LVL\">\n" +
                    "      <field type=\"string\" length=\"3\" scale=\"0\">0</field>\n" +
                    "    </data>\n" +
                    "  </body>\n" +
                    "</service>";
                if (s != null) {
                    outputStream.write(s.getBytes());
                 /*   final byte[] bytes = s.getBytes();
                    for (int i = 0; i < bytes.length; i++) {
                        outputStream.write(bytes[i]);
                    }*/
                }
            //}

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
