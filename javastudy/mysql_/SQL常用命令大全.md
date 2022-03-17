MySQL常用命令
==============

> sql老韩精髓：化繁为简，各个击破

## mysql约束:

+ ### primary key主键:

1. 两种创建方式
    1. field1 primary key
2. 定义表的最后(在括号内且最后一个字段后面要加',') primary key(field1)
3. 主键是特殊的唯一性索引，不能重复且不能为 null，一个表只能有一个主键
4. 复合主键，是多个字段合起来组成一个主键，这几个字段不能完全相同，使用第二种创建方式 primary key(field1,field2,...)
5. 一般每个表中都有主键，可用desc查询
6. 主键最好选用整形，避免使用字符串，字符串非常消耗空间和时间

+ ### not null:

1. 插入数据时必须为其赋值，但是设置了default后就不用赋值了

+ ### default:

1. 设置默认值，可以配合not null使用

+ ### unique:

1. 该列值不能重复，但是在没有指定not null时可以有多个null
2. 一张表可以有多个unique字段
3. unique和primary key的主要区别是key不能为null且一张表只能有一个primary key

+ ### foreign key 外键:

1. 创建方式:创建table1，在创建table2最后加上foreign key (table2_field) references table1(table1_field)
   table2_field是表2的字段，table1_field是表一的字段，两个字段建立外键关系
2. 一张表可以创建多个外键
3. 外键指向表的字段要求是primary key或是unique的，即不能重复
4. 表的类型要求是innodb，这样的表才支持外键
5. 外键字段的类型要和主键字段的类型一致(长度、字段名可以不同)，要求联系的时候两个值要可以相等
6. 外键的值必须要在主键(不是说的primary key)中联系的字段中存在，或者为null
7. 主键表不能随便删除，有外键数据联系就不能删除，外键表不能随便添加，主键中没有的值不能添加
8. 外键可以多对一(多个数据拥有相同的table2_field)，主键不能多对一(因为table1_field不能重复)

+ ### check 约束:

1. 创建方式: 字段名 类型 check(条件)
   sex varchar(10) check(sex in ('man','woman'))   #只能输入两种字符串了
2. 用于强制数据满足指定条件，在MYSQL中实现check功能，一般是在程序中控制，或者通过触发器完成
3. mysql5.7还不能实现check功能，在之后的版本可以实现

+ ### auto_increment自增长

1. 自增长必须配合primary key或者unique使用，也就是说不能重复。不过常配合primary key使用
2. 一个表只允许有一个字段自增长并且自增长修饰的字段为整数型的(虽然小数也可以使用但是非常少这样使用)
3. 在赋值的时候，对于自增长的字段，赋值为null或者不赋值就会按照 当前最大值+1 的方式自动赋值，手动赋值也可以，就是不能重复
4. 数据会按自增长的字段递增排序(没用order by下)，即便是后加入的字段数据
5. 自增长默认从1开始，也可以通过 alter table table1 auto_increment = 新的开始值; 当然如果新值小于当前最大值，设了也没用

## 索引:

1. 在海量数据的数据库内，索引可以明显提高查询速度，不用改程序，只需要一句话。但是创建的索引会占用一部分空间 select * from table1 where field1=常量; 数据量大的时候，查询速度快了不止一星半点儿
2. 在哪个字段上创建了索引，使用select的where查询查询哪个字段效率就会很高，但是只对创建索引的字段有用，其他字段依旧是原速度(对需要的字段创建)
3. 索引创建前查找是全表扫描，创建后就会形成一个索引的数据结构。Mysql索引主要有两种结构：B+Tree索引和Hash索引，hash查询非常快，但是键是散列
   的方式分布，并不支持范围查找和排序等功能；B+tree是mysql使用最频繁的，虽然速度比不上hash索引，但是更适合排序等操作。
4. 索引代价:磁盘占用；对增删改的效率由影响(因为索引的结构也要改变)
5. 创建表格时对字段修饰就会创建索引，这种方式创建的索引名称就是字段名，主键索引的名称为`primary`。创建索引就相当于对字段进行了修饰，删除索引， 字段对应的限制就也会被删除，
    + 使用unique修饰      <---> 创建唯一索引
    + 使用primary key修饰 <---> 创建主键索引
6. 自增长的字段在创建的时候设置了索引就无法删除，因为AUTO_INCREMENT需要
7. 只要索引名不同，可以创建多个索引
8. 索引数据类型的选择:
    + 越小的数据类型通常更好
    + 简单的数据类型更好: 整形优于字符、字符串，内置的日期和时间数据类型优于字符串形式的时间，整形保存IP地址
    + 尽量避免NULL: null会使运算变得更复杂，最好设置默认值
9. 常见索引:
    1. 普通索引 没有任何限制 create index `index_name` on table1(`field1`); alter table table1 add index `index_name`(`field1`);
    2. 唯一索引 索引列的值必须唯一，但允许有空值，如果某列的值不会重复，则优先考虑唯一索引(底层找到对应值就不会再找了)
       create unique index `index_name` on table1(`field1`); alter table table1 add unique (`field1`); alter
       table table1 add unique index `index_name`(`field1`); 创建表格时字段使用unique修饰
    3. 主键索引 特殊的唯一索引，不允许有空值 alter table table1 add primary key (field); 创建表格时字段使用primary key修饰
    4. 全文索引 仅可用于 MyISAM 表，针对较大的数据，生成全文索引很耗时好空间 alter table table1 add fulltext(field1);
    5. 组合索引 创建复合索引时应该将最常用（频率）作限制条件的列放在最左边，依次递减。好处:非主键索引会先查到主键索引的值再从主键索引上 拿到想要的值。但是覆盖索引可以直接在非主键索引上拿到相应的值，减少一次查询 alter
       table table1 add index `index_name`(field1,field2,...);
10. 索引查询:
    show index from table1; show indexes from table1; show keys from table1; desc table1; #不详细
11. 索引删除:
    drop index index_name on table1; drop index `primary` on table1; #在创建表时声明的主键键名就是`primary`
    alter table table1 drop index index_name; alter table table1 drop primary key; #使用alter删除主键不用指明主键名

## 事务:

+ ### 使用:

1. start transaction 开始一个事务
2. savepoint point1 设置保存点
3. rollback to point1 回退事务
4. rollback 回退全部事务(回退到事务的开启点)
5. commit 提交事务，所有的操作生效，不能回退

