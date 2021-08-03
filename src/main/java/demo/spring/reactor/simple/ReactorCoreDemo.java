package demo.spring.reactor.simple;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ReactorCoreDemo {

    public static void main(String[] args) {
        Flux.range(1, 8)
                .doOnRequest(n -> log.info("Request {} number", n))
                .doOnComplete(() -> log.info("Publisher COMPLETE 1"))
//                .publishOn(Schedulers.elastic())
                .map(i -> {
                    log.info("Publish {}", i);
//					return 10 / (i - 3);
                    return i;
                })
                .doOnComplete(() -> log.info("Publisher COMPLETE 2"))
//                .subscribeOn(Schedulers.single())
                .onErrorResume(e -> {
                    log.error("Exception {}", e.toString());
                    return Mono.just(-1);
                })
//				.onErrorReturn(-1)
                .subscribe(i -> log.info("Subscribe {}", i),
                        e -> log.error("error {}", e.toString()),
                        () -> log.info("Subscriber COMPLETE")//,
//                        s -> s.request(4)
                );
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        AtomicInteger atomicInteger = new AtomicInteger();
        atomicInteger.incrementAndGet();
        ConcurrentHashMap<String ,String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.get("asdf");
        concurrentHashMap.put("22", "2");
        concurrentHashMap.remove("22");
        concurrentHashMap.size();
//        CopyOnWriteArrayList
    }
}
