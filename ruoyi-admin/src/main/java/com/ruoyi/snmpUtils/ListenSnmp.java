package com.ruoyi.snmpUtils;

import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.smi.VariableBinding;
import org.springframework.stereotype.Component;

@Component
public class ListenSnmp implements CommandResponder {

    public Logger logger = LoggerFactory.getLogger(getClass());

    public ListenSnmp() {
        logger.info("监控器初始info");
    }

    @Override
    public void processPdu(CommandResponderEvent event) {

        // 解析Response
        if (event != null && event.getPDU() != null) {
            System.out.println(event.getPeerAddress());
            System.out.println(event.getMessageDispatcher());
            Vector<? extends VariableBinding> recVBs = (Vector<? extends VariableBinding>) event.getPDU().getVariableBindings();
            System.out.println(recVBs.toString());
            for (int i = 0; i < recVBs.size(); i++) {
                VariableBinding recVB = recVBs.elementAt(i);
                logger.error(recVB.getOid() + " : " + recVB.getVariable());
            }
        }

    }

}