+ ### 细节:

1. 回退后，回退点到回退前之间的所有保存点都会失效，不能再回退
2. 如果不开始事务，默认情况下，dml操作(增删改)是自动提交的
3. mysql的事务机制需要innodb存储引擎才可以使用，
4. 开始一个事务还可以使用 set autocommit=off;

+ ### 事务的四特性:

1. 原子性:满足原子操作单元，对数据的操作要么全部执行，要么全部失败
2. 一致性:事务开始和完成，数据都必须保持一致
3. 隔离性:事务之间是相互隔离的，中间状态对外不可见
4. 持久性:数据的修改是永久的

+ ### 隔离级别出现的原因-->并发情况下事务引发的问题:

1. 脏读:A事务还未提交，B事务就读到了A事务的结果(破坏了隔离性)
2. 不可重复读:A事务在本次事务中，对自己未操作过的数据进行了多次读取，结果出现不一致或记录不存在的情况(破坏了一致性，update和delete)
3. 幻读:A事务在本次事务中，对自己未操作过的数据进行了多次读取，第一次读取时记录不存在，第二次读取时，记录出现了(破坏了一致性，insert)

+ ### 四种隔离级别只能解决响应的问题:

<table>
<tr><th>隔离级别          </th>    <th>脏读</th>  <th>不可重复读</th>  <th>幻读</th>  <th>加锁读                                           </th></tr>
<tr><td>read uncommitted</td>     <td>T  </td>  <td>T       </td>  <td>T  </td>  <td>不加锁                                           </td></tr>
<tr><td>read committed  </td>     <td>F  </td>  <td>T       </td>  <td>T  </td>  <td>不加锁                                           </td></tr>
<tr><td>repeatable read </td>     <td>F  </td>  <td>F       </td>  <td>T  </td>  <td>不加锁                                           </td></tr>
<tr><td>serializable    </td>     <td>F  </td>  <td>F       </td>  <td>F  </td>  <td>加锁(同一个表只能一个事务使用，其他事务操作该表会卡在这里)</td></tr>
</table>

+ ### 注意:

1. repeatable read 允许幻读，这是ANSI/ISO SQL标准的定义要求，运行幻读依然有非常大的隐患，mysql 在repeatable read 即可满足没有幻读的要 求(也就是说mysql这个级别避免发生幻读)
2. 不可重复读和幻读的区别：不可重复读的重点是修改，幻读的重点是插入或者删除了新数据。两都会造成系统错误，但是避免的方法则区别比较大，对于前者, 只需 要锁住满足条件的记录，对于后者, 要锁住满足条件及其相近的记录。
3. mysql的默认隔离级别为repeatable read，一般情况下没有特殊要求不需要更改

+ ### 查看事务隔离级别:

~~~
    select @@tx_isolation;          查看当前会话隔离级别
    select @@global.tx_isolation;   查看系统当前隔离级别
~~~

+ ### 设置事务隔离级别:

~~~
    set session transaction isolation level read uncommitted;   设置事务当前会话事务隔离级别为read uncommitted
    set global transaction isolation level serializable;        设置系统当前会话事务隔离级别为serializable
    还可以设置mysql的默认事务隔离级别，找到数据库所在位置：D:\My tool\mysql\mysql-5.7.19-winx64下,修改my.ini配置文件，添加transaction-			isolation=read committed        就可以设为read committed了
~~~

## 存储引擎:

+ 主要的存储引擎特点:

<table>
    <tr><th>存储引擎</th><th>批量插入速度</th> <th>事务安全</th>  <th>锁机制</th> <th>存储限制</th><th>空间使用</th><th>内存使用</th><th>支持外键</th></tr>
    <tr><td>Myisam</td><td>高</td><td></td><td>表锁</td><td>没有</td><td>低</td><td>低</td><td></td></tr>
    <tr><td>Innodb</td><td>低</td><td>支持</td><td>行锁</td><td>64TB</td><td>高</td><td>高</td><td>支持</td></tr>
    <tr><td>Memory</td><td>高</td><td></td><td>表锁</td><td>有</td><td>N/A</td><td>中等</td><td></td></tr>
    <tr><td>Archive</td><td>非常高</td><td></td><td>行锁</td><td>没有</td><td>非常低</td><td>低</td><td></td></tr>
</table>

+ 细节:

1. 表锁的级别高于行锁，效率就会低一些
2. Myisam不支持事务和外键，但是访问速度快，对事务完整性没有要求
3. Innodb提供了具体提交、回滚和崩溃恢复能力的事务安全，并且支持外键，但是比起Myisam，Innodb写效率差些并且会占用更多的磁盘空间来保存数据和索引
4. Memory使用存在内存中的内容来创建表，每个Memory表只实际对应一个磁盘文件，Memory类型的表访问速度非常快(因为数据是放在内存中的)， 并且默认是使用hash索引。但是一旦服务关闭，表中数据就会丢失，表的结构还在。

+ 如何选择:

1. 如果不需要事务，处理的只是基本的crud操作，那么就选Myisam
2. 如果需要事务就Innodb
3. Memory由于没有磁盘I/O的等待，速度极快，适合修改频率很高的数据，但是任何修改在服务器重启后都会消失(经典用法：用户的在线状态)

## 视图:

+ 描述:

1. 视图是根据基表(可以是多个基表)来创建的，视图是虚拟的表，视图映射到基表
2. 通过视图可以改变基表的数据，基表的改变也会影响视图的数据
3. 感觉视图就像一个向上转型的对象(这个对象就是基表)，对原数据只是引用，增改查只对部分数据有用，注意增加时，视图以外的数据默认为null,如果有 字段设置了not
   null，并且没有默认值，那视图就无法增加数据，对于删除还是可以间接删除基表数据的(没有外键啥的)

+ 优点:

1. 安全，需要保密的字段不让用户看到，还不用建立新表
2. 性能，关系数据库的数据长长会分表存储，使用外键建立这些表之间的关系，查询时经常会用到连接(JOIN)，效率比较低，使用视图可以将相关表和字段组 合在一起，可以避免使用JOIN查询数据
3. 灵活，可以基于不能轻易删除的旧表建立视图，在设计时可以减少很多改动

+ 操作:

