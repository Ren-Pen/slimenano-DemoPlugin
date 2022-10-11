package com.demo;

import com.slimenano.sdk.robot.messages.content.SNText;
import lombok.extern.slf4j.Slf4j;
import com.slimenano.sdk.config.ConfigLocation;
import com.slimenano.sdk.core.Robot;
import com.slimenano.sdk.framework.annotations.Mount;
import com.slimenano.sdk.logger.Marker;
import com.slimenano.sdk.plugin.BasePlugin;

import java.util.HashMap;

@ConfigLocation(location = "config")
@Slf4j
@Marker("示例插件")
public class DemoPlugin extends BasePlugin {

    /**
     * 插件上下文已经彻底加载完成，当这个方法执行时则代表插件上下文的所有 onLoad 方法均执行完成，并且已经成功加载
     */
    @Override
    public void loaded() {
        super.loaded();
    }

    /**
     * 实例加载完成，所有的Mount字段均已挂载，注意，各个实例中的onLoad方法执行顺序是随机的
     * @throws Exception
     */
    @Override
    public void onLoad() throws Exception {
        super.onLoad();
    }

    /**
     * 插件被卸载，注意，各个实例中的 onDestroy方法执行顺序是随机的
     * @throws Exception
     */
    @Override
    public void onDestroy() throws Exception {
        super.onDestroy();
    }

    /**
     * CLI界面桥扩展方法，指令 demo 可接受参数 --debug | -d
     * @param args
     * @return 指令执行反馈
     */
    public boolean demo(HashMap<String, String> args){

        if (args.containsKey("debug")){
            log.info("调试参数！");
        }else if (args.containsKey("demo")){
            log.info("运行了！ROBOT:" + getBot().getCoreType());
        }else{
            log.info("无参！");
        }

        return true;
    }

    public boolean friend(HashMap<String, String> args){
        long target = 0L;
        String msg = args.get("msg");
        try{
            target = Long.parseLong(args.get("target"));
        }catch (Exception e){
            log.error("错误的输入！", e);
            return false;
        }

        getBot().getFriend(target).sendMessage(getBot(), new SNText(msg).toChain());
        return true;
    }

}
