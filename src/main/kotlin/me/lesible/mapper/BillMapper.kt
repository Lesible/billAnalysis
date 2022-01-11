package me.lesible.mapper

import me.lesible.model.Bill
import org.apache.ibatis.annotations.Select

/**
 * <p>  </p>
 * <p> created at 2022-01-11 15:00 by lesible </p>
 * @author 何嘉豪
 */
interface BillMapper {

    @Select("select * from bill")
    fun selectAll():List<Bill>

}