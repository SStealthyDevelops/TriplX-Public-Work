package triplx.hub.core.hub;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import triplx.hub.core.Hubcore;

import java.io.*;

public class JSONManager {

    public static void init() {
        try {
            File hubsFile = new File(Hubcore.getInstance().getDataFolder(), "hubs.json");

            if (!hubsFile.exists()) {
                initFile(hubsFile);
            }

            JSONObject oj = (JSONObject) new JSONParser().parse(new InputStreamReader(new FileInputStream(hubsFile)));

            if (oj == null) {
                System.out.println("Failed to read hubs.json - ERROR CODE: 5x00001");
                return;
            }

            JSONArray array = (JSONArray) oj.get("hubs");

            // parse hubs

            for (Object o : array) {
                JSONObject object = (JSONObject) o;

                String world;
                String type;
                int maxPlayers;

                try {
                    world = object.get("world").toString();
                    type = object.get("type").toString();
                    maxPlayers = Integer.parseInt(object.get("max-players").toString());
                } catch (Exception e) {
                    System.out.println("Failed to read hubs in hubs.json - ERROR CODE: 5x00002");
                    world = "world";
                    type = "MAIN";
                    maxPlayers = 12;
                }

                Hub hub = new Hub(world, maxPlayers, type);
                HubManager.getInstance().addHub(hub);
            }



        } catch (Exception e) {
            System.out.println("Failed to use hubs.json - ERROR CODE 5x00003");
        }
    }

    public static void addHub(Hub hub) {
        JSONObject obj = new JSONObject();

        obj.put("world", hub.getWorld());
        obj.put("type", hub.getType());
        obj.put("max-players", hub.getMaxPlayers());

        addHub(obj);
    }

    private static void addHub(JSONObject object) {
        try {
            File hubsFile = new File(Hubcore.getInstance().getDataFolder(), "hubs.json");

            JSONObject oj = (JSONObject) new JSONParser().parse(new InputStreamReader(new FileInputStream(hubsFile)));

            JSONArray array = (JSONArray) oj.get("hubs");

            array.add(object);

            oj.put("hubs", array);

            try (FileWriter fileWriter = new FileWriter(hubsFile)) {

                fileWriter.write(oj.toJSONString());
                fileWriter.flush();

            } catch (IOException e){
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.out.println("Error updating hub list - ERROR 5x00004");
        }


    }


    private static void initFile(File file) {
         try {

             file.createNewFile();

             JSONObject obj1 = new JSONObject();
             JSONArray hubsArray = new JSONArray();

             // basic hub

             JSONObject hub = new JSONObject();
             hub.put("world", "world");
             hub.put("max-players", 20);
             hub.put("type", "MAIN");

            hubsArray.add(hub);
            obj1.put("hubs", hubsArray);

             try (FileWriter fileWriter = new FileWriter(file)) {

                 fileWriter.write(obj1.toJSONString());
                 fileWriter.flush();

             } catch (IOException e){
                 e.printStackTrace();
             }

         } catch (Exception e) {
             e.printStackTrace();
         }

    }

}
