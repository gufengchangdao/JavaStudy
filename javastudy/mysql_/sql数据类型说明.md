sql数据类型说明
================

#### 数值类型
+ bit(size)       位   0-64位
   1. bit后面数字表示多少位，最多64位(8个字节)
   2. 查询的时候用二进制数表示的
+ tinyint             1个字节
+ smallint            2个字节
+ mediumint           3个字节
+ int                 4个字节(2^32)
+ bigint              8个字节
+ float               单精度4个字节
+ double              双精度8个字节
+ decimal(M,D)        大小不确定
   1. M是小数位数的总和，最大为65；D是小数点后面的位数，最大为30
   2. 如果有的不填的，M默认是10，D默认是0(此时存的就只是整数了)

#### 文本类型(字符串类型)
+ char(size)          0-255字符  
    (1) char固定字符串长度
+ varchar(size)       0-65535(2^16-1)字节
   1. 虽然上限是65535字节，但是可用字节只有65532字节，因为有3个字节用来记录大小
   2. 如果编码为utf8，则括号内可填最大字符数为(65535-3)/3=21844，但是如果表中还有其他类型的，这个值会不行了  
   如果编码为gbk，则括号内可填最大字符数为(65535-3)/2=32766;
+ text                0-2^16-1
+ mediumtext
+ longtext            0-2^32-1
   细节:
   1. char是固定字符串长度，固定了大小(size)以后，使用不使用都会分配size字符的空间，而varchar、text、...不会，他们是变化的大小，
   2. 但是char的查询速度要高于varchar
   3. 应用：对于定长的数据，推荐使用char，长度不确定就使用varchar
   4. 可用text替代varchar，mediumtext、longtext长度大于text
   5. text和mediumtext、longtext字符对应的字节大小不同，数字就是1，文字就是3

#### 二进制数据类型
+ blob                0-2^16-1
+ longblob            0-2^32-1  
   blob支持任何数据，如文本、声音和图像

#### 日期类型
+ date            日期 年月日  
   2022-9-16
+ time            时间 时分秒  
   19:44:25
+ datetime        年月日时分秒  
   2022-9-16 19:44:25
+ timestamp       时间戳  
   1. 2022-9-16 19:44:25
   2. 可以对时间戳进行设置，在timestamp后面添加 not null default current_timestamp on update current_timestamp ，可以使得在进行insert 和 update 操作时会自动更新timestamp，但是好像默认的，不加也是自动更新！
+ year            年

#### 枚举类型
enum(field1,field2,...)  
   sex ENUM('男','女') NOT NULL

#### SET类型
food SET('骨头','鱼肉','香蕉','馒头','宠物粮食')  
   INSERT INTO pet VALUES('骨头,馒头');            添加时每个元素之间用逗号隔开