~~~
    create view view1 as select field1,field2 from table1;  根据表创建视图，多表、嵌套表等表都可以创建视图
    create view view1 as select field1,field2 from view2;   根据视图创建视图
    select * from view1;    查询
    show create view view1; 显示视图创建指令
    drop view view1;        删除视图
    ...(其他指令和表差不多，就看成一张表就好)
~~~

## mysql管理:

+ mysql.user重要字段的说明:
    + host 为允许用户登录的位置，localhost表示该用户只允许本机登录，也可以指定IP地址，如:192.168.1.100
    + user 为用户名
    + authentication_string 为密码，是通过password()加密过的密码

+ 操作:

1. 创建用户

~~~
    create user '用户名' @'登录位置' identified by '密码';   这三个参数都是字符串
        create user 'zhangchao' @'localhost' identified by '123';
        create user tom;                        没有指定地址和密码，则表示任意地址都可以登录，登录时输密码时直接回车，输入内容就登不上了
        create user 'tom' @'%';                 和上面等同
        create user 'nescafe' @'128.168.%.%';   表示地址前面是128.168的都可以登录
~~~

2. 删除用户

~~~
    drop user '用户名' @'登录位置';
        drop user tom;      只能删除创建方式为这样的 'tom' @'%';
~~~

3. 修改密码

~~~
    set password=password('新密码');   修改用户自己密码
    set password for '用户名' @'登录位置'=password('新密码'); 修改他人密码(需要持有相应权限)
~~~

4. 用户授权
    + grant 权限列表 on 数据库.对象名 to '用户名' @'登录位置' identified by '密码'; GRANT SELECT，INSERT ON test.* TO 'zhangchao'
      @'localhost'; identified by '密码'可加可不加，加上时，如果密码不对，就会修改密码为自己写的，如果没有该用户，就会创建该用户再授权
    + 可授予的权限:
        + (会用的)all、alter、create、create uses、create view、delete、drop、index、insert、select、show databases、show view、update、
        + (不会用的)alter routine、create routine、create tablespace、create temporary tables、event、execute、file、grant option、
          lock tables、process、proxy、reference、reload、replication client、replication slave、shutdown、super、trigger、usage

5. 回收权限
    + revoke 要回收的权限 on 数据库.对象名 from '用户名' @'登录位置';
    + 数据库.对象名可用 * 替代这两个，表示所有的
    + 要回收的权限可以写多个，也可以用 all ，直接回收全部权限
6. 刷新权限 flush privileges; 一般不用刷新，老版的mysql需要重新加载一下权限，将权限信息从内存中写入数据库
7. 查看用户权限 show grants for '用户名' @'地址'; #赋予的所有指令，也是返回可以执行的指令

## 函数:

1. 聚合函数
   > 总数count：

   返回一个数，为指定的数据/表达式不为null的个数，括号内可以是表达式，括号内的数据不为null就算一个(0也算一个)，常配合if(表达式,非null变量,null)使用
    ~~~
    select count(*) from table1         所有数据
    select count(field) from table1     field不为空的数据个数
    select count(if(field is null,1,null))  计算field为null的数据的个数，这里的1换成其他的也可以，像20、'abc'、0
    select count(distinct field)            显示不重复field数据的个数
    ~~~
   细节:

        + *为(在范围内)所有的的数据，而field则还要剔除null的数据
        + 聚合函数可以在group后面的having的后面使用的,where后面不可以用；或者就是在select后面，不group也可以使用

   > 求和： 求出范围内所有field的和

   select sum(field) from table1

   > 平均：

   select avg(field) from table1
   > 最大：

   select max(field) from table1
   > 最小：
   >
   select min(field) from table1

3. 常用函数:
    + charset(str)    返回字串字符集
    + concat(str1,str2,...)   连接字串
    + instr(str,childStr) 返回childStr在str中出现的位置，没有返回0，注意从1开始
    + ucase(str)、lcase(str)   大小写转换
    + left(str,length)、right(str,length)  从左边/右边读取length个字符
    + length(str) 返回长度(按照字节)
    + replace(str,oldStr,newStr)  替换
    + strcmp(str1,str2)   逐行比较字符，返回-1 0 1
    + substring(str,position,length)  从指定位置(从1开始)截取指定长度
    + ltrim(str)、rtrim(str)、trim 去除前端/后端/前后端空格
   > 注意：函数下标是从1开始的，不是0；有些函数用的是字符，有点是字节
4. 常用数学函数:
    + abs(num)
    + bin(num)    十进制转二进制
    + hex(num)    转为16进制
    + conv(num,from_base,to_base) num由from_base进制转为to_base
    + ceiling(num)    向上取整
    + floor(num)      向下取整
    + format(num,sum) 保留sum位
    + least(num1,num2,num3,...)   求最小值，和min不同，可以多个比较
    + mod(num,denominator)    num除以denominator的余数
    + rand([seed])    返回随机数0<=v<=1
5. 时间日期相关函数:
    + current_date()      当前日期
    + current_time()      当前时间
    + current_timestamp() 当前时间戳
    + unix_timestamp()    返回距离1970-01-01 00:00:00所经过的秒数，注意是秒。可以用int型储存时间，具有很大的用处，比如php上的时间戳就是用秒表示
    + from_unixtime()     将上面的秒数转化为指定格式的日期(也可以是默认的)
      SELECT FROM_UNIXTIME(UNIX_TIMESTAMP(),'现在时间: %Y年%m月%d日%H时%i分%s秒') AS `time` FROM DUAL;
    + date_format()       将时间日期转化为指定格式
    + SELECT DATE_FORMAT(NOW(),'现在时间: %Y年%m月%d日%H时%i分%s秒'); 注意：大写Y为四位年，小写y为末两位；大写H为24，小写为12
    + now()               当前时间日期，和current_timestamp()等价
    + sysdate()           返回调用此函数的时间
    + date(datetime)      返回datetime的日期部分
    + date_add(date,interval date_value date_type) 给date日期/时间加上date_type类型的date_value日期/时间
    + year(date)、month(date)、... 返回指定的时间字段，根据要返回的字段类型来决定参数(比如要返回日期，就不能只传入时间，但是反过来可以)
      常用date_type:YEAR MONTH DAY HOUR MINUTE SECOND
    + date_sub(date,interval date_value date_type) 减去时间 select * from employee where date_sub(now(),interval 10
      minute) <= date_time; 输出时间距离在10分钟内的数据
      
      > 注意，时间之前可以直接相减，但是最好不要减去常数，格式会变得奇怪
      
    + datediff(date1,date2)   返回日期差(天)，前面减去后面
    + timediff(time1,time2)   返回时间差(差多少小时多少分多少秒)，前面减去后面，含有日期信息的话，也会计算为小时
    + timestamp(date_type,date1,date2)  返回指定字段差(可以是年月日秒)，注意是后面减去前面
      > 注意:
      > + date_add、date_sub、datediff、timediff的参数可以是date、datetime、timestamp
      > + 函数名后面可以不带括号的函数有:CURRENT_DATE、CURRENT_TIME、CURRENT_TIMESTAMP
      > + now和和current_timestamp等价，获得的是语句开始执行时的时间，sysdate返回的是这个函数执行时候的时间，在显示里面添加sleep()就能知道
      > + 日期可以直接比较大小 > < >= <=...
