# Slimenano 插件

一个slimenano插件由插件类和插件描述文件组成

所有的插件都需要一个继承了 `BasePlugin` 的插件类，并且该类的路径需要与插件描述文件的 `path` 保持一致

使用 Instance 注解标识一个类为被管理类，上下文会自动创建单例并提供给其他 Instance 标识的类

实例可以通过实现 `InitializationBean` 接口来在上下文加载和卸载时执行一些方法

在 Instance 类中使用 Mount 注解来挂载 Instance 注解的类，Mount可以通过类型或者名字进行挂载。如果使用类型挂载但是有多个匹配类型的实例，则会抛出 `BeanRepeatNameException`

插件上下文提供了一些内置的可挂载对象

```java

@Mount("dataDir")
private File dataDir; //当前插件的数据目录 data/<插件类名>/

@Mount("bot")
private Robot bot; //插件用于操作的机器人接口

@Mount("information")
private PluginInformation information; //插件的信息 （插件描述文件解析而来）

@Mount("eventChannel")
private EventChannel eventChannel; //事件总线接口

@Mount("iGUIBridge")
private IGUIBridge bridge; //界面桥接口

@Mount("accessManager")
private AccessManager accessManager; //权限管理器，可用于动态申请权限

@Mount
private Context context; //插件上下文

```

可以使用 ConfigLocation 注解标注插件类来标识该插件使用了配置文件 应用会自动在插件数据目录中生成&lt;location&gt;.json文件

当插件启用了配置文件后，既可以使用 Configuration 注解创建配置类，上下文会自动从配置文件中读取配置并实例化成配置类

配置类字段修改后可以调用插件上下文的save方法保存更改

配置类需要 实现一个 DefaultConfiguration 来标识这个配置类接受一个默认配置，如果这个配置类的配置丢失或损坏，则会使用无参构造函数构造默认配置，否则将抛出异常并终止插件加载流程

插件中的日志请使用 slf4j 日志，可以使用 lombok 的 @Slf4j 注解生成 logger

如果发现你打印的日志中出现一个 `<unknown>` 那是因为这行日志的类没有添加 `@Marker` 注解，`@Marker` 注解中的内容会被填充到 `<unknown>` 的位置

！！！注：slimenano-api 库已经导入了 lombok

## 示例插件

示例插件实现了两个对话器 分别为文本对话器和回调对话器，并实现了一个配置类和两个指令（slimenano-cli-bridge）

## 创建自己的插件

首先克隆sn-robot项目并使用 mvn install 安装项目（注意修改 sn-bot.dir 输出目录）（没有上传公共仓库的痛/_ \）

克隆该项目，修改插件描述信息，写自己的逻辑，修改pom文件中的sn-bot.dir指向slimenano的根目录，运行 `mvn install`

插件文件将会被输出到 sn-bot.dir/plugins 中

请保证 slimenano-api 的 scope 为 provided，这很重要！！！

我该怎样调试？

在你的idea中添加Remote调试项，并将其提供的jvm参数添加到slimenano的启动命令中，运行slimenano并启动调试，把插件包放置于plugins文件夹下并载入插件即可调试
