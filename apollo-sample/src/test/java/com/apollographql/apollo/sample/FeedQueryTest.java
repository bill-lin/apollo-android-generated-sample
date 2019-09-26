package com.apollographql.apollo.sample;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.fetcher.ApolloResponseFetchers;
import com.apollographql.apollo.sample.type.FeedType;
import com.apollographql.apollo.subscription.WebSocketSubscriptionTransport;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

class FeedQueryTest {
	private static final String BASE_URL = "https://api.githunt.com/graphql";
	private static final String SUBSCRIPTION_BASE_URL = "wss://api.githunt.com/subscriptions";
	ApolloCall<FeedQuery.Data> githuntFeedCall;
	private static final int FEED_SIZE = 20;


	@Test
	public void testFetchFeed() throws InterruptedException {
		final FeedQuery feedQuery = FeedQuery.builder()
				.limit(FEED_SIZE)
				.type(FeedType.HOT)
				.build();
		githuntFeedCall = apolloClient()
				.query(feedQuery)
				.responseFetcher(ApolloResponseFetchers.NETWORK_FIRST);


		ApolloCall.Callback<FeedQuery.Data> dataCallback
				= new ApolloCall.Callback<FeedQuery.Data>(){

			@Override
			public void onResponse(@NotNull Response<FeedQuery.Data> response) {
				System.out.println("response: " + response.data().toString());
			}

			@Override
			public void onFailure(@NotNull ApolloException e) {
				e.printStackTrace();
				fail(e.getMessage());
			}
		};

		githuntFeedCall.enqueue(dataCallback);
		Thread.sleep(10000);
	}



	public ApolloClient apolloClient() {
		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.build();

		return  ApolloClient.builder()
			.serverUrl(BASE_URL)
			.okHttpClient(okHttpClient)
			.subscriptionTransportFactory(new WebSocketSubscriptionTransport.Factory(SUBSCRIPTION_BASE_URL, okHttpClient))
			.build();
	}
}