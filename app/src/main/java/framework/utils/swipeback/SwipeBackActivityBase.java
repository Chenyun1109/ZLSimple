package framework.utils.swipeback;

@SuppressWarnings("ALL")
interface SwipeBackActivityBase {
    
    SwipeBackLayout getSwipeBackLayout();

    void setSwipeBackEnable(boolean enable);

    void scrollToFinishActivity();
}
