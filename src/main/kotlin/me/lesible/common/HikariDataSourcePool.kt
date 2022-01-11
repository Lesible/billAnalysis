package me.lesible.common

import com.zaxxer.hikari.HikariDataSource
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory


/**
 * <p> create at 2022-01-11 14:53 </p>
 * @author lesible
 */
class HikariDataSourcePool : UnpooledDataSourceFactory() {
    init {
        dataSource = HikariDataSource()
    }
}