6. 加密和系统函数:
    + user()  查询正在使用数据库的用户 返回 用户名@IP地址
    + database()      数据库名称
    + md5(str)    使用md5加密方式加密str，返回32位字符串(固定的)
    + password()  SQL的加密方式，SQL中有一个mysql数据库用来存放用户的相关信息(主机、用户名、相关权限、密码、...)，其中密码就是默认
    + 用password()函数进行加密
7. 流程控制函数:
    + if(expr1,expr2,expr3)   类似三目运算符，第一个为false/0，值就是expr3，否则就是expr2
    + ifnull(expr1,expr2)     类似于设置默认值，如果expr1为null，值就是expr2，否则值就是expr1，很适合在筛选的时候给一些可能 为null的字段赋一个值
    ~~~
    select case when expr1 then expr2 when expr3 then expr4 else expr5 end  类似if-else if-else语句
        SELECT id,(SELECT
            CASE
            WHEN `name`='琪亚娜' THEN 'Kiana Kaslana'          #只要name是琪亚娜就替换为Kiana Kaslana，否则还是显示name
            ELSE `name` END) AS `name`,Salary
        FROM employee;
   ~~~
8. 自定义函数
    + 说明:
        1. 函数存储着一系列sql语句，调用函数就是一次性执行这些语句。所以函数可以降低语句重复
        2. 函数只会返回一个值，不允许返回一个结果集(多个值)
    + 创建:
   ~~~
        create function 函数名([参数列表]) returns 数据类型
        begin
         sql语句;
         return 值;
        end;
   ~~~
    + 调用:
   ~~~
        select 函数名([参数列表]) from ...
   ~~~
    + 示例:
   ~~~
        无参数
        create function test1() returns int
        begin
            declare c int;              /*声明一个int变量*/
            select id from class where cname="python" into c;       /*给变量赋值*/
            return c;
        end;
        select test1();
        含参数
        create function test2(name varchar(15)) returns int
        begin
            declare c int;
            select id from class where cname=name into c;   /*查询和传入的参数相等的数据*/
            return c;
        end;
        select test2("python");
   ~~~
    + 查看:
   ~~~
        show create function 函数名;               查看函数创建语句
        show function status [like 'pattern'];    查看所有函数
   ~~~
    + 修改:
   ~~~
        函数的修改只能修改一些如comment的选项，不能修改内部的sql语句和参数列表
        alter function 函数名 选项；
   ~~~
    + 删除:
   ~~~
        drop function 函数名;
   ~~~

## 关键字

### create:

1. 创建数据库:
   ~~~
   #啥也没指明，默认字符集为utf8，校对规则为utf8_general_ci(不区分大小写,这里的区分指的是查询，不代表不能存储)
   CREATE DATABASE database-name;
   #指定字符集
   CREATE DATABASE db2 CHARACTER SET utf8;
   #指定校对规则为uft8_bin(区分大小写)，默认不区分大小写
   CREATE DATABASE db3 CHARACTER SET utf8 COLLATE utf8_bin;
   #指定字符集和校对规则
   create database database_name character set utf8 collate utf8_bin engine innodb;    创建数据库
   #当数据库名称为关键字时，可以添加反引号来规避
   CREATE DATABASE `create`
   ~~~
2. 创建表:
   ~~~
   create table `name`(
      id int,
      `state` bit(64),
      sex enum('男','女') not null,                 #使用枚举
      ) character set utf8 collate utf8_bin engine innodb;
   create table tab_new like tab_old           使用旧表创建新表
   create table tab_new as select col1,col2… from tab_old definition only
   #新建的表如果没有指定字符集和核对，就会以数据库的为准
      CREATE TABLE `user`(
          id INT UNSIGNED,        #无符号的
          `state` BIT(8),         #bit后面数字表示多少位，范围在0~64，即最多可用8个字节，并且查询时用二进制数表示
          `password` VARCHAR(255),#varchar后面数字表示多少字符
          `birthday` DATE,
          money DECIMAL(65,30))  #钱一些需要高精度的数据就用decimal
          CHARACTER SET utf8 COLLATE utf8_bin ENGINE INNODB;  #声明字符集，校对规则
   根据已有的表创建新表：
   create table tab_new like tab_old   使用旧表创建新表，会保留表的格式，但是不保留数据
   create table tab_new as select col1,col2…from tab_old definition only
   ~~~
3. 创建索引:
   ~~~
   create index `index_name` on table1(`field1`);          普通索引
   create unique index `index_name` on table1(`field1`);   唯一索引
   ~~~
4. 创建视图:
   ~~~
   create view view1 as select field1,field2 from table1;  根据表创建视图，多表、嵌套表等表都可以创建视图
   create view view1 as select field1,field2 from view2;   根据视图创建视图
   ~~~
5. 创建用户:
   ~~~
   create user '用户名' @'登录位置' identified by '密码';   创建用户，这三个参数都是字符串
   ~~~
6. 创建函数:
   ~~~
   create function 函数名([参数列表]) returns 数据类型
   begin
      sql语句;
      return 值;
   end;
   ~~~

### drop:

1. 删除数据库:
   ~~~
   drop database database_name;
   ~~~
2. 删除表
   ~~~
   drop table table1;
   ~~~
3. 删除视图:
   ~~~
   drop view view1;
   ~~~
4. 删除用户:
   ~~~
   drop user '用户名' @'登录位置';
   ~~~
5. 删除函数:
   ~~~
   drop function 函数名;
   ~~~

