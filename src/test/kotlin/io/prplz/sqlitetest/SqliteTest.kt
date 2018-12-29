package io.prplz.sqlitetest

import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import kotlin.system.measureTimeMillis
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class SqliteTest {
    private lateinit var file: File
    private lateinit var connection: Connection

    @BeforeTest
    fun connect() {
        file = File.createTempFile(javaClass.name, ".sqlite")
        file.deleteOnExit()
        connection = DriverManager.getConnection("jdbc:sqlite:$file")
        connection.createStatement().use { statement ->
            statement.executeUpdate("create table points(id integer primary key, x integer, y integer)")
            statement.executeUpdate("create index location on points(x, y)")
        }
    }

    @AfterTest
    fun close() {
        connection.close()
    }

    private fun makePoints(count: Int) {
        connection.createStatement().use { statement ->
            for (i in 1..count) {
                val x = (-100..100).random()
                val y = (-100..100).random()
                statement.executeUpdate("insert into points(id, x, y) values ($i, $x, $y)")
            }
        }
    }

    @Test
    fun testInsert() {
        val count = 100
        val time = measureTimeMillis { makePoints(count) }
        println("Inserted $count points in ${time}ms")
    }

    @Test
    fun testSelect() {
        connection.autoCommit = false
        makePoints(1000)
        connection.commit()

        val queryCount = 10000
        var found = 0
        val time = measureTimeMillis {
            connection.prepareStatement("select id from points where x = ? and y = ?").use { statement ->
                for (i in 1..queryCount) {
                    statement.setInt(1, (-100..100).random())
                    statement.setInt(2, (-100..100).random())
                    statement.executeQuery().use { results ->
                        if (results.next()) {
                            found++
                        }
                    }
                }
            }
        }
        println("$queryCount queries found $found points in ${time}ms")
    }

    @Test
    fun testUpdate() {
        val count = 100

        connection.autoCommit = false
        makePoints(1000)
        connection.commit()
        connection.autoCommit = true

        val updateCount = 100
        val time = measureTimeMillis {
            connection.prepareStatement("update points set x = ?, y = ? where id = ?").use { statement ->
                for (i in 1..updateCount) {
                    statement.setInt(1, (-100..100).random())
                    statement.setInt(2, (-100..100).random())
                    statement.setInt(3, (1..count).random())
                    statement.executeUpdate()
                }
            }
        }
        println("Updated $updateCount rows in ${time}ms")
    }

    @Test
    fun testUpdateBigger() {
        val count = 100

        connection.autoCommit = false
        makePoints(10000)
        connection.commit()
        connection.autoCommit = true

        val updateCount = 100
        val time = measureTimeMillis {
            connection.prepareStatement("update points set x = ?, y = ? where id = ?").use { statement ->
                for (i in 1..updateCount) {
                    statement.setInt(1, (-100..100).random())
                    statement.setInt(2, (-100..100).random())
                    statement.setInt(3, (1..count).random())
                    statement.executeUpdate()
                }
            }
        }
        println("Updated $updateCount rows in ${time}ms")
    }

    @Test
    fun testUpdateHuge() {
        val count = 100

        connection.autoCommit = false
        makePoints(1000000)
        connection.commit()
        connection.autoCommit = true

        val updateCount = 100
        val time = measureTimeMillis {
            connection.prepareStatement("update points set x = ?, y = ? where id = ?").use { statement ->
                for (i in 1..updateCount) {
                    statement.setInt(1, (-100..100).random())
                    statement.setInt(2, (-100..100).random())
                    statement.setInt(3, (1..count).random())
                    statement.executeUpdate()
                }
            }
        }
        println("Updated $updateCount rows in ${time}ms")
    }
}