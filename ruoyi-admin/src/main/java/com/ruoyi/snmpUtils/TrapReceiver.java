package com.ruoyi.snmpUtils;

import java.io.IOException;
import java.net.UnknownHostException;

import lombok.extern.log4j.Log4j2;
import org.snmp4j.CommandResponder;
import org.snmp4j.MessageDispatcherImpl;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.MPv1;
import org.snmp4j.mp.MPv2c;
import org.snmp4j.mp.MPv3;
import org.snmp4j.security.SecurityModels;
import org.snmp4j.security.SecurityProtocols;
import org.snmp4j.security.USM;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.TcpAddress;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.transport.DefaultTcpTransportMapping;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.MultiThreadedMessageDispatcher;
import org.snmp4j.util.ThreadPool;
import org.springframework.stereotype.Component;

@Log4j2
//@Component
public class TrapReceiver {
    private MultiThreadedMessageDispatcher dispatcher;
    private Snmp snmp = null;
    private Address listenAddress;
    private ThreadPool threadPool;

    public TrapReceiver(CommandResponder listener)  throws UnknownHostException, IOException {
        threadPool = ThreadPool.create("Trap", 4);
        dispatcher = new MultiThreadedMessageDispatcher(threadPool,
                new MessageDispatcherImpl());
        log.debug("监听的IP和端口是={}",System.getProperty(
                "snmp4j.listenAddress", "udp:"+getAddress()));
        listenAddress = GenericAddress.parse(System.getProperty("snmp4j.listenAddress", "udp:"+getAddress())); // 本地IP与监听端口
//         listenAddress = GenericAddress.parse(address); // 本地IP与监听端口
        TransportMapping<?> transport;
        // 对TCP与UDP协议进行处理
        if (listenAddress instanceof UdpAddress) {
            transport = new DefaultUdpTransportMapping(
                    (UdpAddress) listenAddress);
        } else {
            transport = new DefaultTcpTransportMapping(
                    (TcpAddress) listenAddress);
        }
        snmp = new Snmp(dispatcher, transport);
        snmp.getMessageDispatcher().addMessageProcessingModel(new MPv1());
        snmp.getMessageDispatcher().addMessageProcessingModel(new MPv2c());
        snmp.getMessageDispatcher().addMessageProcessingModel(new MPv3());
        USM usm = new USM(SecurityProtocols.getInstance(), new OctetString(MPv3
                .createLocalEngineID()), 0);
        SecurityModels.getInstance().addSecurityModel(usm);
        snmp.listen();
        log.debug("开始监听={}");
        snmp.addCommandResponder(listener);
    }

    public MultiThreadedMessageDispatcher getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(MultiThreadedMessageDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public Snmp getSnmp() {
        return snmp;
    }

    public void setSnmp(Snmp snmp) {
        this.snmp = snmp;
    }

    public Address getListenAddress() {
        return listenAddress;
    }

    public void setListenAddress(Address listenAddress) {
        this.listenAddress = listenAddress;
    }

    public ThreadPool getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(ThreadPool threadPool) {
        this.threadPool = threadPool;
    }

    private static String getAddress(){
        try{
            return java.net.InetAddress.getLocalHost().getHostAddress()+"/8088";
        }catch (UnknownHostException e){
            throw new RuntimeException();
        }
    }
}