### rename:

1. 修改表名
   ~~~
   rename table table1 to new_table;
   ~~~

### alter:

1. 添加:

~~~
   添加一个列，可添加多个
      alter table table1 add(
          image VARCHAR(255) NOT NULL DEFAULT '美',    #NOT NULL是不允许为null，DEFAULT '美'是设置默认值
          name char(255)
          );
   添加主键：
      Alter table tabname add primary key(col)
   创建索引:
      alter table table1 add index `index_name`(`field1`);            普通索引
      alter table table1 add unique `index_name`(`field1`);           唯一索引
      alter table table1 add unique index `index_name`(`field1`);     唯一索引
      alter table table1 add primary key (field);                     主键，创建的主键名就是`primary`
      alter table table1 add fulltext(field1);                        全文索引
      alter table table1 add index `index_name`(field1,field2,...);   组合索引
~~~

2. 修改:

~~~
   修改表类型
      alter table table1 character set utf8;
   修改表名
      alter table old_table rename to new_table;
   修改引擎:
      alter table table1 engine=innodb;

   修改字段属性
      alter table table1 modify field_name varchar(60) not null default '';
   修改字段
      alter table table1 change old_field new_field(属性....);
          ALTER TABLE employee CHANGE `image` `img` CHAR(66) NOT NULL DEFAULT''; #image名改为img并设置类型
   修改函数的comment选项
      alter function 函数名 选项；  不能修改函数内部语句
~~~

4. 删除:

~~~
   删除一个列
      alter table table1 drop field_name;
   删除索引/主键:
      alter table table1 drop index index_name;                       删除索引
      alter table table1 drop primary key;                            删除主键
~~~

### set:

~~~
设置事务隔离级别:
   set session transaction isolation level read uncommitted;   设置事务当前会话事务隔离级别为read uncommitted
   set global transaction isolation level serializable;        设置系统当前会话事务隔离级别为serializable
设置用户新密码(修改密码):
   set password=password('新密码');   修改用户自己密码
   set password for '用户名' @'登录位置'=password('新密码'); 修改他人密码(需要持有相应权限)
~~~

### insert:

~~~
添加表中数据:
#没有指明赋值变量就是按顺序给所有属性赋值(必须是所有)
insert into table1 values(value1,value2,value3,...);
#指明参数
insert into table1(field1,field2) values(value1,value2),(value1,value2),(value1,value2),...;
#直接复制表格
insert into table1(...) select field1,field2,... from table2;
insert into table1 select * from table1; #蠕虫复制，测试某个语句的效率会需要到海量数据
细节：
  (1) 参数类型要匹配，但是可以把字符数字作为参数放到需要数值类型的位置，可以自动转为数字
  (2) 列可以插入null，前提是该字段允许为null(在创建时没有 not null 这个修饰)
  (3) 如果是给所有字段赋值，可以不写字段名(有默认值的字段也得写)
  (4) 添加数据时，没有赋值的字段有默认值的是默认值，没有默认值的默认为null
~~~

### delete:

~~~
删除数据：
   delete from table1 (where 范围);
细节:
  (1) where用来指定删除范围，如果没有where会删除表中所有数据，跟删除表有所区别
      如果想清空表数据，可以使用truncate，但是注意delete可以回滚，但是truncate不可以回滚，使用 truncate table table_name;
      truncate后会使表和索引所占用的空间会恢复到初始大小；delete操作不会减少表或索引所占用的空间，drop语句将表所占用的空间全释放掉。
  (2) 如果想删除一个列的数据(重新赋值)可以用update，删除一个字段用alter
~~~

### truncate:

~~~
初始化表(清空数据):
   truncate table table1;
~~~

### update:

~~~
更新表中数据
#可赋值可运算
   update table1 set field1=value1,field2=field2+value2,field3=value3,... (where 范围)
   UPDATE employee SET Salary=Salary + 1000 WHERE `name`='李四'; #李四Salary在原先基础上加1000
细节:
   (1) where用来指定修改范围，如果没有where，会修改所有行的该字段
修改用户密码:
   update mysql.user set authentication_string=password('hsp') where user='root' and Host='localhost';修改 root 用户的密码为 hsp
~~~

### select使用:

#### 查看引擎:

~~~
  select @@tx_isolation;          查看当前会话隔离级别
  select @@global.tx_isolation;   查看系统当前隔离级别
~~~

#### 查看表中数据:

> select 字段、表达式、函数 from 范围、操作

