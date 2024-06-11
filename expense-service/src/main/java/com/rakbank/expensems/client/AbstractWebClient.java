package com.rakbank.expensems.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public abstract class AbstractWebClient {

	private static final Logger log = LoggerFactory.getLogger(AbstractWebClient.class);

	@Autowired
	private WebClient.Builder loadBalanced;

	public void setLoadBalanced(WebClient.Builder loadBalanced) {
		this.loadBalanced = loadBalanced;
	}


	protected <S, T> Mono<T> postNoAuth(String url, S request, Class<T> response) {
		log.info("Post request url: {}", url);
		log.info("Post request body: {}", request);
		return loadBalanced.build()
				.post()
				.uri(url)
				.body(Mono.just(request), request.getClass())
				.retrieve()
				.bodyToMono(response);
	}

	protected <S, T> Mono<T> get(String url, Class<T> response) {
		log.info("Get request url: {}", url);
		return loadBalanced.build()
				.get()
				.uri(url)
				.retrieve()
				.bodyToMono(response);
	}

}
