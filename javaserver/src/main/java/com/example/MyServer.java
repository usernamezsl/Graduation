package com.example;

import com.example.facade.IFacade;
import com.example.facade.TvController;

public class MyServer {

    public static void main(String[] args) {
        IFacade iFacade = new TvController();
        iFacade.powerSystem();
        iFacade.voiceSystem();
        iFacade.channelSystem();
    }
}
