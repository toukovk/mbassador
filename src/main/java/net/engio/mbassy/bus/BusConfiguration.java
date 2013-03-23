package net.engio.mbassy.bus;

import net.engio.mbassy.listener.MetadataReader;
import net.engio.mbassy.subscription.SubscriptionFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * The bus configuration holds various parameters that can be used to customize the bus' runtime behaviour.
 *
 * @author bennidi
 *         Date: 12/8/12
 */
public class BusConfiguration {

    private static final ThreadFactory DaemonThreadFactory = new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread thread = Executors.defaultThreadFactory().newThread(r);
            thread.setDaemon(true);
            return thread;
        }
    };

    public static BusConfiguration Default() {
        return new BusConfiguration();
    }

    private int numberOfMessageDispatchers;

    private ExecutorService executor;

    private int maximumNumberOfPendingMessages;

    private SubscriptionFactory subscriptionFactory;

    private MetadataReader metadataReader;

    private MessagePublication.Factory messagePublicationFactory;

    public BusConfiguration() {
        this.numberOfMessageDispatchers = 2;
        this.maximumNumberOfPendingMessages = Integer.MAX_VALUE;
        this.subscriptionFactory = new SubscriptionFactory();
        this.executor = new ThreadPoolExecutor(10, 10, 1, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(), DaemonThreadFactory);
        this.metadataReader = new MetadataReader();
        this.messagePublicationFactory = new MessagePublication.Factory();
    }

    public MessagePublication.Factory getMessagePublicationFactory() {
        return messagePublicationFactory;
    }

    public void setMessagePublicationFactory(MessagePublication.Factory messagePublicationFactory) {
        this.messagePublicationFactory = messagePublicationFactory;
    }

    public MetadataReader getMetadataReader() {
        return metadataReader;
    }

    public BusConfiguration setMetadataReader(MetadataReader metadataReader) {
        this.metadataReader = metadataReader;
        return this;
    }

    public int getNumberOfMessageDispatchers() {
        return numberOfMessageDispatchers > 0 ? numberOfMessageDispatchers : 2;
    }

    public BusConfiguration setNumberOfMessageDispatchers(int numberOfMessageDispatchers) {
        this.numberOfMessageDispatchers = numberOfMessageDispatchers;
        return this;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public BusConfiguration setExecutor(ExecutorService executor) {
        this.executor = executor;
        return this;
    }

    public int getMaximumNumberOfPendingMessages() {
        return maximumNumberOfPendingMessages;
    }

    public BusConfiguration setMaximumNumberOfPendingMessages(int maximumNumberOfPendingMessages) {
        this.maximumNumberOfPendingMessages = maximumNumberOfPendingMessages > 0
                ? maximumNumberOfPendingMessages
                : Integer.MAX_VALUE;
        return this;
    }

    public SubscriptionFactory getSubscriptionFactory() {
        return subscriptionFactory;
    }

    public BusConfiguration setSubscriptionFactory(SubscriptionFactory subscriptionFactory) {
        this.subscriptionFactory = subscriptionFactory;
        return this;
    }
}
