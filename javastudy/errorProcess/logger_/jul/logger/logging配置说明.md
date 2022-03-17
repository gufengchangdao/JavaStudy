处理器配置
===========

### 1. FileHandler

```properties
# 处理器级别，默认为Level.ALL
java.util.logging.FileHandler.level=Level.ALL
# 文件名模式，默认 %h/java%u.log
# 变量描述：%h是系统属性user.home的值, %t是系统临时目录, %u是解决冲突的唯一编号，从0开始累加, %g是循环日志的生成号， %%是%字符
java.util.logging.FileHandler.pattern=src/javastudy/errorProcess/logger_/jul/java%u.log
# 在打开另一个文件之前允许写入一个文件的近似最大字节数(0表示无限制)，默认为50000
java.util.logging.FileHandler.limit=50000
# 循环序列中日志记录数量，默认为 1 。日志的循环功能就是从0到不断累加创建文件，如果文件超出大小限制就会删除老文件，其他的文件重新命名，同时从0开始创建
java.util.logging.FileHandler.count=1
# 跟线程有关系，不知道怎么用
java.util.logging.FileHandler.maxLocks=100
# 记录格式化器，默认为 java.util.logging.XMLFormatter
java.util.logging.FileHandler.formatter=java.util.logging.SimpleFormatter
# 日志是追加还是重写，默认为false
java.util.logging.FileHandler.append=true
# 要使用的过滤器类，默认不过滤
java.util.logging.FileHandler.filter=
# 字符编码，默认是平台的编码
java.util.logging.FileHandler.encoding=UTF-8
```
