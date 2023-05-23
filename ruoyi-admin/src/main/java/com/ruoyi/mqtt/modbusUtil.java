package com.ruoyi.mqtt;

import com.intelligt.modbus.jlibmodbus.Modbus;
import com.intelligt.modbus.jlibmodbus.exception.ModbusIOException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusNumberException;
import com.intelligt.modbus.jlibmodbus.exception.ModbusProtocolException;
import com.intelligt.modbus.jlibmodbus.master.ModbusMaster;
import com.intelligt.modbus.jlibmodbus.master.ModbusMasterFactory;
import com.intelligt.modbus.jlibmodbus.tcp.TcpParameters;
import com.ruoyi.utils.CommonUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.ObjectUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Hello world!
 *
 */
@Log4j2
public class modbusUtil {

    private static ModbusMaster master;


    public static void initMaster(String host){
        try {
            // 设置主机TCP参数
            TcpParameters tcpParameters = new TcpParameters();

            // 设置TCP的ip地址
            InetAddress adress = InetAddress.getByName(host);
            tcpParameters.setHost(adress);

            // TCP设置长连接
            tcpParameters.setKeepAlive(true);
            // TCP设置端口，这里设置是默认端口502
            tcpParameters.setPort(Modbus.TCP_PORT);

            // 创建一个主机
            master = ModbusMasterFactory.createModbusMasterTCP(tcpParameters);
            Modbus.setAutoIncrementTransactionId(true);

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Map<String,Double> getTemperatureHumidity(String host,int slaveId) {
        initMaster(host);
        int offset = 0;             // 寄存器读取开始地址
        int quantity = 2;                // 读取的寄存器数量
        Map<String,Double> map = new HashMap<>();
        try {
            if (!master.isConnected()) {
                master.connect();   // 开启连接
            }
            // 读取对应从机的数据，readInputRegisters读取的写寄存器，功能码04
                int[] registerValues = master.readInputRegisters(slaveId, offset, quantity);
            // 每两位byte转为无符号整型
//            int[] registerValues = master.readHoldingRegisters(slaveId, offset, quantity);

            // 控制台输出
            for (int i = 0; i < registerValues.length; i++) {
                int value = registerValues[i];
                log.info("Index: " + i + ", Value: " + value);
            }
            map.put("temperature", CommonUtils.int2Double(registerValues[0],10));
            map.put("humidity", CommonUtils.int2Double(registerValues[1],10));

        } catch (ModbusProtocolException e) {
           log.error("ModbusProtocolException:"+e.getMessage());
        } catch (ModbusNumberException e) {
            log.error("ModbusNumberException:"+e.getMessage());
        } catch (ModbusIOException e) {
            log.error("ModbusIOException:"+e.getMessage());
        }
        return map;
    }


    public static void main(String[] args) {
        System.out.println(getTemperatureHumidity("192.168.1.254",1));
    }
}



