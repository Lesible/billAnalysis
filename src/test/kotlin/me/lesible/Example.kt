package me.lesible;

import me.lesible.mapper.BillMapper
import me.lesible.model.Bill
import me.lesible.util.DBUtil
import org.junit.jupiter.api.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.stream.IntStream
import kotlin.random.Random
import kotlin.streams.toList

/**
 * <p>  </p>
 * <p> created at 2022-01-11 15:10 by lesible </p>
 *
 * @author 何嘉豪
 */
class Example {

    private val log: Logger = LoggerFactory.getLogger(this::class.java)

    @Test
    fun listAll() {
        DBUtil.sqlSession.use {
            val mapper = it.getMapper(BillMapper::class.java)
            val billList = mapper.selectAll()
            val mapping = billList.groupBy(Bill::orderNo)
            mapping.forEach { (key, list) ->
                run {
                    val lineMapping = list.groupBy(Bill::lineNo)
                    val values = lineMapping.values
                    for (index in values.indices) {

                    }
                }
            }
        }
    }

    @Test
    fun start() {
        val list = IntStream.generate { Random.nextInt(1, 10) }.limit(SIZE.toLong()).toList()
        log.info("list:{}", list)
        val sum = 10

    }

    private fun sumOfKNum(sum: Int, list: List<Int>, index: Int) {
        if (index == SIZE - 1) {
            return;
        }
    }

    companion object {
        private const val SIZE = 10
    }

}
