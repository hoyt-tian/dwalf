package com.hoyt_tian.dwalf.node;

import com.hoyt_tian.dwalf.event.Event;

public class WALFEvent extends Event<WALFEventType> {

    static WALFEvent fromWALFEventType(WALFEventType type) {
        WALFEvent event = new WALFEvent();
        event.setType(type);
        return event;
    }
}