1. select单表、多表查询:
    + 单表查询
   ~~~
      #查询整个表
          select * from table1;               查询整个表，*表示所有数据
          select table1.* from table1,table2; 多个表时，表.*也可以显示指定表的全部字段
      #查询指定字段
      select field1,field2,... table1;       显示顺序按照field1,field2,...
      #你甚至可以输出常量'天才'
      select field1,'天才' from table1;
      #查询其他数据库中的表
      select * from database1.table1;     不用切换数据库就可以查询表
   ~~~
    + 多表查询
   ~~~
      #前一个表的每一个数据对应后一张表的所有数据，采用笛卡尔积的方式合并成一张表，数据量为table1*table2
      select * from table1,table2;
      #多表查询的正确合并:显示需要的数据、写出正确的过滤条件where
      select table1.field1,field2,field3 from table1,table2 where table1.field1=table2.field1;
          #两表有相同字段时，要 表.字段 的方式表示，上一个例子只显示第一张表和第二张表的相同类型字段值相同的数据
          #比如field1为两表共有，field2为表一独有，field3为表二独有，这时每一个field1就连接着field2和field3
      #自连接，一张表变两张表，必须给表起别名
          select name1.field field_name1 name2.field field_name2 from table name1 table name2 where...
          #这里给表取了两个别名name1和name2，在使用field时要加别名，但是显示的时候不显示别名，因此你还可以给该字段又起别名
      注意，当字段为关键字时，错误的示范---->`table1.field1`  ，而正确的示范 `table1`.`field1`
   ~~~
    + 嵌套查询
   ~~~
      (1)用子表查询结果用来控制范围
      #表子查询(嵌套查询):    根据查询结果来筛选，查询结果只有一条可以使用=,>,...，多条时使用 in，表示只要等于任意一条时才显示
          #可以使用=、>、< ....要求查询结果只有一个，查询结果为0是就是null
          #注意使用 + - * / = > <> ... 在某个字段值和null运算的时候，结果都为null，所以运算前最好先使用ifnull()
          select * from table1 where field1 > (select field1 from table1 where ...)
          #表示查询结果是其中之一，注意嵌套的子查询的查询字段要求
          select * from table1 where field1 in (select field1 from table1 where ...)
          #查询结果为多个时，用all表示所有数据，也就是field1要大于所有数据，当然用一些函数代替也可以
          select * from table1 where field1 > all (select field1 from table1 where ...)
          #查询结果为多个时，用any表示任意数据，也就是field1大于任意一个数据就可以
          select * from table1 where field1 > any (select field1 from table1 where ...)
      (2)用子表查询结果作为临时表
      #子表的查询的字段就相当于临时表的所有字段，没有查询的字段就用不了；一般还给子表设置别名，不然太长了
      select table1.field1,table2.field1 from table1,(select field1,field2 from table2 where...) table2 where ...
          #每个部门薪水最大的员工信息，这个临时表中只有department和salary两个字段
              SELECT `name`,member.department,member.salary
                  FROM member,(SELECT department,MAX(salary) salary FROM member GROUP BY department) max_salary
                  WHERE member.salary=max_salary.salary
                  ORDER BY member.department,member.salary DESC;  #先按department升序再按member.salary降序
      (3)你甚至还可以用子表查询结果作为一个常量查询，虽然好像没什么卵用，而且好像查询结果只能是一个值
      select (select ...) from dual;
      (4)嵌套查询细节:
          (1) 子查询放在select 后面就仅仅支持标量子查询(仅能支持一行一列)，
              这个时候注意where和having的区别(having可使用别名而where不可以)
          (2) 放在from 后面支持表 子查询，
              就必须起别名
          (3) 放在where/having后面支持标量子查询(一般搭配着单行操作符使用)、列子查询(还一般搭配着多行操作符使用,in/ any/some/ all)、行子查询
          (4) 放在exists后面(相关子查询)
                  exists后面的子查询 #结果返回1或者0;先执行主查询,再执行子查询
                  ... where exists(select * from table1 where 条件);    可以用来判断哪些数据存在的
   ~~~
    + 多列子查询
   ~~~
      #同时查询多列，省去写多个and了
      select * from table1 where (field1,field2)=('a','圣芙蕾雅学园');  #和常量比较
      select * from table1 where (field1,field2)=(select field1,field2 from...);  #和返回的多列数据比较，这里用的=就得要求数据只有一个
   ~~~
    + 合并 union all
   ~~~
      #将table1和table2的所有数据都合并到相同的列上
      select * from table1 union select * from table2;
      细节:
          (1) union会去掉完全重复的列，union all不会去重
          (2) 合并只对字段总数有要求(报错)，对两表的字段名称、字段类型都没有要求。
          (3) 显示的顺序是前面的表，然后是后面的表，就是简单的叠加，字段的名称按照第一个表显示
   ~~~
2. select后面的 字段、表达式 部分:
    + 聚合函数的使用(搬到函数里面去了)
    + 其他函数的使用 注意：from之前的 字段、表达式可以写多个，写的顺序就是表中内容的输出顺序
    + distinct的使用:
   ~~~
      #滤掉完全重复的(重复与否只看查询的数据)
      select distinct * from table1   #这里是每个数据的所有字段都相等才算重复
   ~~~
    + as的使用:
      起别名，可以加在字段上、sum()上。where、order后面都可以使用该别名 起别名还可以不加as 直接在字段后面写上就可以了，如果别名之间含有空格，则需要用单引号或双引号括起来 像select `name` myName
      from...就起了个myName的别名 别名不仅可以改变显示的内容，在from后面的筛选中使用as替换函数还可以提高些效率
    + 简单运算的使用:
   ~~~
      select (field1 + field2 -field3 * field4 / field5) as `sum` from table1;    #as `sum`是取别名为sum，不取名字就是表达式
   ~~~
