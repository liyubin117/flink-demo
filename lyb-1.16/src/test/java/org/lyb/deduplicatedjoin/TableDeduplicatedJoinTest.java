package org.lyb.deduplicatedjoin;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.test.junit5.MiniClusterExtension;
import org.apache.flink.types.Row;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.lyb.deduplicatedjoin.records.Customer;
import org.lyb.deduplicatedjoin.records.CustomerSupplier;
import org.lyb.deduplicatedjoin.records.TestData;
import org.lyb.deduplicatedjoin.records.Transaction;
import org.lyb.utils.CookbookKafkaCluster;
import org.lyb.utils.MiniClusterExtensionFactory;
import org.lyb.utils.records.DuplicatingTransactionSupplier;

import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.assertj.core.api.Assertions.assertThat;
import static org.lyb.deduplicatedjoin.TableDeduplicatedJoin.CUSTOMER_TOPIC;
import static org.lyb.deduplicatedjoin.TableDeduplicatedJoin.TRANSACTION_TOPIC;

class TableDeduplicatedJoinTest {

    @RegisterExtension
    static final MiniClusterExtension FLINK =
            MiniClusterExtensionFactory.withDefaultConfiguration();

    /**
     * Runs the production job against an in-memory Kafka cluster.
     *
     * <p>This is a manual test because this job will never finish.
     */
    @Test
    @Disabled("Not running 'testProductionJob()' because it is a manual test.")
    void testProductionJob() throws Exception {
        try (final CookbookKafkaCluster kafka = new CookbookKafkaCluster()) {
            kafka.createTopicAsync(CUSTOMER_TOPIC, Stream.generate(new CustomerSupplier()));
            kafka.createTopicAsync(
                    TRANSACTION_TOPIC, Stream.generate(new DuplicatingTransactionSupplier()));

            TableDeduplicatedJoin.runJob();
        }
    }

    @Test
    void ResultsAreDeduplicatedAndJoined() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        DataStream<Customer> customerStream = env.fromElements(TestData.CUSTOMERS);
        DataStream<Transaction> transactionStream = env.fromElements(TestData.TRANSACTIONS);

        final DataStream.Collector<Row> testSink = new DataStream.Collector<>();

        TableDeduplicatedJoin.defineWorkflow(
                tableEnv,
                customerStream,
                transactionStream,
                workflow -> workflow.collectAsync(testSink));

        env.executeAsync();

        assertThat(testSink.getOutput())
                .toIterable()
                .containsExactlyInAnyOrderElementsOf(TestData.EXPECTED_DEDUPLICATED_JOIN_RESULTS);
    }

    @Test
    public void DuplicatingTransactionTest() {
        final int NUM_TRANSACTIONS = 10;
        DuplicatingTransactionSupplier transactionSupplier = new DuplicatingTransactionSupplier();
        Stream<Transaction> streamWithDuplicates = Stream.generate(transactionSupplier);

        List<Transaction> distinct =
                StreamSupport.stream(
                                Spliterators.spliteratorUnknownSize(
                                        streamWithDuplicates.iterator(), Spliterator.ORDERED),
                                false)
                        .limit(NUM_TRANSACTIONS)
                        .distinct()
                        .collect(Collectors.toList());

        assertThat(NUM_TRANSACTIONS).isNotEqualTo(distinct.size());
    }
}
