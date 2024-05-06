package org.example.Jsons;

import com.google.gson.Gson;
import org.example.Infos.SchedulerInfo;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class JsonReader {

    public SchedulerInfo readJson(String name) {
        SchedulerInfo schedulerInfo;
        String path = switch (name) {
            case "fcfs" -> "src/main/java/org/example/Jsons/exemplo_fcfs.json";
            case "rr" -> "src/main/java/org/example/Jsons/exemplo_rr.json";
            case "rm" -> "src/main/java/org/example/Jsons/exemplo_rm.json";
            case "edf" -> "src/main/java/org/example/Jsons/exemplo_edf.json";
            default -> "";
        };

        try (Reader reader = new FileReader(path)) {
            schedulerInfo = new Gson().fromJson(reader, SchedulerInfo.class);
            System.out.println(schedulerInfo);
            System.out.println("==============================================================================================");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return schedulerInfo;
    }
}
