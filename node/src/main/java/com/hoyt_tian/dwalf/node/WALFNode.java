package com.hoyt_tian.dwalf.node;

import com.hoyt_tian.dwalf.event.EventLoopImpl;
import com.hoyt_tian.dwalf.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;


public class WALFNode extends EventLoopImpl<WALFEvent, WALFEventType> implements Server {
    private  final Logger logger = LoggerFactory.getLogger(WALFNode.class);

    public WALFNode() {
        subscribe(WALFEventType.服务器启动, this::startup);
        subscribe(WALFEventType.服务器关闭, this::shutdown);
    }

    protected void startup(WALFEvent event) {
        logger.info("WALFNode start....");
    }

    protected void shutdown(WALFEvent event) {
        logger.info("WALFNode shutdown....");
    }

    @Override
    public void start() {
        try {
            this.enqueue(WALFEvent.fromWALFEventType(WALFEventType.服务器启动));
        } catch (InterruptedException e) {
            logger.error("Server starts fail...\n{}", e);
        }
    }

    @Override
    public void stop() {
        logger.info("WALFNode stopped...");
    }
}
