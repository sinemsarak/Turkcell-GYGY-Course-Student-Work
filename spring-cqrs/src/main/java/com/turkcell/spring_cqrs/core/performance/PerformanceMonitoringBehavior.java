package com.turkcell.spring_cqrs.core.performance;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.turkcell.spring_cqrs.core.mediator.pipeline.PipelineBehavior;
import com.turkcell.spring_cqrs.core.mediator.pipeline.RequestHandlerDelegate;

@Component
@Order(5)
public class PerformanceMonitoringBehavior implements PipelineBehavior {

    private static final long THRESHOLD_MS = 3000;

    @Override
    public <R> R handle(Object request, RequestHandlerDelegate<R> next) {
        String requestName = request.getClass().getSimpleName();
        long start = System.currentTimeMillis();

        R response = next.invoke();

        long elapsed = System.currentTimeMillis() - start;

        if (elapsed > THRESHOLD_MS) {
            System.out.println("[PERFORMANCE WARNING] " + requestName
                    + " çok yavaş çalıştı! Süre: " + elapsed + "ms (eşik: " + THRESHOLD_MS + "ms)");
        } else {
            System.out.println("[PERFORMANCE] " + requestName + " tamamlandı. Süre: " + elapsed + "ms");
        }

        return response;
    }
}
