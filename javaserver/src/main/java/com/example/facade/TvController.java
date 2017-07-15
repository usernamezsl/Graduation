package com.example.facade;

/**
 * 作者: 张少林 on 2017/4/28 0028.
 * 邮箱:1083065285@qq.com
 */

public class TvController implements IFacade {
    private PowerSystem mPowerSystem = new PowerSystem();
    private VoiceSystem mVoiceSystem = new VoiceSystem();
    private ChannelSystem mChannelSystem = new ChannelSystem();

    @Override
    public void powerSystem() {
        mPowerSystem.powerOn();
        mPowerSystem.powerOff();
    }

    @Override
    public void voiceSystem() {
        mVoiceSystem.turnUp();
        mVoiceSystem.turnDown();
    }

    @Override
    public void channelSystem() {
        mChannelSystem.next();
        mChannelSystem.prev();
    }
}
