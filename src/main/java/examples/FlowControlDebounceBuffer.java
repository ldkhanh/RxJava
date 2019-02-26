package examples;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class FlowControlDebounceBuffer {
    public static void main(String[] args) {
        // debounce to the last value in each burst
        // intermittentBurst().debounce(10, TimeUnit.MILISECONDS).toBlocking().forEach(System.out::println);

        // The following will emit a buffered list as it is debounced
        // first we multicast the stream ... using refCount so it handles the subscribe/unsubscribe

        Observable<Integer> burstStream = intermittentBursts().take(20).publish().refCount();
        // then we get the debounced version
        Observable<Integer> debounced = burstStream.debounce(10, TimeUnit.MILLISECONDS);
        Observable<List<Integer>> buffered = burstStream.buffer(debounced);
        buffered.toBlocking().forEach(System.out::println);
    }

    public static Observable<Integer> intermittentBursts() {
        return Observable.create((Subscriber<? super Integer> s) -> {
            while (!s.isUnsubscribed()) {
                // burst some number of items
                for (int i = 0; i < Math.random() * 20; i++) {
                    s.onNext(i);
                }
                try {
                    // sleep for a random amount of time
                    Thread.sleep((long) (Math.random() * 1000));
                } catch (Exception e) {

                }
            }
        }).subscribeOn(Schedulers.newThread());
    }
}
