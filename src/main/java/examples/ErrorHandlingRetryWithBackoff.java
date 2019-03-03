package examples;


import rx.Observable;
import rx.functions.Action0;

public class ErrorHandlingRetryWithBackoff {
    public static void main(String[] args) {
        // retry(n) can be used to immediately retry n times
        Observable.create(s -> {
            System.out.println("1) subscribing");
            s.onError(new RuntimeException("1) always fails"));
            s.onNext("    next data");
        }).retry(3).doOnError(t -> System.out.println("Test: " + t)).doOnCompleted(System.out::println).subscribe(System.out::println, t -> System.out.println("1) Error: " + t));
        System.out.println("");

    }
}
