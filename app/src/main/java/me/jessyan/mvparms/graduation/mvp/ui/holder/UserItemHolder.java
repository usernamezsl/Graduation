package me.jessyan.mvparms.graduation.mvp.ui.holder;

/**
 * Created by jess on 9/4/16 12:56
 * Contact with jess.yan.effort@gmail.com
 */
//public class UserItemHolder extends BaseHolder<User> {
//
//    @Nullable
//    @BindView(R.id.iv_avatar)
//    ImageView mAvater;
//    @Nullable
//    @BindView(R.id.tv_name)
//    TextView mName;
//    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用glide,使用策略模式,可替换框架
//    private final WEApplication mApplication;
//
//    public UserItemHolder(View itemView) {
//        super(itemView);
//        //可以在任何可以拿到Application的地方,拿到AppComponent,从而得到用Dagger管理的单例对象
////        mApplication = (WEApplication) itemView.getContext().getApplicationContext();
//        mApplication = ((WEApplication) BaseApplication.getInstance());
//        mImageLoader = mApplication.getAppComponent().imageLoader();
//    }
//
//
//
//
//    @Override
//    protected void onRelease() {
//        mImageLoader.clear(mApplication, GlideImageConfig.builder()
//                .imageViews(mAvater)
//                .build());
//    }
//}
