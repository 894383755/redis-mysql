# 版本
1.0
# redis-mysql
科东-同步服务
## 转换操作
### 
## 各个包功能
* xxhyf    						信息化研发  该包下同步服务的业务逻辑，下面是各个包的介绍
* xxhyf.database.connection   	数据库连接池
* xxhyf.main   					同步服务的启动项
* xxhyf.main.core.Run             控制启动那个功能模块
* xxhyf.mysql_redis				历史库MySQL同步到实时库redis
* xxhyf.mysql_redis.core          历史库MySQL同步到实时库redis业务实现
* xxhyf.notice					待办任务入库
* xxhyf.notice.core               待办任务入库的业务实现
* xxhyf.resolveXml				xml解析方法（工具）
* xxhyf.static_model				静态模型入库
* xxhyf.static_model.core         静态模型入库服务业务实现
* xxhyf.synchro					同步服务
* xxhyf.synchro.core              同步服务业务实现
* xxhyf.util                      工具类
## 总体流程
1. RedisMysqlApplication springboot启动
2. Run 运行
    1. 各个线程任务初始化
        * 查询每个表的最大id 存储到redis
        1. 获取SYS_TABLEINFO表的所有id和表名
        2. 通过表明查询该表的最大id
        3. 将最大id存储到 REDISKEY+id+"_num" 下
    2. 执行各个任务线程
        * 静态模型 static_model
        * 模型同步 redis_Mysql
            1. 获取SYS_TABLEINFO表的所有id和表名
            2. 通过表id查询该表名，表字段和字段类型
            3. 将该表名，表字段和字段类型存储到 REDISKEY + tableId 下
            4. 判断表是否是静态信息表（id末尾为0）
                1. （static_data（））
        * 模型入库 static_model
            1. 从kafka获取xml
            2. 解析xml，转换成map
            3. 对于每个key 解析出ID和表号
                1. 获取到相应表号的域信息
                2. 插入或更新金泰模型表
                    1. 将查找映射名称从数据查找出来
                    2. 
        * 同步数据
        * 代办任务
# 信息
## 作者
王俊磊
# 需求
* 对某个表同步不同步有开关
