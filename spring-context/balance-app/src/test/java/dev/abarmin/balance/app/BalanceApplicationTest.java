package dev.abarmin.balance.app;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.context.ApplicationContext;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

class BalanceApplicationTest {
    @ParameterizedTest
    @MethodSource("contexts")
    void startAndAcceptRequests(Supplier<ApplicationContext> supplier) throws Exception {
        ApplicationContext context = supplier.get();
        HttpHandler handler = context.getBean(HttpHandler.class);
        HttpServer server = HttpServer.create(new InetSocketAddress(9999), 0);
        server.createContext("/", handler);
        server.start();

        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString("""
                        {
                        "amount": "10",
                        "currency": "USD"
                        }
                        """))
                .uri(new URI("http://localhost:9999/balance/10/reserve"))
                .build();

        String responseBody = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString())
                .body();

        server.stop(1);

        assertThat(responseBody).isEqualTo("true");
    }

    static Collection<Supplier<ApplicationContext>> contexts() {
        return List.of(
                () -> BalanceApplication.getJavaContext(),
                () -> BalanceApplication.getAnnotationContext(),
//                () -> BalanceApplication.getContextWithComponentScan(), // need to add annotations everywhere
                () -> BalanceApplication.getXmlContext()
        );
    }
}