package me.lesible.util

import org.apache.ibatis.builder.xml.XMLConfigBuilder
import org.apache.ibatis.session.*

/**
 * <p> create at 2022-01-11 14:53 </p>
 * @author lesible
 */
object DBUtil {
    private var SQL_SESSION_MANAGER: SqlSessionManager
    private fun getSqlSession(autoCommit: Boolean): SqlSession {
        SQL_SESSION_MANAGER.startManagedSession(autoCommit)
        return SQL_SESSION_MANAGER
    }

    val sqlSession: SqlSession
        get() = getSqlSession(true)

    fun getBatchSqlSession(autoCommit: Boolean): SqlSession {
        SQL_SESSION_MANAGER.startManagedSession(ExecutorType.BATCH, autoCommit)
        return SQL_SESSION_MANAGER
    }

    val batchSqlSession: SqlSession
        get() = getBatchSqlSession(true)

    fun close() {
        if (SQL_SESSION_MANAGER.isManagedSessionStarted) {
            SQL_SESSION_MANAGER.close()
        }
    }

    fun <T> getMapper(t: Class<T>?): T {
        return SQL_SESSION_MANAGER.getMapper(t)
    }

    init {
        val resourceAsStream = DBUtil::class.java.classLoader.getResourceAsStream("mybatis-config.xml")
        val sessionFactoryBuilder = SqlSessionFactoryBuilder()
        val configuration = XMLConfigBuilder(resourceAsStream, "dev").parse()
        val sqlSessionFactory: SqlSessionFactory = sessionFactoryBuilder.build(configuration)
        SQL_SESSION_MANAGER = SqlSessionManager.newInstance(sqlSessionFactory)
    }
}