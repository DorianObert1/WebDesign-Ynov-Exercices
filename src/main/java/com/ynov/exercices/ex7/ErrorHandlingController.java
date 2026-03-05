package com.ynov.exercices.ex7;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class ErrorHandlingController {

    @GetMapping("/error-resume")
    public Flux<String> errorResume() {
        return Flux.just("A", "B", "C")
                .concatWith(Flux.error(new RuntimeException("Simulated error after C")))
                .onErrorResume(e -> Flux.just("Default1", "Default2"));
    }

    @GetMapping("/error-continue")
    public Flux<Integer> errorContinue() {
        return Flux.range(1, 5)
                .map(n -> {
                    if (n == 2) throw new RuntimeException("Simulated error for number 2");
                    return n;
                })
                .onErrorContinue((e, obj) -> System.out.println("Ignoring error for: " + obj));
    }
}
