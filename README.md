My desktop (SSD):

```
io.prplz.sqlitetest.SqliteTest > testUpdateHuge STANDARD_OUT
    Updated 100 rows in 1522ms

io.prplz.sqlitetest.SqliteTest > testUpdateBigger STANDARD_OUT
    Updated 100 rows in 1586ms

io.prplz.sqlitetest.SqliteTest > testInsert STANDARD_OUT
    Inserted 100 points in 1457ms

io.prplz.sqlitetest.SqliteTest > testSelect STANDARD_OUT
    10000 queries found 248 points in 22ms

io.prplz.sqlitetest.SqliteTest > testUpdate STANDARD_OUT
    Updated 100 rows in 1532ms
```

OVH server with HDD:
```
io.prplz.sqlitetest.SqliteTest > testUpdateHuge STANDARD_OUT
    Updated 100 rows in 16324ms

io.prplz.sqlitetest.SqliteTest > testUpdateBigger STANDARD_OUT
    Updated 100 rows in 14605ms

io.prplz.sqlitetest.SqliteTest > testInsert STANDARD_OUT
    Inserted 100 points in 12252ms

io.prplz.sqlitetest.SqliteTest > testSelect STANDARD_OUT
    10000 queries found 269 points in 45ms

io.prplz.sqlitetest.SqliteTest > testUpdate STANDARD_OUT
    Updated 100 rows in 13779ms
```
