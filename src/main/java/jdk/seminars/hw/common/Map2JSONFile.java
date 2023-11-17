package jdk.seminars.hw.common;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class Map2JSONFile {
public static<K,V> void toJsonFile(Map<K,V> map, String filename){
    Gson gson = new Gson();
    String data2file = gson.toJsonTree(map).toString();
    try (FileWriter fw = new FileWriter(filename)){
        fw.write(data2file);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }


//    for (Map.Entry<K,V> mapElement: map.entrySet()) {
//        K key = mapElement.getKey();
//        V value = mapElement.getValue();
//
//    }
}
}
