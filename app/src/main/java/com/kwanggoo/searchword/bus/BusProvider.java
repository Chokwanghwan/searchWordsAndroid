package com.kwanggoo.searchword.bus;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by josunghwan on 15. 4. 18..
 */
public class BusProvider {
    private static final Bus BUS = new Bus(ThreadEnforcer.MAIN);

    public static Bus getBus() {
        return BUS;
    }
}
