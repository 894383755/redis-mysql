# 版本
1.0
# redis-mysql
科东-同步服务 - 仅仅用在 西南
## 转换操作
### 
## 各个包功能
* xxhyf    						信息化研发  该包下同步服务的业务逻辑，下面是各个包的介绍
* xxhyf.aop                     拦截包
* xxhyf.annotation              注解包
* xxhyf.entity                  实体包
* xxhyf.config                  配置包
* xxhyf.main   					同步服务的启动项初始化
* xxhyf.mysql_redis				历史库MySQL同步到实时库redis
* xxhyf.notice					待办任务入库
* xxhyf.resolveXml				xml解析方法（工具）
* xxhyf.static_model			  静态模型入库
* xxhyf.synchro					同步服务
* xxhyf.util                    工具类

## 主要功能
1. 同步静态模型,和静态数据,:静态模型为入库程序(动态数据上送,负责人赵国卫)提供表信息数据,静态数据为(各个web服务提供了缓存的数据支撑)
2. 同步静态模型中每张表里的最大ID : 为插入数据最自增ID做准备
3. 模型上送: 用于福建放的硬件信息属于我们采集的,所以有了 模型的下发,和模型上送两个功能首先通过 模型下发功能生成规定格式的xml文件来下发模型,有采集程序解析后 第一次上送静态模型(举个例子: 服务器部署上采集之后,数据库里没有这台服服务器的磁盘 ,网卡等信息,需要有模型下发功能第一次下发空模型,有采集解析将模型静态信息存入kafka,在有模型同步程序进行模型入库).
4. 待办任务:  待办是接入设备信息入库的 也是用kafka中拉取待办消息 ,消息会以xml的形式告诉 新增,修改,退运某一台设备, 待办任务解析入库.
5. 静态模型触发式同步,添加删除修改了数据库的静态模型 需要 通知模型同步的模型同步功能同步数据库最新数据到redis

## 总体流程
1. RedisMysqlApplication springboot启动
2. start 运行（通过@PostConstruct）
    1. 各个线程任务初始化 init();
        * 查询每个表的最大id 存储到redis
        1. 获取SYS_TABLEINFO表的所有id和表名
        2. 通过表明查询该表的最大id
        3. 将最大id存储到 REDISKEY+id+"_num" 下
    2. 执行各个任务线程（通过执行计划）
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
                    1. 查询 ALARMMODEL
                    2. 每条数据 在 REDISKEY + "ALARMMODEL" 存放id
                    3. 每条数据 在 id 存放 结果map
                2. 同步VIEW_HISDB_MODEL_DATA
            6. redis_MysqlImpl.synNowRunDateNeedStaticDataToCodis()
                1. 同步CONF_COLLECTION_INDB
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
        * 同步数据 SynchroData
             1. 从kafka获取xml
                2. 解析xml，转换成map
                3. 对于每个key 解析出ID和表号
                4. 和redis_mysql 执行相同代码
        * 同步服务 Synchro
              1. 和SynchroData相同，但kafka分组不同
        * 代办任务 notice
            1. 从kafka中获取数据
            2. 解析xml，转换成map
            3. 对于每个key 解析出ID和表号
                1. 获取标号，根据"_"分割
                3. 查找SYS_NOTICE表下NOTICE_DESC对应值是否有数据，无则继续
                2. 对于每条数据（在SYS_NOTICE表插入数据）
                    * 1：新增 OPT参数为 add
                    * 2：更新 OPT参数为 update
                    * 3：退回 OPT参数为 back
                    * 4: 通知 OPT参数为 notice
3. 各项拦截
    1. 对所有的计划经行拦截，判断是否开启计划
        * 开启方式为配置yml
        * 只有类名则说明该类全部都允许
        * 类名.方法名说明允许指定方法名
        
## 表说明
* SYS_REDISINFO ：同步告警阈值的 codis查询表
* AUS_APP_NODE_B 应用节点模型
* AUS_SERVICE_BUS_SERVER_B 服务总线模型

## redis 
| key | value | 说明 | 存 | 取 |
| --- | --- | --- | --- | --- |
| REDISKEY + id + "-SYS_REDISINFO" |  |  |  |  |
| REDISKEY + sql |  |  |  |  |
| REDISKEY + sql + "_key" |  keys[i] |  |  |  |
| REDISKEY + sql + "_rule_" + keys[i] | {key：hkeys[i]，value：hvalues[i]} |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |
|  |  |  |  |  |

# 表信息结构
## 每张表id结构
### 福建
表号（前6） + 区域号 （中6） + 序列号 （后6位，自增）
### 西南、河南
表号（前4） + 区域号 （中6） + 序列号 （后8位，自增）

# 信息
## 作者
王俊磊
# 需求
* 对某个表同步不同步有开关
* 优化sql查询框架
