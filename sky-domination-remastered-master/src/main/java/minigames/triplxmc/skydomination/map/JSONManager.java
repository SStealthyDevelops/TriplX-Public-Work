package minigames.triplxmc.skydomination.map;

import lombok.Getter;
import lombok.Setter;
import minigames.triplxmc.skydomination.Core;
import minigames.triplxmc.skydomination.map.constructors.Base;
import minigames.triplxmc.skydomination.map.constructors.NoWorldLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import triplx.core.api.chat.Color;


import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("all") // everything is in a try/catch
public class JSONManager {

    @Getter
    @Setter
    private static JSONManager instance;

    public void init() {
        try {
            File mapsJSON = new File(Core.getInstance().getDataFolder(), "maps.json");

            if (!mapsJSON.exists()) {
                initFile(mapsJSON);
            }

            JSONObject oj = (JSONObject) new JSONParser().parse(new InputStreamReader(new FileInputStream(mapsJSON)));

            if (oj == null) {
                throw new NullPointerException("oj is null");
            }

            JSONArray array = (JSONArray) oj.get("maps");

            for (Object mapObject : array) {
                JSONObject map = (JSONObject) mapObject;

                String name = map.get("name").toString();
                String worldName = map.get("world").toString();

                JSONArray spawnPoints1 = (JSONArray) map.get("spawnpoints_team_1");
                ArrayList<String> spawnPoints1List = new ArrayList<>();

                for (Object o : spawnPoints1) {
                    spawnPoints1List.add(o.toString());
                }

                JSONArray spawnPoints2 = (JSONArray) map.get("spawnpoints_team_2");
                ArrayList<String> spawnPoints2List = new ArrayList<>();
                for (Object o : spawnPoints2) {
                    spawnPoints2List.add(o.toString());
                }


                String[] spawnPointsArray = spawnPoints1List.toArray(new String[0]);
                String[] spawnPointsArray2 = spawnPoints2List.toArray(new String[0]);

                JSONObject border = (JSONObject) map.get("border");
                String borderX = border.get("x").toString();
                String borderY = border.get("y").toString();
                String borderZ = border.get("z").toString();


                JSONObject rules = (JSONObject) map.get("rules");
                JSONArray blocksArray = (JSONArray) rules.get("unbreakable-blocks");
                ArrayList<String> blocksList = new ArrayList<>();

                for (Object objj : blocksArray) {
                    blocksList.add(objj.toString());
                }

                String[] blocksArrayStr = blocksList.toArray(new String[0]);
                ArrayList<Material> materials = new ArrayList<>();
                for (String s : blocksArrayStr) {
                    materials.add(Material.valueOf(s));
                }

                JSONArray basesArray = (JSONArray) map.get("bases");

                ArrayList<Base> basesArrayList = new ArrayList<>();



                for (Object baseObject : basesArray) {
                    JSONObject base = (JSONObject) baseObject;
                    String location1 = base.get("location-1").toString();
                    String[] ints1 = location1.split(" ");


                    String location2 = base.get("location-2").toString();
                    String[] ints2 = location2.split(" ");

                    int x1 = Integer.parseInt(ints1[0]), y1 = Integer.parseInt(ints1[1]), z1 = Integer.parseInt(ints1[2]);
                    int x2 = Integer.parseInt(ints2[0]), y2 = Integer.parseInt(ints2[1]), z2 = Integer.parseInt(ints2[2]);

                    NoWorldLocation loc1 = new NoWorldLocation(x1, y1, z1);
                    NoWorldLocation loc2 = new NoWorldLocation(x2, y2, z2);



                    Base b = new Base(loc1, loc2);
                    basesArrayList.add(b);
                }

                String lobbySpawn = map.get("lobby-spawn").toString();
                
                Set<NoWorldLocation> teamShopStrings = new HashSet<>();
                JSONArray teamShops = (JSONArray) map.get("team-shop-locs");
                for (Object o : teamShops) {
                    String[] s = o.toString().split(" ");
                    teamShopStrings.add(new NoWorldLocation(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2])));
                }

                Set<NoWorldLocation> personalShopStrings = new HashSet<>();
                JSONArray personalShops = (JSONArray) map.get("personal-shop-locs");
                for (Object o : personalShops) {
                    String[] s = o.toString().split(" ");
                    personalShopStrings.add(new NoWorldLocation(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Integer.parseInt(s[2])));
                }

                

                Base[] bases = basesArrayList.toArray(new Base[0]);

                GameMap gameMap = new GameMap(name, worldName, spawnPointsArray, spawnPointsArray2, borderX, borderY, borderZ,
                        materials.toArray(new Material[0]), bases, lobbySpawn, personalShopStrings, teamShopStrings);
                MapManager.getInstance().addMap(gameMap);
            }






        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(Color.cc("&cCould not read maps.json. Priniting error log:"));
            e.printStackTrace();
        }
    }

    private void initFile(File f) {
        try {
            f.createNewFile();
            JSONObject object = new JSONObject();
            JSONArray array = new JSONArray();

            JSONObject arena = new JSONObject();

            arena.put("name", "Skyworld");
            arena.put("world", "world");

            JSONArray spawn1 = new JSONArray();
            spawn1.add("100 50 100");
            spawn1.add("102 50 100");

            arena.put("spawnpoints_team_1", spawn1);

            JSONArray spawn2 = new JSONArray();
            spawn2.add("200 50 200");
            spawn2.add("202 50 200");

            arena.put("spawnpoints_team_2", spawn2);

            JSONObject border = new JSONObject();

            border.put("x", "100 100");
            border.put("y", "0 200");
            border.put("z", "100 100");

            arena.put("border", border);

            JSONObject rules = new JSONObject();
            JSONArray array1 = new JSONArray();
            array1.add("DIRT");
            array1.add("GRASS");
            array1.add("STONE");

            rules.put("unbreakable-blocks", array1);

            arena.put("rules", rules);

            JSONArray basesArray = new JSONArray();
            JSONObject base1 = new JSONObject();

            base1.put("location-1", "20 20 20");
            base1.put("location-2", "25 20 25");
            basesArray.add(base1);

            JSONObject base2 = new JSONObject();

            base2.put("location-1", "50 20 50");
            base2.put("location-2", "55 20 55");
            basesArray.add(base2);

            arena.put("lobby-spawn", "0 0 0 0 0");

            arena.put("bases", basesArray);

            array.add(arena);
            object.put("maps", array);

            try (FileWriter fileWriter = new FileWriter(f)) {

                fileWriter.write(object.toJSONString());
                fileWriter.flush();

            } catch (IOException e){
                e.printStackTrace();
            }



        }catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(Color.cc("&cCritical error creating maps.json... disabling plugin."));

            e.printStackTrace();

            Core.getInstance().getServer().getPluginManager().disablePlugin(Core.getInstance());
        }


    }



}
