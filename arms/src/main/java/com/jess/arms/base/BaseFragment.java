package com.jess.arms.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.mvp.Presenter;
import com.trello.rxlifecycle.LifecycleProvider;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.RxLifecycle;
import com.trello.rxlifecycle.android.FragmentEvent;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;

import org.simple.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by jess on 2015/12/8.
 */
public abstract class BaseFragment<P extends Presenter> extends SupportFragment implements LifecycleProvider<FragmentEvent> {
    //控制rx生命周期使用
    private final BehaviorSubject<FragmentEvent> lifecycleSubject = BehaviorSubject.create();

    protected BaseActivity mActivity;
    protected View mRootView;
    protected final String TAG = this.getClass().getSimpleName();
    @Inject
    protected P mPresenter;
    private Unbinder mUnbinder;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
        if (useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().registerSticky(this);//注册到事件主线
        ComponentInject();
    }

    /**
     * 依赖注入的入口
     */
    protected abstract void ComponentInject();


    @Override
    public void onDestroyView() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY_VIEW);
        super.onDestroyView();
        if (mUnbinder != Unbinder.EMPTY) mUnbinder.unbind();
    }

    @Override
    public void onDestroy() {
        lifecycleSubject.onNext(FragmentEvent.DESTROY);
        super.onDestroy();
        if (mPresenter != null) mPresenter.onDestroy();//释放资源
        if (useEventBus())//如果要使用eventbus请将此方法返回true
            EventBus.getDefault().unregister(this);
        this.mPresenter = null;
        this.mActivity = null;
        this.mRootView = null;
        this.mUnbinder = null;
    }

    /**
     * 是否使用eventBus,默认为使用(true)，
     *
     * @return
     */
    protected boolean useEventBus() {
        return true;
    }


    protected abstract View initView();

    protected abstract void initData();


    /**
     * 此方法是让外部调用使fragment做一些操作的,比如说外部的activity想让fragment对象执行一些方法,
     * 建议在有多个需要让外界调用的方法时,统一传bundle,里面存一个what字段,来区分不同的方法,在setData
     * 方法中就可以switch做不同的操作,这样就可以用统一的入口方法做不同的事,和message同理
     * <p>
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onActivityCreated
     * 还没执行,setData里调用presenter的方法时,是会报空的,因为dagger注入是在onActivityCreated
     * 方法中执行的,如果要做一些初始化操作,可以不必让外部调setData,在内部onActivityCreated中
     * 初始化就可以了
     *
     * @param data
     */
    public void setData(Object data) {

    }

    /**
     * 使用此方法时请注意调用时fragment的生命周期,如果调用此setData方法时onActivityCreated
     * 还没执行,setData里调用presenter的方法时,是会报空的,因为dagger注入是在onActivityCreated
     * 方法中执行的,如果要做一些初始化操作,可以不必让外部调setData,在内部onActivityCreated中
     * 初始化就可以了
     */
    public void setData() {

    }
    //*************rxLifecycle**************************
    @Override
    @NonNull
    @CheckResult
    public final Observable<FragmentEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindUntilEvent(@NonNull FragmentEvent event) {
        return RxLifecycle.bindUntilEvent(lifecycleSubject, event);
    }

    @Override
    @NonNull
    @CheckResult
    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return RxLifecycleAndroid.bindFragment(lifecycleSubject);
    }

    @Override
    @CallSuper
    public void onAttach(android.app.Activity activity) {
        super.onAttach(activity);
        lifecycleSubject.onNext(FragmentEvent.ATTACH);
    }

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = initView();
        //绑定到butterknife
        mUnbinder = ButterKnife.bind(this, mRootView);
        initData();
        return mRootView;
    }

    @Override
    @CallSuper
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lifecycleSubject.onNext(FragmentEvent.CREATE_VIEW);
    }

    @Override
    @CallSuper
    public void onStart() {
        super.onStart();
        lifecycleSubject.onNext(FragmentEvent.START);
    }

    @Override
    @CallSuper
    public void onResume() {
        super.onResume();
        lifecycleSubject.onNext(FragmentEvent.RESUME);
    }

    @Override
    @CallSuper
    public void onPause() {
        lifecycleSubject.onNext(FragmentEvent.PAUSE);
        super.onPause();
    }

    @Override
    @CallSuper
    public void onDetach() {
        lifecycleSubject.onNext(FragmentEvent.DETACH);
        super.onDetach();
    }

    //*************rxLifecycle**********************
}