3. from之后的 范围、操作:
    + 排序order by的使用：
   ~~~
      select * from table1 order by field1 (asc),field2 desc;     #field1按升序排序，field1相等时field2按降序排序
      细节:
          (1) field1也可以是表达式、别名
          (2) 默认是按照升序(asc)排序的
          (3) 可以设多个排序规则，相等的时候就按下一个规则比较
   ~~~
    + 筛选where的使用:
   ~~~
      (1) 可使用运算符 +-*/, >、<、>=、<=、=、!=、<>(同!=)
      (2) between field1 and field2(在某一范围，闭区间), in(field1,field2,...)(显示列表中的值), not between 、not in
      (3) like    %表示0到多个字符， _表示任意单个字符
              like '张%'、not like '张%'、like '_潮'
              select * from table1 where field1 like ’%value1%’;  表示含有value1的字段field1
      (4) is null     要查询某个字段为null的数据，不能使用 = ,必须使用 is null
          查询非null，就得使用 is not null，不能用<>、!=
      (5) and、or、not                      逻辑运算符
   ~~~
    + 亚元表dual
   ~~~
      SELECT INSTR('wudishiduomo','wudi') FROM DUAL;
      dual亚元表，为系统表，可以作为测试表使用，当然省事的做法是FROM DUAL 都省去
   ~~~
    + mysql.user 表
   ~~~
      mysql数据库中的user表被用来存放用户的相关信息(主机、用户名、相关权限、密码、...)
          select * from mysql.user;
   ~~~
    + limit start,rows分页查询
   ~~~
      显示从第(start+1)条语句开始，rows条数据，start从0开始，跟数组一样
      limit 每页页数*(第几页 - 1), 每页页数
          SELECT id,`name`,Salary FROM employee
              ORDER BY Salary DESC        #Salary按降序排序，再显示第二页(第11~15条信息)
              LIMIT 10,5;
   ~~~
    + 分组group by的使用:
   ~~~
     (1) 聚合函数(count,sum,max,min,avg)分组的标准，group by就像是改变这些函数的计算方式，获得多个解
     (2) group by相当于数据的合并，把零散的数据依据某些字段(像班级、部门)合并，使用分组后就只能显示这些字段了，被合并的字段不能再显示了
     (3) 不能以text,image类型的字段作为分组依据，常用数字、字符作为分组依据
     (4) 多层分组相当于细分
         SELECT id,part,AVG(Salary) AS `avg` FROM employee GROUP BY id,part HAVING `avg`>=8000;
         #说明：显示id、id下的所有part、每种id下所有part对应的avg(实际上也只能显示这三种值了)
         #先按照id进行分组，在每个id下再对part进行分组
         #分组后进行筛选，只显示`avg`>=8000的id-part-avg数据
   ~~~
    + where 和 having 的区别
   ~~~
     (1) where和having都可以对数据进行筛选，但是where只能在分组(group by)之前使用，对非count,sum,max,min,avg这些函数的字段、表达式使用
         而having只能在分组之后使用，只能对分组的依据使用
     (2) having有时候可以替代where使用，
         having不能使用 select 和from之间没有列出的字段，而where不能使用select后写的常量的别名(但是having却可以)，换言之where只可以使用常量和表中所有的数据
   ~~~
    + 多子句查询顺序:
   ~~~
     where ... group by ... having ... order by ... limit ...
   ~~~
    + 表外连接
   ~~~
     (1)左外连接
     select table1.field1,table2.field2 from table1 left join table2 on table1.field1=table2.table1;
         SELECT students.sno,cname,degree FROM students          #多表连接
             LEFT JOIN(SELECT sno,cno,degree FROM scores) sco ON students.sno=sco.sno
             LEFT JOIN (SELECT cno,cname FROM courses) cou ON sco.cno=cou.cno;
     (2)右外连接
     select table1.field1,table2.field2 from table1 right join table2 on table1.field1=table2.table1;
     (3)内连接
     ... inner join ... on ....
         SELECT cno FROM courses WHERE tno IN(SELECT tno FROM teachers WHERE depart='计算机系');
         #使用inner替代where
         SELECT cno FROM courses INNER JOIN teachers ON depart='计算机系' AND courses.tno=teachers.tno;
     (4)完全连接
     ... full join ... on ...
     说明:
         (1) 表外连接也类似于两表列项排列
         (2) 表外连接就是一主一辅，左外连接是左边表格内容完整，右边根据筛选条件来显示(符号条件就是数据，否则就是null)，以左表为基准，左表一条记录如果
             对应右表多条记录，那查出的数据中右表的数据也只显示一条，如果要都显示，可以用group_contact()将字段用逗号隔开显示在一条记录上
         (3) on很像where，后面接筛选条件，但是不同于where，一个主只会有一个附属，使用完on后还可以使用where筛选
         (4) 显示用的哪个表格的字段就比较重要了(平常如果同名字段值也相同就无所谓)，如果一组数据没符合条件，那个它的所有字段都为null，用该表格表示就是null了
         (5) ifnull()用来解决运算时字段为null情况，外连接用来解决整条数据都没有的情况
         (6) 内连接是条件成立才显示两表，好像和where没什么区别，可以顶替where；完全连接是两表全部显示，但是on中条件不满足的字段为null
   ~~~
4. select的优化(主要是防止查询时使用的不是索引而是全表扫描作的优化)

~~~
      (1) 在 where 及 order by 涉及的列上建立索引
      (2) 避免在 where 子句中使用!=或<>操作符
      (3) 避免在 where 子句中对字段进行 null 值判断(判断很复杂)，像where num is null就不好，最好设置默认值
      (4) 避免在 where 子句中使用 or 来连接条件，可以用union all替换，像
          select id from t where num=10 or num=20 替换为
          select id from t where num=10 union all select id from t where num=20
      (5) MySQL只有对以下操作符才使用索引：<，<=，=，>，>=，BETWEEN，IN，以及某些时候的LIKE
          在使用like时，避免使用通配符（%或者_）开头，像'Mich%'将使用索引，而'%hello'不会使用索引，必须开头的话可以使用全文检索
      (6) in 和 not in 也要慎用，否则会导致全表扫描，能用between代替就代替
      (7) 使用exists()替换in好一点，比如num in(select num from b)替换为exists(select 1 from b where num=a.num)
      (8) 在 where 子句中使用参数，也会导致全表扫描
      (9) 避免在 where 子句中对字段进行表达式操作，像num/2=100 替换为num=2*100
      (10) 避免在where子句中对字段进行函数操作，能用 like和操作符什么的替换就替换
      (11) 不要在 where 子句中的“=”左边进行函数、算术运算或其他表达式运算
      (12) 在使用索引字段作为条件时，如果该索引是复合索引，那么必须使用到该索引中的第一个字段作为条件时才能保证系统使用该索引，否则该索引将不会被使用，
          并且应尽可能的让字段顺序与索引顺序相一致
      (13) 当索引列有大量数据重复时，索引的用处就会很小，比如在字段sex中只有male和female两个值
      (14) 索引不是越多越好，索引会降低 insert 及 update 的效率，因为 insert 或 update 时有可能会重建索引
      (15) 尽量使用数字型字段作为索引
      (16) 尽可能的使用 varchar/nvarchar 代替 char/nchar
      (17) 尽量避免向客户端返回大数据量
      (18) 任何地方都不要使用 select * from t ，用具体的字段列表代替“*”，不要返回用不到的任何字段
      (19) 临时表并不是不可使用，适当地使用它们可以使某些例程更有效，例如，当需要重复引用大型表或常用表中的某个数据集时。但是，对于一次性事件，最好使用导出表
      (20) 数据量很大的时候尽量避免使用游标，因为游标的效率较差
~~~

### desc:

~~~
    #查看表/视图的结构
    desc table1;
    desc view1;
~~~

### show:

+ 展示索引:

~~~
	show index from table1;
	show indexes from table1;
	show keys from table1;
~~~

+ 展示所有引擎:

~~~
  	show engines;
~~~

+ 展示表的创建信息:

~~~
  	show create table table1;       #就直接显示了表的创建指令，可以看存储引擎、字符集
~~~

+ 展示视图创建信息:

~~~
	show create view view1; 显示视图创建指令
~~~

+ 展示数据库内所有表的相关信息

~~~
  	show table status from database1;
~~~

+ 展示给予的用户权限

~~~
  	show grants for '用户名' @'地址';   #赋予的所有指令，也是返回可以执行的指令
~~~

+ 展示函数

~~~
    show create function 函数名;               查看函数创建语句
    show function status [like 'pattern'];    查看所有函数
~~~

## 数据类型说明:

1. 数值类型

