# luminate 消息推送平台

## 基本介绍

**核心功能**：统一各种类型消息（短信、邮件）的发送接口，对消息生命周期全链路追踪。

当企业开发中有发送消息的需求，如果没有统一的消息下发平台，会造成每一个业务方都需要自己对接所需的下发消息 SDK，造成开发效率下降，代码冗余等问题。如图所示



<img src="https://raw.githubusercontent.com/warr99/picture-space/main/luminate-readme/luminate-readme-01.png" alt="readme01" style="zoom:50%;" />

**luminate** 通过接入各种消息下发的第三方服务，**向业务方提供统一接口**，利于对功能的收拢，以及提高业务需求开发的效率。

<img src="https://raw.githubusercontent.com/warr99/picture-space/main/luminate-readme/luminate-readme-03.png" alt="readme03" style="zoom:50%;" />

## 系统架构

**下面是 luminate 的系统架构图**

<img src="https://raw.githubusercontent.com/warr99/picture-space/main/luminate-readme/luminate-readme-02.png" alt="readme02" style="zoom: 67%;" />

**各个工程模块功能如下**

|         **工程模块**          |                           **作用**                           |
| :---------------------------: | :----------------------------------------------------------: |
|      **luminate-common**      |           项目公共包：存储着项目公共常量/枚举/Bean           |
|     **luminate-support**      |                 项目工具包：对接中间件/组件                  |
|       **luminate-cron**       |        定时任务模块：对xxl-job封装和项目定时任务逻辑         |
|       **luminate-web**        |               后台管理模块：提供接口给前端调用               |
|   **luminate-service-api**    |       消息接入层接口定义模块：只有接口和必要的入参依赖       |
| **luminate-service-api-impl** |             消息接入层具体实现模块：真实处理请求             |
|     **luminate-handler**      |                消息处理逻辑层：消费MQ下发消息                |
|      **luminate-stream**      | 实时处理模块：利用flink实时处理下发链路数据，将操作日志写入 redis |

## 技术栈

|         技术栈         |          实现           |
| :--------------------: | :---------------------: |
|      **编程语言**      |     Java（JDK 1.8)      |
|    **项目管理工具**    |        Maven 3.x        |
|    **集成开发工具**    |        IDEA 2023        |
|     **部署服务器**     |       Centos 7.6        |
|    **系统部署工具**    | Docker & Docker-compose |
|      **项目环境**      |    Springboot 2.5.6     |
|    **关系型数据库**    |         MySQL 8         |
|     **缓存数据库**     |       Redis 6.2.6       |
|      **ORM框架**       |      Mybatis-plus       |
| **分布式定时任务框架** |      Xxl-job 2.3.0      |
|   **分布式配置中心**   |         Apollo          |
|      **消息队列**      |          Kafka          |
|   **分布式计算引擎**   |      Flink 1.16.0       |
