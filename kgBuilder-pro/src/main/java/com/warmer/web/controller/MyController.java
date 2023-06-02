package com.warmer.web.controller;

import com.warmer.web.entity.MyData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
public class MyController extends BaseController{
    @PostMapping("/data")
    public void processData(@RequestBody MyData myData) {
        try {
            // 设置Python文件路径和参数
            String pythonFile = "cq.py";
            // 在这里处理接收到的 JSON 数据
            String text = myData.getText();
            String zhuti = myData.getZhuti();
            String gx1 = myData.getGx1();
            String gx2 = myData.getGx2();
            String gx3 = myData.getGx3();
//            String zhuti = "竞赛名称";
//            String gx1 = "主办方";
//            String gx2 = "承办方";
//            String gx3 = "已举办次数";
//            String text = "2022语言与智能技术竞赛由中国中文信息学会和中国计算机学会联合主办，百度公司、中国中文信息学会评测工作委员会和中国计算机学会自然语言处理专委会承办，已连续举办4届，成为全球最热门的中文NLP赛事之一";

            // 构建命令列表
            ProcessBuilder pb = new ProcessBuilder("python", pythonFile);
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            pb.redirectErrorStream(true);

            // 设置环境变量
            pb.environment().put("zhuti", zhuti);
            pb.environment().put("gx1", gx1);
            pb.environment().put("gx2", gx2);
            pb.environment().put("gx3", gx3);
            pb.environment().put("text", text);

            // 启动进程并执行命令
            Process process = pb.start();

            // 读取命令输出
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder output = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                output.append(line);
            }

            // 等待命令执行完成
            int exitCode = process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }
}