~~~
    bit(size)       位   0-64位
        (1) bit后面数字表示多少位，最多64位(8个字节)
        (2) 查询的时候用二进制数表示的
    tinyint             1个字节
    smallint            2个字节
    mediumint           3个字节
    int                 4个字节(2^32)
    bigint              8个字节
    float               单精度4个字节
    double              双精度8个字节
    decimal(M,D)        大小不确定
    numeric(M,D)
        (1) M是小数位数的总和，最大为65；D是小数点后面的位数，最大为30
        (2) 如果有的不填的，M默认是10，D默认是0(此时存的就只是整数了)
        (3) numeric和decimal是同一类型
~~~

2. 文本类型(字符串类型)

~~~
    char(size)          0-255字符
        (1) char固定字符串长度
    varchar(size)       0-65535(2^16-1)字节
        (1) 虽然上限是65535字节，但是可用字节只有65532字节，因为有3个字节用来记录大小
        (2) 如果编码为utf8，则括号内可填最大字符数为(65535-3)/3=21844，但是如果表中还有其他类型的，这个值会不行了
    如果编码为gbk，则括号内可填最大字符数为(65535-3)/2=32766;
        text                0-2^16-1
        mediumtext
        longtext            0-2^32-1
    细节:
        (1) char是固定字符串长度，固定了大小(size)以后，使用不使用都会分配size字符的空间，而varchar、text、...不会，他们是变化的大小，
        (2) 但是char的查询速度要高于varchar
        (3) 应用：对于定长的数据，推荐使用char，长度不确定就使用varchar
        (4) 可用text替代varchar，mediumtext、longtext长度大于text
        (5) text和mediumtext、longtext字符对应的字节大小不同，数字就是1，文字就是3
~~~

4. 二进制数据类型

~~~
    blob                0-2^16-1
    longblob            0-2^32-1
~~~

5. 日期类型

~~~
    date            日期 年月日
        (1) 2022-9-16
    time            时间 时分秒
        (1) 19:44:25
    datetime        年月日时分秒
        (1) 2022-9-16 19:44:25
    timestamp       时间戳
        (1) 2022-9-16 19:44:25
        (2) 可以对时间戳进行设置，在timestamp后面添加 not null default current_timestamp on update current_timestamp ，可以使得在进行			insert 和 update 操作时会自动更新timestamp，但是好像默认的，不加也是自动更新！
    year            年
~~~

6. 枚举类型

~~~
    enum(field1,field2,...)
    	sex ENUM('男','女') NOT NULL
~~~

## 一些初始化操作:

+ 启动MYSQL服务：net start mysql
+ 停止MYSQL服务：net stop mysql
+ 连接MYSQL数据库

~~~
  mysql -h   主机名   -P  端口 -u 用户名 -p密码
  mysql -h 127.0.0.1 -P 3306 -u root -phsp
~~~

1. 连接前要先启动MYSQL服务
2. 注意端口前面的P要大写。密码和-p之间没有空格
3. 如果没有写-h，默认就是本机
4. 如果没有写-P，默认就是3306，实际工作中一般都不是用3306
5. -p后面如果没有密码，回车会要求输入密码

+ 选择MYSQL数据库：use mysql;
    1. 这里选择要进入的数据库名称
    2. 使用其他的数据库的表时，可以再次切换也可以直接使用 数据库名.表名

## 符号说明:

~~~
    go <-- \g <-- \G <-- ;
        无所不在的分号命令结束符;实际上是/g命令的简写，而/g命令本身就是go命令的简写。
        go命令在历史上和当前其他类型的SQL中都被用来提交一批命令，由服务器编译和/或解释。
        /G命令似乎从/G继承了它的特征字母，并被大写以进一步表明修改的行为
~~~

## 操作:

+ 备份和恢复:
    + 备份db3(另存为):
   ~~~
       mysqldump -u 用户名 -p 密码 -B 数据库1 数据库2 (数据库...) > 保存的地址
           mysqldump -u root -p -B db1 db3 > d:\\temp.sql
       mysqldump -u 用户名 -p 密码 数据库 表1 表2 (数据库...) > 保存的地址
           mysqldump -u root -p db3 users > d:\\temptable.sql
           该操作在DOS执行
           -p后面可以跟密码，不跟密码后面会要求输入密码
   ~~~
    + 恢复:
   ~~~
       source 地址
       source d:\\temptable.sql
       该操作需要进入MYSQL后在执行
       也可以不同该方法，直接把备份的内容(其实保存的就是sql命令)复制粘贴到MYSQL里面就可以
   ~~~
+ 表中数据去重:

~~~
    create table table2 like table1;                     #创建临时表
    insert into table2 select distinct * from table1;    #把表的非重复数据传给临时表
    delete from table1;                                  #删除旧表信息
    insert into table1 select distinct * from table2;    #再把非重复信息传给旧表
    drop table table2;                                   #删除临时表
~~~

+ 查询表中最高分学生信息:

~~~
  (1) 如果最高分只有一个人，可以使用limit限制输入一行数据
      SELECT Sno,Cno FROM Scores ORDER BY Degree DESC LIMIT 1;
  (2) 使用all(any)
      SELECT sno,cno FROM scores WHERE degree >= ALL(SELECT degree FROM scores);
  (3) 使用max函数查询结果作为外表查询条件
      SELECT MAX(degree) FROM scores; -- 标量子查询，先查最高分
      SELECT sno,cno FROM scores WHERE degree=(SELECT MAX(degree) FROM scores);   -- 再作为查询条件
~~~

+ 多项比较:

~~~
    (1) and、or--->union all
    (2) 多个and配合in(表查询)
    (3) in --->between
    (4) exists , ...where exists(select ... where field1=... and ...)
    (5) any、all
    (6) 多列子查询 , (...)=(select field1,field2,field3...)
~~~

+ 在多表查询时，要注意:

~~~
    (1)思考，想要的数据需要从哪个表拿取，哪些表可以提供哪些需要的数据，先写嵌套表、子表，从简入繁
    (2)根据要求和查询的效率选择合适运算符、函数
    (3)难点在于考虑哪些表是主哪些表是辅，辅表的数据缺失怎么办，字段为null怎么办，处理不好可能会丢失某些数据而不自知
    整条数据都没有可以考虑左右外连接、全连(诶，我就是不用内连，但是内联可以替换where)；某个字段可能为null在对其使用运算符时就用ifnull
~~~