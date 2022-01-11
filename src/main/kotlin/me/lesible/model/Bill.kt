package me.lesible.model;

/**
 * <p>  </p>
 * <p> created at 2022-01-11 14:58 by lesible </p>
 *
 * @author 何嘉豪
 */
data class Bill(
    val id: Long,
    val stockNum: Int,
    val soldNum: Int,
    val billNo: String,
    val orderNo: String,
    val lineNo: Int,
    val fee: Long,
)