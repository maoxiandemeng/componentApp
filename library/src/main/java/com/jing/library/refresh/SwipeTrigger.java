package com.jing.library.refresh;

/**
 *
 */
public interface SwipeTrigger {
    void onPrepare();

    void onMove(int y, boolean isComplete, boolean automatic);

    void onRelease();

    void onComplete();

    void onReset();
}
