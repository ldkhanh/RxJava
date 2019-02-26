package examples;

import rx.Observable;

import java.util.concurrent.TimeUnit;

public class ZipInterval {

    public static void main(String... args) {
        Observable<String> data = Observable.just("One", "Two", "Three", "Four", "Five");
        Observable.zip(data, Observable.interval(500, TimeUnit.MILLISECONDS), (d,t) -> {
            return d + " " + t;
        }).toBlocking().forEach(System.out::println);
    }
}
