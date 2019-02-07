package com.eresh.spring.boot.batch.service;

import com.eresh.spring.boot.batch.persistance.domain.BatchDetails;
import com.eresh.spring.boot.batch.persistance.domain.User;
import com.eresh.spring.boot.batch.persistance.repository.BatchDetailsRepository;
import com.eresh.spring.boot.batch.persistance.repository.UserRepository;
import com.eresh.spring.boot.batch.util.BatchDetailsEnum;
import com.eresh.spring.boot.commons.exception.ApplicationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author Gorantla, Eresh
 * @created 07-02-2019
 */
@Service
public class DataProcessingService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BatchDetailsRepository batchDetailsRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ApplicationContext applicationContext;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    @Qualifier("fixedThreadPool")
    private ExecutorService fixedThreadPool;

    @Autowired
    @Qualifier("databasePersistThreadPool")
    private ExecutorService databasePersistThreadPool;

    @Autowired
    @Qualifier("baseRetryTemplate")
    private RetryTemplate myRetryTemplate;

    public void saveUserData() {
        try {
            File propertiesFile = ResourceUtils.getFile("data.json");
            List<User> users = objectMapper.readValue(propertiesFile, objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
            Instant start = Instant.now();
            users.parallelStream().forEach(user -> {
                userRepository.save(user);
            });
            Instant end = Instant.now();
            Duration interval = Duration.between(start, end);
            System.out.println("Process completed in " + interval.getSeconds() + " seconds");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void saveUserDataAsync() {
        try {
            File propertiesFile = ResourceUtils.getFile("data.json");
            List<User> users = objectMapper.readValue(propertiesFile, objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
            users = users.stream().limit(100000).collect(Collectors.toList());
            AtomicInteger count = new AtomicInteger();
            Integer size = 10000;
            Collection<List<User>> collection = users.stream().collect(Collectors.groupingBy(it -> count.getAndIncrement() / size)).values();
            Instant start = Instant.now();
            CompletableFuture<Void> onNextCall = null;
            for (Collection coll : collection) {
                onNextCall = CompletableFuture.runAsync(() -> executeAsynchronously(new ArrayList<User>(coll)), databasePersistThreadPool);
            }
            onNextCall.get();
            Instant end = Instant.now();
            Duration interval = Duration.between(start, end);
            System.out.println("Process completed in " + interval.getSeconds() + " seconds");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveUserDataAsynchWithRetry() throws ApplicationException  {
        try {
            File propertiesFile = ResourceUtils.getFile("data.json");
            List<User> users = objectMapper.readValue(propertiesFile, objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
            users = users.stream().limit(10).collect(Collectors.toList());
            users = users.stream().map(user -> modifyuserId(user)).collect(Collectors.toList());
            AtomicInteger count = new AtomicInteger();
            Integer size = 10;
            Collection<List<User>> collection = users.stream().collect(Collectors.groupingBy(it -> count.getAndIncrement() / size)).values();
            Instant start = Instant.now();
            //CompletableFuture<Void> onNextCall = null;
            for (Collection coll : collection) {
                executeAsynchronouslyWithRety(new ArrayList<User>(coll));
            }
            //onNextCall.get();
            Instant end = Instant.now();
            Duration interval = Duration.between(start, end);
            System.out.println("Process completed in " + interval.getSeconds() + " seconds");
        } catch (Exception e) {
            throw new ApplicationException(e);
        }
    }

    private User modifyuserId(User user) {
        user.setFirstName(null);
        return user;
    }

    @Async
    private void processAsynch(List<User> users) {
        executeAsynchronously(users);
    }

    @Autowired
    public void getJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void executeAsynchronously(List<User> users) {
        BatchDetails batchDetails = null;
        try {
            batchDetails = persistBatchDetails(users);
            Instant start = Instant.now();
            userRepository.saveAll(users);
            Instant end = Instant.now();
            Duration interval = Duration.between(start, end);
            batchDetails.setProcessingTime(interval.getSeconds());
            batchDetails.setStatus(BatchDetailsEnum.COMPLETED.value());
        } catch (Exception e) {
            batchDetails.setStatus(BatchDetailsEnum.FAILED.value());
            throw new RuntimeException();
        } finally {
            updateBatchDetails(batchDetails);
        }
    }

    public void executeAsynchronouslyWithRety(List<User> users) {
        myRetryTemplate.execute(context -> {
            System.out.println("In retry Mechanism");
            executeAsynchronously(users);
            return null;
        });

    }

    private BatchDetails persistBatchDetails(List<User> users) throws Exception {
        BatchDetails batchDetails = new BatchDetails();
        batchDetails.setBatchSize(users.size());
        String data = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(users);
        //batchDetails.setData(data);
        batchDetails.setStatus(BatchDetailsEnum.INITIATED.value());
        return batchDetailsRepository.save(batchDetails);
    }

    private BatchDetails updateBatchDetails(BatchDetails batchDetails) {
        return batchDetailsRepository.save(batchDetails);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void saveUserData1() {
        try {
            File propertiesFile = ResourceUtils.getFile("data.json");
            List<User> users = objectMapper.readValue(propertiesFile, objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
            Instant start = Instant.now();
            users.stream().forEach(user -> {
                userRepository.save(user);
            });
            Instant end = Instant.now();
            Duration interval = Duration.between(start, end);
            System.out.println("Process completed in " + interval.getSeconds() + " seconds");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveUserData2() {
        try {
            File propertiesFile = ResourceUtils.getFile("data.json");
            List<User> users = objectMapper.readValue(propertiesFile, objectMapper.getTypeFactory().constructCollectionType(List.class, User.class));
            Instant start = Instant.now();
            users.parallelStream().forEach(user -> {
                userRepository.save(user);
            });
            Instant end = Instant.now();
            Duration interval = Duration.between(start, end);
            System.out.println("Process completed in " + interval.getSeconds() + " seconds");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<BatchDetails> getBatchDetails() {
        return batchDetailsRepository.findAll();
    }
}
