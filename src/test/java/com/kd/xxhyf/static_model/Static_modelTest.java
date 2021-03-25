package com.kd.xxhyf.static_model;

import com.kd.redis.config.RedisConfig;
import com.kd.xxhyf.config.MyAsyncConfigurer;
import com.kd.xxhyf.config.MyTaskExecutorConfig;
import com.kd.xxhyf.util.Connection;
import com.kd.xxhyf.static_model.core.StaticServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest()
@RunWith(SpringRunner.class)
class Static_modelTest {

    @Autowired
    private RedisConfig redisConfig;

    @Autowired
    private Connection connection;

    @Autowired
    StaticServiceImpl staticService;


    @BeforeEach
    void setUp() {
        System.err.println("开始停止");
        //MyTaskExecutorConfig.shutdown();
    }

    @AfterEach
    void tearDown() {
        while (MyAsyncConfigurer.getActiveCount() != 0){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void start() {
    }

    @Test
    void run() {
    }

    @Test
    public void resolveXml(){
        staticService.run(str3);
    }

    String str = "<lists>\n" +
            "<list device=\"\" type=\"51150\">\n" +
            "<CONTEXT_ID>sys,sys,sys,sys,sys,sys,sys,sys,sys,realtime,realtime,realtime,realtime,realtime,realtime,realtime,sys,sys,sys,sys,sys,sys,sys,sys,realtime,realtime,realtime,realtime,realtime,realtime,sys,sys,realtime,realtime,realtime,realtime,realtime,realtime,realtime,realtime,realtime,realtime,realtime,realtime,realtime,realtime,realtime,realtime,realtime,realtime,realtime,realtime,realtime,sys,realtime,realtime</CONTEXT_ID>\n" +
            "<APP_ID>base_srv,base_srv,base_srv,base_srv,base_srv,base_srv,base_srv,base_srv,base_srv,public,public,public,public,public,public,public,base_srv,base_srv,base_srv,base_srv,base_srv,base_srv,base_srv,base_srv,dsa,dsa,dsa,dsa,dsa,dsa,base_srv,base_srv,public,public,public,public,public,public,public,public,public,public,public,public,public,public,public,public,public,public,public,public,public,base_srv,public,public</APP_ID>\n" +
            "<PROCESS_ID>logd,sys_appwarn,msgtcprecv,sys_srv,msgbroker,msgtcpsend,pgm_send,pgm_recv,sys_warn_server,rtdb_down_srv,rtdb_srv,rtdb_pub,rtdbmodi,priv_srv,caseserver,mode_server,procm,ping_server,remote_exed,sys_processm,locatorupdate,cmdhis,system_check,sys_nicmonitor,dpaDSAComp,eaccs_alarmclient,eaccs_alarmserver,eaccs_transserver_m0,dsa_op,eaccs_dispatch,mon_procm,sys_app,warnserver,rtdbsynrcv,down_daemon,file_recv,rtdb_daemon_PUBLIC,rtdb_daemon,db_reinforce_sys,locatorservice,guiserv,evt_sender,evt_recv,sync_msg_recv,sync_rt_send_public,guiservfor100,middata,guiservext,ntpmon_server,hiscache_server,locator,rtdbmodi_region,syncmsgrec,sys_info,evtrecv,evtsender</PROCESS_ID>\n" +
            "<NODEID>710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105,710199010600000105</NODEID>\n" +
            "<time></time>\n" +
            "</list>\n" +
            "</lists>\n";

    String str1 = "<lists>\n" +
            "<list device=\"\" type=\"100020\">\n" +
            "<NODEID>100010350000000020</NODEID>\n" +
            "<DISKNAME>/dev/sda2</DISKNAME>\n" +
            "<time></time>\n" +
            "</list>\n" +
            "</lists>";

    String str3 = "2021-03-22 19:53:28 | INFO  | MyAsync-7 | com.kd.xxhyf.static_model.core.StaticServiceImpl | {_100140_0=[{SERVICE_NAME=midhs2, P_NODE=100010350000000018, TIME=, NODEID=sgs3-web2}, {SERVICE_NAME=midhs, P_NODE=100010350000000018, NODEID=sgs3-web2}, {SERVICE_NAME=midbrowser, P_NODE=100010350000000018, NODEID=sgs3-web2}, {SERVICE_NAME=middata, P_NODE=100010350000000018, NODEID=sgs3-web2}, {SERVICE_NAME=midmmi, P_NODE=100010350000000018, NODEID=sgs3-web2}, {SERVICE_NAME=midhs2, P_NODE=100010350000000018, NODEID=sgs3-web1},\n" +
            "\n" +
            " {SERVICE_NAME=midhs, P_NODE=100010350000000018, NODEID=sgs3-web1}, {SERVICE_NAME=middata, P_NODE=100010350000000018, NODEID=sgs3-web1}, {SERVICE_NAME=midbrowser, P_NODE=100010350000000018, NODEID=sgs3-web1}, {SERVICE_NAME=midmmi, P_NODE=100010350000000018, NODEID=sgs3-web1}, {SERVICE_NAME=gfile_serv, P_NODE=100010350000000018, NODEID=sgs3-web1}, {SERVICE_NAME=db_commit, P_NODE=100010350000000018, NODEID=sgs3-web1}, {SERVICE_NAME=ftpserv, P_NODE=100010350000000018, NODEID=sgs3-web1}, {SERVICE_NAME=model_modify, P_NODE=100010350000000018, NODEID=sgs3-web1}, {SERVICE_NAME=sqlsp_srv, P_NODE=100010350000000018, NODEID=sgs3-web1}, {SERVICE_NAME=down_srv, P_NODE=100010350000000018, NODEID=sgs3-web1}, {SERVICE_NAME=rtdb_server_S_4, P_NODE=100010350000000018, NODEID=sgs3-web1}, {SERVICE_NAME=rtdb_server_P_4, P_NODE=100010350000000018, NODEID=sgs3-web1}, {SERVICE_NAME=rtdb_server_4, P_NODE=100010350000000018, NODEID=sgs3-web1}, {SERVICE_NAME=evt_recv, P_NODE=100010350000000018, NODEID=sgs3-web1}, {SERVICE_NAME=evt_sender, P_NODE=100010350000000018, NODEID=sgs3-web1}, {SERVICE_NAME=download_daemon, P_NODE=100010350000000018, NODEID=sgs3-web1}, {SERVICE_NAME=priv_server, P_NODE=100010350000000018, NODEID=sgs3-web1}, {SERVICE_NAME=rtdb_server_S, P_NODE=100010350000000018, NODEID=sgs3-web1}, {SERVICE_NAME=rtdb_server_P, P_NODE=100010350000000018, NODEID=sgs3-web1}, {SERVICE_NAME=rtdb_server, P_NODE=100010350000000018, NODEID=sgs3-web1}, {SERVICE_NAME=ftpserv_chat, P_NODE=100010350000000018, NODEID=sgs3-gw2}, {SERVICE_NAME=impubguodiao, P_NODE=100010350000000018, NODEID=sgs3-gw2}, {SERVICE_NAME=imapp, P_NODE=100010350000000018, NODEID=sgs3-gw2}, {SERVICE_NAME=gfile_serv, P_NODE=100010350000000018, NODEID=sgs3-web2}, {SERVICE_NAME=evt_recv, P_NODE=100010350000000018, NODEID=sgs3-wapp1}, {SERVICE_NAME=evt_sender, P_NODE=100010350000000018, NODEID=sgs3-wapp1}, {SERVICE_NAME=midbrowser, P_NODE=100010350000000018, NODEID=sgs3-wapp1}, {SERVICE_NAME=middata, P_NODE=100010350000000018, NODEID=sgs3-wapp1}, {SERVICE_NAME=midmmi, P_NODE=100010350000000018, NODEID=sgs3-wapp1}, {SERVICE_NAME=download_daemon, P_NODE=100010350000000018, NODEID=sgs3-wapp1}, {SERVICE_NAME=priv_server, P_NODE=100010350000000018, NODEID=sgs3-wapp1}, {SERVICE_NAME=rtdb_server_S, P_NODE=100010350000000018, NODEID=sgs3-wapp1}, {SERVICE_NAME=rtdb_server_P, P_NODE=100010350000000018, NODEID=sgs3-wapp1}, {SERVICE_NAME=rtdb_server, P_NODE=100010350000000018, NODEID=sgs3-wapp1}, {SERVICE_NAME=midmmi, P_NODE=100010350000000018, NODEID=sgs3-gw2}, {SERVICE_NAME=midmmi, P_NODE=100010350000000018, NODEID=sgs3-gw1}, {SERVICE_NAME=middata, P_NODE=100010350000000018, NODEID=sgs3-gw1}, {SERVICE_NAME=rtdb_server, P_NODE=100010350000000018, NODEID=sgs3-gw1}, {SERVICE_NAME=download_daemon, P_NODE=100010350000000018, NODEID=sgs3-gw1}, {SERVICE_NAME=midbrowser, P_NODE=100010350000000018, NODEID=sgs3-gw1}, {SERVICE_NAME=rtdb_server_P, P_NODE=100010350000000018, NODEID=sgs3-gw1}, {SERVICE_NAME=evt_recv, P_NODE=100010350000000018, NODEID=sgs3-gw1}, {SERVICE_NAME=rtdb_server_S, P_NODE=100010350000000018, NODEID=sgs3-gw1}, {SERVICE_NAME=evt_sender, P_NODE=100010350000000018, NODEID=sgs3-gw1}, {SERVICE_NAME=priv_server, P_NODE=100010350000000018, NODEID=sgs3-gw1}, {SERVICE_NAME=evt_recv, P_NODE=100010350000000018, NODEID=sgs3-gw2}, {SERVICE_NAME=download_daemon, P_NODE=100010350000000018, NODEID=sgs3-gw2}, {SERVICE_NAME=rtdb_server_P, P_NODE=100010350000000018, NODEID=sgs3-gw2}, {SERVICE_NAME=middata, P_NODE=100010350000000018, NODEID=sgs3-gw2}, {SERVICE_NAME=evt_sender, P_NODE=100010350000000018, NODEID=sgs3-gw2}, {SERVICE_NAME=rtdb_server, P_NODE=100010350000000018, NODEID=sgs3-gw2}, {SERVICE_NAME=midbrowser, P_NODE=100010350000000018, NODEID=sgs3-gw2}, {SERVICE_NAME=rtdb_server_S, P_NODE=100010350000000018, NODEID=sgs3-gw2}, {SERVICE_NAME=priv_server, P_NODE=100010350000000018, NODEID=sgs3-gw2}, {SERVICE_NAME=sqlsp_srv, P_NODE=100010350000000018, NODEID=sgs3-web2}, {SERVICE_NAME=rtdb_server_S, P_NODE=100010350000000018, NODEID=sgs3-web2}, {SERVICE_NAME=ftpserv, P_NODE=100010350000000018, NODEID=sgs3-web2}, {SERVICE_NAME=rtdb_server_P, P_NODE=100010350000000018, NODEID=sgs3-web2}, {SERVICE_NAME=download_daemon, P_NODE=100010350000000018, NODEID=sgs3-web2}, {SERVICE_NAME=db_commit, P_NODE=100010350000000018, NODEID=sgs3-web2}, {SERVICE_NAME=rtdb_server, P_NODE=100010350000000018, NODEID=sgs3-web2}, {SERVICE_NAME=priv_server, P_NODE=100010350000000018, NODEID=sgs3-web2}, {SERVICE_NAME=down_srv, P_NODE=100010350000000018, NODEID=sgs3-web2}, {SERVICE_NAME=model_modify, P_NODE=100010350000000018, NODEID=sgs3-web2}, {SERVICE_NAME=chat_duty, P_NODE=100010350000000018, NODEID=sgs3-gw2}]}\n";
}