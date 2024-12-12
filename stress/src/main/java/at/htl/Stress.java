package at.htl;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Stress {
    private static final AtomicInteger performedRequests = new AtomicInteger(0);
    private static final AtomicInteger failures = new AtomicInteger(0);
    private static final AtomicLong maxResponseTime = new AtomicLong(Long.MIN_VALUE);
    private static final AtomicLong minResponseTime = new AtomicLong(Long.MAX_VALUE);

    private static void printUsage() {
        System.err.println("[usage]: <url> <total-requests> <mode>");
        System.err.println("where total-requests > 0");
        System.err.println("mode is one of GET,POST");
    }

    public static void main(String[] args) {
        if (args.length < 3) {
            printUsage();
            return;
        }

        String url = args[0];
        int totalRequests = Integer.parseInt(args[1]);
        String mode = args[2];
        System.out.printf("Performing %,d requests%n", totalRequests);

        if (totalRequests <= 0) {
            printUsage();
            return;
        }

        HttpRequest request;

        if (mode.equalsIgnoreCase("GET")) {
            request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
        } else if (mode.equalsIgnoreCase("POST")) {
            request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .POST(HttpRequest.BodyPublishers.ofString(
                            """
                                     {
                                       "firstName": "Horst",
                                       "lastName": "Flock",
                                       "email": "h.flock@yahoomail.com",
                                       "address": "Bauernweg 5, 4033 Basel",
                                       "dateOfBirth": "2003-11-21"
                                     }
                                    """
                    ))
                    .build();
        } else {
            printUsage();
            return;
        }

        long start;
        try (HttpClient client = HttpClient.newHttpClient()) {
            start = System.nanoTime();
            for (int i = 0; i < totalRequests; i++) {
                long requestStart = System.nanoTime();
                // tag::send_webrequest[]
                client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                        .thenRun(() -> {
                            long elapsed = System.nanoTime() - requestStart;
                            minResponseTime.getAndAccumulate(elapsed, Math::min);
                            maxResponseTime.getAndAccumulate(elapsed, Math::max);
                        })
                        .thenRun(performedRequests::incrementAndGet)
                        .exceptionally(t -> {
                            failures.incrementAndGet();
                            performedRequests.incrementAndGet();
                            return null;
                        });
                // end::send_webrequest[]
            }
        }

        while (performedRequests.get() < totalRequests) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        long end = System.nanoTime();
        long totalElapsedNanos = end - start;

        System.out.printf("Total: %f ms%n", totalElapsedNanos / 1000000.0);
        System.out.printf("Avg: %f ms%n", totalElapsedNanos / (double) totalRequests / 1000000.0);
        System.out.printf("Min: %f ms%n", minResponseTime.get() / 1000000.0);
        System.out.printf("Max: %f ms%n", maxResponseTime.get() / 1000000.0);
        System.out.printf("Failures: %d%n", failures.get());
    }
}
