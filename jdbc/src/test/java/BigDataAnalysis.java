import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

/**
 * @Author: Liuyuming
 * @Date: 2020/2/3 16:10
 */
public class BigDataAnalysis {
    public static final int conThreads = 1000;
    public static final int totalCount = 10000000;
    public static CountDownLatch countDownLatch = new CountDownLatch(conThreads);
    public static CountDownLatch countDownLatch2 = new CountDownLatch(conThreads);
    public static final ConcurrentLinkedQueue<Connection> connections = new ConcurrentLinkedQueue<>();
    private static String[] usernames = {"tom", "jan", "rose", "John", "James", "Chase", "Eric", "Justin","sydml","Edison","Susan","Mrt"};
//    private static LinkedList<Connection> connections = new LinkedList<>();
    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
    private static ThreadFactory namedThreadFactory2 = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
    private static ThreadPoolExecutor pool = new ThreadPoolExecutor(conThreads, conThreads, 1000L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10000), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    private static ThreadPoolExecutor connPool = new ThreadPoolExecutor(100, 100, 1000L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10000), namedThreadFactory2, new ThreadPoolExecutor.AbortPolicy());
    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1000, 1000, 1000L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10000), namedThreadFactory2, new ThreadPoolExecutor.AbortPolicy());
    private static String sqlText = "insert into user (username,loginTime) values(?,?)";
    private static String querySqlText = "select * from user";
    
    static{
        /*try {
            for (int i = 0; i < conThreads; i++) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigdata?serverTimezone=GMT", "root", "root");
                connections.add(connection);
                connectionPool.add(connection);
            }
            System.out.println();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        
        
    }
    
    @Test
    public void createConnectionWithThreadPool(){
        long start = System.currentTimeMillis();
        for (int i = 0; i <conThreads ; i++) {
            connPool.execute(() -> {
                try {
                    createCon();
                    countDownLatch.countDown();
                } catch (ClassNotFoundException | SQLException | BrokenBarrierException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        try {
            countDownLatch.await();
            if (countDownLatch.getCount() == 0) {
                connPool.shutdown();
                System.out.println(connections.size());
                System.out.println("创建连接总耗时" + (System.currentTimeMillis() - start));
            }
        } catch (InterruptedException  e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createConnections() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < conThreads; i++) {
            try {
                createCon();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
        System.out.println(connections.size());
        System.out.println("创建连接总耗时" + (System.currentTimeMillis() - start));
    }

    private static void createCon() throws ClassNotFoundException, SQLException, InterruptedException, BrokenBarrierException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigdata?serverTimezone=GMT", "root", "root");
        connections.add(connection);
    }

    public static Connection getConnection() {
//        Connection connection = connections.removeFirst();
        Connection connection = connections.poll();
        return connection;
    }
    
    public static boolean closeConnection(Connection connection){
        return connections.offer(connection);
//        return connections.add(connection);
    }

    @Test
    public void test() throws InterruptedException, BrokenBarrierException {
        int threads = conThreads;
        int count = totalCount / threads;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(threads + 1);

        long start = System.currentTimeMillis();
        for (int i = 0; i < threads; i++) {
            pool.execute(() -> {
                try {
                    save(sqlText, cyclicBarrier, count);
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        cyclicBarrier.await();
        System.out.println("总耗时：" + (System.currentTimeMillis() - start));
    }

    private void save(String sqlText, CyclicBarrier cyclicBarrier, int count) throws BrokenBarrierException, InterruptedException {
        batchSave(sqlText, count);
        cyclicBarrier.await();
    }

    @Test
    public void batchSaveTest() {
        batchSave(sqlText, 100000);
    }

    private void batchSave(String sqlText, int count) {
        long start = System.currentTimeMillis();
        Random random = new Random();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigdata?serverTimezone=GMT", "root", "root");
            preparedStatement = connection.prepareStatement(sqlText);
            int circle = count/1000;
            for (int i = 0; i < circle; i++) {
                for (int j = 0; j < 1000; j++) {
                    preparedStatement.setString(1, usernames[random.nextInt(12)]);
                    preparedStatement.setObject(2, LocalDateTime.now().plusDays(-random.nextInt(10)));
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }
            
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("error getRunnable");
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
        System.out.println("每批耗时：" + (System.currentTimeMillis() - start));
    }


    @Test
    public void testByConPool() throws InterruptedException, BrokenBarrierException {
        createConnectionWithThreadPool();
        
        
        int threads = conThreads;
        int count = totalCount / threads;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(threads + 1);

        long start = System.currentTimeMillis();
        for (int i = 0; i < threads; i++) {
            pool.execute(() -> {
                try {
                    saveByConPool(sqlText, cyclicBarrier, count);
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        cyclicBarrier.await();
        System.out.println("总耗时：" + (System.currentTimeMillis() - start));
    }

    private void saveByConPool(String sqlText, CyclicBarrier cyclicBarrier, int count) throws BrokenBarrierException, InterruptedException {
        batchSaveFromConPool(sqlText, count);
        cyclicBarrier.await();
    }
    
    private void batchSaveFromConPool(String sqlText, int count) {
        long start = System.currentTimeMillis();
        Random random = new Random();
        Connection connection = getConnection();
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(sqlText);
            int circle = count/1000;
            for (int i = 0; i < circle; i++) {
                for (int j = 0; j < 1000; j++) {
                    preparedStatement.setString(1, usernames[random.nextInt(12)]);
                    preparedStatement.setObject(2, LocalDateTime.now().plusDays(-random.nextInt(10)));
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
                connection.commit();
            }

        } catch (SQLException e) {
            System.out.println("error getRunnable");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            if (connection != null) {
                closeConnection(connection);
            }
        }
        System.out.println("每批耗时：" + (System.currentTimeMillis() - start));
    }
    
    @Test

    public void batchSave(){
        createConnectionWithThreadPool();
        long start = System.currentTimeMillis();
        Random random = new Random();
        for (int i = 0; i < conThreads; i++) {
            threadPool.execute(()->{
                long singleStart = System.currentTimeMillis();
                Connection connection = null;
                PreparedStatement preparedStatement = null;
                try {
                    connection = getConnection();
                    connection.setAutoCommit(false);
                    for (int j = 0; j < 10; j++) {
                        preparedStatement = connection.prepareStatement(sqlText);
                        for (int k = 0; k < 1000; k++) {
                            preparedStatement.setString(1, usernames[random.nextInt(12)]);
                            preparedStatement.setObject(2, LocalDateTime.now().plusDays(-random.nextInt(10)));
                            preparedStatement.addBatch();
                        }
                        preparedStatement.executeBatch();
                        connection.commit();
                        System.out.println("每批耗时：" + (System.currentTimeMillis() - singleStart));
                    }
                } catch (Exception e) {
                    try {
                        connection.rollback();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    e.printStackTrace();
                }finally {
                    if (preparedStatement != null) {
                        try {
                            preparedStatement.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    closeConnection(connection);
                }
                countDownLatch2.countDown();
            });
        }
        try {
            countDownLatch2.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (countDownLatch2.getCount() == 0) {
            threadPool.shutdown();
            System.out.println("总耗时：" + (System.currentTimeMillis() - start));
            System.out.println("回收前连接池数量："+connections.size());
            for (int i = 0; i < conThreads; i++) {
                try {
                    getConnection().close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    @Test
    public void queryByStream() throws SQLException, ClassNotFoundException, IllegalAccessException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigdata?serverTimezone=GMT", "root", "root");
        PreparedStatement preparedStatement = connection.prepareStatement(querySqlText,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
        preparedStatement.setFetchSize(Integer.MIN_VALUE);
        long start = System.currentTimeMillis();
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println("流式查询耗时："+ (System.currentTimeMillis()-start));
        User[] users = new User[10];
        List<Tuple<Field, String, Class>> tuples = getTupleFields(User.class);
        int i=0;
        while (resultSet.next() && i<10) {
            User user = new User();
            setFieldValue(resultSet, tuples, user);
            users[i] = user;
            i++;
        }
        System.out.println();
    }

    private void setFieldValue(ResultSet resultSet, List<Tuple<Field, String, Class>> tuples, Object targetInstance) throws IllegalAccessException, SQLException {
        for (Tuple<Field, String, Class> tuple : tuples) {
            tuple._1.set(targetInstance, resultSet.getObject(tuple._2, tuple._3));
        }
    }

    private List<Tuple<Field, String, Class>> getTupleFields(Class target) {
        List<Tuple<Field, String, Class>> tuples = new ArrayList<>();
        Field[] declaredFields = target.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            tuples.add(new Tuple<>(declaredField, declaredField.getName(), declaredField.getType()));
        }
        return tuples;
    }

    @Test
    public void query() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigdata?serverTimezone=GMT", "root", "root");
        PreparedStatement preparedStatement = connection.prepareStatement(querySqlText,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
        preparedStatement.setFetchSize(10);
        long start = System.currentTimeMillis();
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println("普通查询耗时："+ (System.currentTimeMillis()-start));
        User[] users = new User[10];
        int i=0;
        while (resultSet.next() && i<10) {
            Long id = resultSet.getObject("id", Long.class);
            String username = resultSet.getObject("username", String.class);
            LocalDateTime loginTime = resultSet.getObject("loginTime", LocalDateTime.class);
            User user = new User();
            user.setId(id);
            user.setUsername(username);
            user.setLoginTime(loginTime);
            users[i] = user;
            i++;
        }
    }

    @Test
    public void queryWithCursor() throws SQLException, InterruptedException, BrokenBarrierException, ClassNotFoundException, IllegalAccessException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bigdata?useCursorFetch=true&serverTimezone=GMT", "root", "root");
        PreparedStatement preparedStatement = connection.prepareStatement(querySqlText,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
        preparedStatement.setFetchSize(10);
        long start = System.currentTimeMillis();
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println("游标查询耗时："+ (System.currentTimeMillis()-start));
        User[] users = new User[10];
        int i=0;
        List<Tuple<Field, String, Class>> tuples = getTupleFields(User.class);
        while (resultSet.next() && i<10) {
            
            /*Long id = resultSet.getObject("id", Long.class);
            String username = resultSet.getObject("username", String.class);
            LocalDateTime loginTime = resultSet.getObject("loginTime", LocalDateTime.class);*/
            User user = new User();
            setFieldValue(resultSet, tuples, user);
            
            /*user.setId(id);
            user.setUsername(username);
            user.setLoginTime(loginTime);*/
            users[i] = user;
            i++;
        }
    }
    
}
