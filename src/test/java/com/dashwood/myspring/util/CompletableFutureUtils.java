package com.dashwood.myspring.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * 
 * @author 陈喜骋
 *
 */
public class CompletableFutureUtils {

    private static final ScheduledExecutorService scheduler =Executors.newScheduledThreadPool(1, new ThreadFactoryBuilder()
            .setDaemon(true)
            .setNameFormat("failAfter-%d")
            .build());

	public static CompletableFutureUtils newInstance() {
		return new CompletableFutureUtils();
	}

	public <T> CompletableFuture<List<T>> squence(List<CompletableFuture<T>> futureList) {

		CompletableFuture<Void> doneAllFuture = CompletableFuture.allOf(futureList.stream().toArray(CompletableFuture[]::new));

		return doneAllFuture.thenApply(
				v -> futureList.stream()
//                            .map(future -> future.exceptionally(throwable -> {
//                                System.out.println("aaa");
//                                return null;}))
							.map(CompletableFuture::join)
                            .filter(t -> t != null)
							.collect(Collectors.<T>toList())
				);
	}
	
	public <T> CompletableFuture<List<T>> squence(Stream<CompletableFuture<T>> futures) {
		
		List<CompletableFuture<T>> futureList = futures.filter(f -> f != null).collect(Collectors.toList());
		
		return squence(futureList);
	}

    public <T> CompletableFuture<T> failAfter(Duration duration) {
        final CompletableFuture<T> promise = new CompletableFuture<>();
        scheduler.schedule(() -> {
            final TimeoutException ex = new TimeoutException("Timeout after " + duration);
            return promise.completeExceptionally(ex);
        }, duration.toMillis(), MILLISECONDS);
        return promise;
    }

    public <T> CompletableFuture<T> within(CompletableFuture<T> future, Duration duration) {
        final CompletableFuture<T> timeout = failAfter(duration);
        return future.applyToEither(timeout, Function.identity());
    }

}






















