package com.jing.componentapp.rxjava;

import android.util.Log;

import com.jing.library.utils.LogUtil;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by liujing on 2017/7/27.
 */

public class RxJavaDemo {

    private static final String TAG = "RxJavaDemo";

    /**
     * ObservableEmitter： Emitter是发射器的意思，这个就是用来发出事件的，它可以发出三种类型的事件，
     * 通过调用emitter的onNext(T value)、onComplete()和onError(Throwable error)就可以分别发出next事件、complete事件和error事件。
     *
     * 上游可以发送无限个onNext, 下游也可以接收无限个onNext.
     * 当上游发送了一个onComplete后, 上游onComplete之后的事件将会继续发送, 而下游收到onComplete事件之后将不再继续接收事件.
     * 当上游发送了一个onError后, 上游onError之后的事件将继续发送, 而下游收到onError事件之后将不再继续接收事件.
     * 上游可以不发送onComplete或onError.
     * 最为关键的是onComplete和onError必须唯一并且互斥, 即不能发多个onComplete, 也不能发多个onError,
     * 也不能先发一个onComplete, 然后再发一个onError.
     * 发送多个onComplete是可以正常运行的, 依然是收到第一个onComplete就不再接收了,
     * 但若是发送多个onError, 则收到第二个onError事件会导致程序会崩溃.
     *
     * 运行结果
     * onSubscribe: false
     1
     onNext: 1
     2
     onNext: 2
     onComplete:
     3
     *
     */
    public static void demoOne(){
        //创建一个被观察着（上游） 发射事件
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                System.out.println(1);
                e.onNext(1);
                System.out.println(2);
                e.onNext(2);
                e.onComplete();
                System.out.println(3);
                e.onNext(3);
            }
        });
        //创建一个观察着（下游）接受
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("onSubscribe: " + d.isDisposed());
                LogUtil.i("demoOne", "onSubscribe: " + d.isDisposed());
            }

            @Override
            public void onNext(@NonNull Integer i) {
                System.out.println("onNext: " + i);
                LogUtil.i("demoOne", "onNext: " + i);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println(e.getMessage());
                LogUtil.i("demoOne", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete: " );
                LogUtil.i("demoOne", "onComplete");
            }
        };
        observable.subscribe(observer);
    }

    /**
     * Disposable, 这个单词的字面意思是一次性用品,用完即可丢弃的
     * 当调用它的dispose()方法时, 它就会切断观察者和被观察者的联系
     * 后面被观察者发送的信息就接受不到
     *
     * 运行结果
     * onSubscribe: false
     1
     onNext: 1
     2
     onNext: true
     onNext: 2
     3
     */
    public static void demoTwo(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                System.out.println(1);
                e.onNext(1);
                System.out.println(2);
                e.onNext(2);
                System.out.println(3);
                e.onNext(3);
            }
        }).subscribe(new Observer<Integer>() {
            private Disposable disposable;
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("onSubscribe: "+d.isDisposed());
                disposable = d;
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                if (integer == 2) {
                    disposable.dispose();
                    System.out.println("onNext: "+disposable.isDisposed());
                }
                System.out.println("onNext: "+integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("onError: "+e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete: ");
            }
        });
    }

    /**
     * 线程调度
     * subscribeOn() 指定的是上游发送事件的线程, observeOn() 指定的是下游接收事件的线程.
     * 多次调用subscribeOn() 只有第一次的有效, 其余的会被忽略.
     * 多次指定下游的线程是可以的, 也就是说每调用一次observeOn() , 下游的线程就会切换一次.
     *
     * Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作
     * Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作
     * Schedulers.newThread() 代表一个常规的新线程
     * AndroidSchedulers.mainThread() 代表Android的主线程
     */
    public static void demoThree(){
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                System.out.println("Observable thread is : " + Thread.currentThread().getName());
                emitter.onNext(1);
            }
        });

        Consumer<Integer> consumer = new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println("Observer thread is :" + Thread.currentThread().getName());
                System.out.println("onNext: " + integer);
            }
        };

//        observable.subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(consumer);
        observable.subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("After observeOn(io), current thread is: " + Thread.currentThread().getName());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("After observeOn(mainThread), current thread is : " + Thread.currentThread().getName());
                    }
                })
                .subscribe(consumer);
    }

    /**
     * map转化
     */
    public static void demoFour(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return "This is result " + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("accept:" + s);
            }
        });

    }

    /**
     * flatMap
     * FlatMap将一个发送事件的上游Observable变换为多个发送事件的Observables，然后将它们发射的事件合并后放进一个单独的Observable里
     */
    public static void demoFive(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + integer);
                }
                return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });
    }

    /**
     * Zip通过一个函数将多个Observable发送的事件结合到一起，然后发送这些组合到一起的事件.
     * 它按照严格的顺序应用这个函数。它只发射与发射数据项最少的那个Observable一样多的数据
     */
    public static void demoSix(){
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
                Log.d(TAG, "emit 2");
                emitter.onNext(2);
                Log.d(TAG, "emit 3");
                emitter.onNext(3);
                Log.d(TAG, "emit 4");
                emitter.onNext(4);
                Log.d(TAG, "emit complete1");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d(TAG, "emit A");
                emitter.onNext("A");
                Log.d(TAG, "emit B");
                emitter.onNext("B");
                Log.d(TAG, "emit C");
                emitter.onNext("C");
                Log.d(TAG, "emit complete2");
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.io());
        //.subscribeOn(Schedulers.io() 表示observable1和observable2在不同线程中发送，
        //不然就在一个线程中操作，等observable1发送完数据才会发送observable2
        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, "onNext: " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });
    }

    /**
     * Backpressure背压
     *
     */
    public static void demoSeven(){
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; ; i++) {   //无限循环发事件
                    emitter.onNext(i);
                }
            }
        }).subscribeOn(Schedulers.io()).sample(2, TimeUnit.SECONDS);
        //sample每隔指定的时间就从上游中取出一个事件发送给下游. 这里我们让它每隔2秒取一个事件给下游
        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("A");
                emitter.onNext("A");
                emitter.onNext("A");
                emitter.onNext("A");
            }
        }).subscribeOn(Schedulers.io());

        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.w(TAG, throwable);
            }
        });

    }

    /**
     * Flowable
     * BackpressureStrategy.ERROR 上游发数据下游处理不及时时抛出错误
     * BackpressureStrategy.BUFFER 上游发数据不管有没有处理，但会发生内存溢出
     * BackpressureStrategy.DROP 直接把存不下的事件丢弃 会丢失数据
     * BackpressureStrategy.LATEST 只保留最新的事件  会丢失数据
     *
     * 同步情况下，上游得到下游的处理个数 上游每发送一个事件，requested的值便会减一
     * 异步情况下，不管下游能够处理多少个数，上游得到的都是128个
     */
    public static void demoEight(){
        Flowable<Integer> upstream = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                //emitter.requested()得到下游处理能力的个数
                Log.d(TAG, "subscribe: " + emitter.requested());
//                for (int i = 0; i < 100; i++) {
//                    Log.d(TAG, "emit "+i);
//                    emitter.onNext(i);
//                }
//                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR); //增加了一个参数

        Subscriber<Integer> downstream = new Subscriber<Integer>() {

            @Override
            public void onSubscribe(Subscription s) {
                Log.d(TAG, "onSubscribe");
                //注意这句代码 指定下游处理的个数 告诉上游它的处理能力
                s.request(1);
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: " + integer);

            }

            @Override
            public void onError(Throwable t) {
                Log.w(TAG, "onError: " + t.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };

        upstream
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(downstream);

    }
}
