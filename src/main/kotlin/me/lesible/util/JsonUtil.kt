package me.lesible.util

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.slf4j.LoggerFactory
import java.io.IOException
import java.text.SimpleDateFormat

/**
 *
 * jackson 封装
 *
 * date 2020-11-18 15:07
 *
 * @author k6428
 */
object JsonUtil {
    private val OBJECT_MAPPER = ObjectMapper()
    private val log = LoggerFactory.getLogger(JsonUtil::class.java)
    private val TYPE_MAP: MutableMap<String, JavaType?> = HashMap(16)
    fun <T> parseJson(json: String?, clazz: Class<T>): T? {
        try {
            return OBJECT_MAPPER.readValue(json, clazz)
        } catch (e: IOException) {
            log.error("json:{} 转为 {} 对象失败", json, clazz.name, e)
        }
        return null
    }

    fun <T> parseJsonList(json: String?, clazz: Class<T>): List<T>? {
        val key = "list" + clazz.name
        var type = TYPE_MAP[key]
        if (type == null) {
            val typeFactory = OBJECT_MAPPER.typeFactory
            type = typeFactory.constructParametricType(ArrayList::class.java,
                MutableList::class.java, clazz)
            TYPE_MAP[key] = type
        }
        try {
            return OBJECT_MAPPER.readValue(json, type)
        } catch (e: IOException) {
            log.error("json:{} 转为 {} 集合失败", json, clazz.name, e)
        }
        return null
    }

    fun <T> parseJsonArray(json: String?, clazz: Class<T>): Array<T>? {
        val key = "array" + clazz.name
        var type = TYPE_MAP[key]
        if (type == null) {
            val typeFactory = OBJECT_MAPPER.typeFactory
            type = typeFactory.constructArrayType(clazz)
            TYPE_MAP[key] = type
        }
        try {
            return OBJECT_MAPPER.readValue(json, type)
        } catch (e: IOException) {
            log.error("json:{} 转为 {} 数组失败", json, clazz.name, e)
        }
        return null
    }

    fun <K, V> parseJsonMap(json: String?, keyClazz: Class<K>, valueClazz: Class<V>): Map<K, V>? {
        val key = keyClazz.name + ":" + valueClazz.name
        var type = TYPE_MAP[key]
        if (type == null) {
            val typeFactory = OBJECT_MAPPER.typeFactory
            type = typeFactory.constructMapType(HashMap::class.java, keyClazz, valueClazz)
            TYPE_MAP[key] = type
        }
        try {
            return OBJECT_MAPPER.readValue(json, type)
        } catch (e: IOException) {
            log.error("json:{} 转为 map 失败", json, e)
        }
        return null
    }

    fun writeJson(`object`: Any?): String {
        try {
            return OBJECT_MAPPER.writeValueAsString(`object`)
        } catch (e: JsonProcessingException) {
            log.error("{} 转换成 json 字符串失败", `object`, e)
        }
        return ""
    }

    fun writePrettyJson(`object`: Any?): String? {
        try {
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(`object`)
        } catch (e: JsonProcessingException) {
            log.error("{} 转换成 json 字符串失败", `object`, e)
        }
        return null
    } /*public static void main(String[] args) throws Exception {
        ProcessBuilder ipconfig = new ProcessBuilder("C:\\Windows\\System32\\wbem\\WMIC.exe",
                "/node:12.99.229.188", "/user:administrator", "process", "call", "create", "\"cmd.exe /c ipconfig>c:\\relic.txt\"");
        ipconfig.redirectInput(new File("test.txt"));
        ipconfig.redirectError(new File("result.txt"));
        Process start = ipconfig.start();
        BufferedWriter bfw = new BufferedWriter(new OutputStreamWriter(start.getOutputStream()));
        BufferedReader bfr = new BufferedReader(new InputStreamReader(start.getInputStream(), "GBK"));
        String line;
        while ((line = bfr.readLine()) != null) {
            System.out.println(line);
        }

    }*/

    init {
        OBJECT_MAPPER.registerModule(JavaTimeModule())
        OBJECT_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        OBJECT_MAPPER.dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }
}