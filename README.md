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
2. start 运行（通过@PostConstruct）
    1. 各个线程任务初始化 init();
        * 查询每个表的最大id 存储到redis
        1. 获取SYS_TABLEINFO表的所有id和表名
        2. 通过表明查询该表的最大id
        3. 将最大id存储到 REDISKEY+id+"_num" 下
    2. 执行各个任务线程
        * 静态模型 static_model
        * 模型同步 redis_Mysql
            1. 获取SYS_TABLEINFO表的所有id和表名
            2. redis_MysqlImpl.run
                1. 通过表id查询该表名，表字段和字段类型
                2. 将该表名，表字段和字段类型存储到 REDISKEY + tableId 下
                3. 判断表是否是静态信息表（id末尾为0）
                    1. （static_data（））
            3. redis_MysqlImpl.runing_data
                1. 查询 REDISINFO 表并对每个结果执行以下
                2. REDISKEY + sql + "_key" 里放 key
                3. REDISKEY + sql + "_rule_" + keys[i] 放 参数map
                4. REDISKEY + sql + "_rule_" + rkey 获取所有结果
            4. redis_MysqlImpl.server() 同步服务器数据
                1. 查询服务器表 AUE_SERVER_B
                2. 将每条数据放入 REDISKEY + "AUE_SERVER_B_DATA", m.get("ID") + ""
                3. 将每条数据放入 REDISKEY + "SERVER_IP_DATA", m.get("IP") + "",m.get("ID")+""
            5. redis_MysqlImpl.syn_view() 同步视图
                1. 同步ALARMMODEL
                    1. 查询 OMPSE.ALARMMODEL
                    2. 每条数据 在 REDISKEY + "ALARMMODEL" 存放id
                    3. 每条数据 在 id 存放 结果map
                2. 同步VIEW_HISDB_MODEL_DATA
            6. redis_MysqlImpl.synNowRunDateNeedStaticDataToCodis()
                1. 同步OMPSE.CONF_COLLECTION_INDB
                    1. 
                2. 同步AUS_APP_B表
                3. 同步AUS_CONTEXT_B表
                4. 同步AUS_SERVICE_BUS_SERVER_B表最大id
                5. 同步AUS_SERVICE_BUS_SERVER_B表 服务总线模型
                6. 同步AUS_APP_NODE_B表最大id 应用节点模型
                7. 同步AUS_APP_NODE_B表 应用节点模型
                8. 同步AUS_PROCESS_NODE_B
            7. redis_MysqlImpl.synDeviceId()
        * 模型入库 static_model
            1. 从kafka获取xml
            2. 解析xml，转换成map
            3. 对于每个key 解析出ID和表号
                1. 获取到相应表号的域信息
                2. 插入或更新金泰模型表
                    1. 将查找映射名称从数据查找出来
                    2. 每条数据的sql参数 存放在redis key为：REDISKEY + sql + "_key"下
                    3. 每条数据的sql执行，
                    4. 
        * 同步数据
        * 代办任务

## 表说明
* SYS_REDISINFO ：同步告警阈值的 codis查询表
* AUS_APP_NODE_B 应用节点模型
* AUS_SERVICE_BUS_SERVER_B 服务总线模型


# 信息
## 作者
王俊磊
# 需求
* 对某个表同步不同步有开关
* 优化sql查询框架
