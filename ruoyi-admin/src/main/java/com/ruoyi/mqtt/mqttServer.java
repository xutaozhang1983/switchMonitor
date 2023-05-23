package com.ruoyi.mqtt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class mqttServer {

    public static void main(String[] args) throws IOException {
        int port = 1883;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("MQTT Broker started on port " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("New client connected: " + clientSocket.getInetAddress());

            InputStream is = clientSocket.getInputStream();
            OutputStream os = clientSocket.getOutputStream();

            byte[] buffer = new byte[1024];
            int read = is.read(buffer);

            byte[] connectAck = { 0x20, 0x02, 0x00, 0x00 };
            os.write(connectAck);

            while (true) {
                read = is.read(buffer);
                if (read == -1) {
                    break;
                }
                int messageType = (buffer[0] >> 4) & 0x0f;
                if (messageType == 0x03) { // PUBLISH message
                    String topic = readMqttString(buffer, 2);
                    byte[] messageData = new byte[read - topic.length() - 4];
                    System.arraycopy(buffer, topic.length() + 4, messageData, 0, messageData.length);
                    int qosLevel = (buffer[0] >> 1) & 0x03;
                    boolean retained = ((buffer[0] >> 0) & 0x01) == 1;
                    System.out.println("Received message on topic " + topic + ": " + new String(messageData));
                    forwardMessageToSubscribers(topic, messageData, qosLevel, retained);
                }
            }
            clientSocket.close();
            System.out.println("Client disconnected");
        }
    }

    private static String readMqttString(byte[] buffer, int offset) {
        int stringLength = ((buffer[offset] & 0xff) << 8) | (buffer[offset + 1] & 0xff);
        byte[] stringData = new byte[stringLength];
        System.arraycopy(buffer, offset + 2, stringData, 0, stringLength);
        return new String(stringData);
    }

    private static void forwardMessageToSubscribers(String topic, byte[] messageData, int qosLevel, boolean retained) {
        String brokerUrl = "tcp://localhost:1883";
        String clientId = "mqtt-broker";
        MemoryPersistence persistence = new MemoryPersistence();

        try {
            MqttClient mqttClient = new MqttClient(brokerUrl, clientId, persistence);
            mqttClient.connect();
            MqttMessage message = new MqttMessage(messageData);
            message.setQos(qosLevel);
            message.setRetained(retained);
            mqttClient.publish(topic, message);
            mqttClient.disconnect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}

