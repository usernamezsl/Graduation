package me.jessyan.mvparms.graduation.app;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by jess on 8/30/16 16:39
 * Contact with jess.yan.effort@gmail.com
 */
public class EventBus {

    public static class StartBrotherEvent {
        SupportFragment mSupportFragment;

        public StartBrotherEvent(SupportFragment supportFragment) {
            mSupportFragment = supportFragment;
        }
    }

    public static class StartNextFragment {
        SupportFragment mSupportFragment;

        public StartNextFragment(SupportFragment supportFragment) {
            mSupportFragment = supportFragment;
        }
    }

    public static class ShowBottomBar {
        private String show;

        public ShowBottomBar(String show) {
            this.show = show;
        }
    }

    public static class HideBottomBar {
        private String hide;

        public HideBottomBar(String hide) {
            this.hide = hide;
        }
    }

    public static class NotifyDataHome {
        public NotifyDataHome() {
        }
    }

    public static class NotifyDataHot {
        public NotifyDataHot() {
        }
    }

    public static class FocusToNext {
        public SupportFragment mSupportFragment;

        public FocusToNext(SupportFragment supportFragment) {
            mSupportFragment = supportFragment;
        }
    }

    public static class RefreshData {
        public String reshe;

        public RefreshData(String reshe) {
            this.reshe = reshe;
        }
    }
}
