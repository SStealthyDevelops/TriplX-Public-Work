package minigames.triplxmc.skydomination.map.constructors;

import com.sun.corba.se.spi.orbutil.fsm.ActionBase;
import lombok.Getter;
import lombok.Setter;
import minigames.triplxmc.skydomination.game.Game;
import minigames.triplxmc.skydomination.nms.Actionbar;
import minigames.triplxmc.skydomination.player.Profile;
import minigames.triplxmc.skydomination.player.team.Team;
import minigames.triplxmc.skydomination.utils.world.WorldUtils;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import triplx.core.api.chat.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class Base {

    // array of blocks that contain the base
    private final NoWorldLocation[] blocks;

    private boolean contested = false;
    private boolean converting = false;
    private List<Profile> profiles;
    private Team holding;

    private Team capturing;

    public int captured = 0;
    private World world;

    public Base(NoWorldLocation loc1, NoWorldLocation loc2) {
        Set<NoWorldLocation> locs = WorldUtils.blocksFromTwoPoints(loc1, loc2);
        this.blocks = locs.toArray(new NoWorldLocation[0]);
        this.profiles = new ArrayList<>();
    }

    public void addProfile(Profile profile) {
        if (profiles.contains(profile)) return;
        getProfiles().add(profile);

        boolean contested = false;
        for (Profile p : profiles) {
            if (p.getTeam() != profile.getTeam()) {
                contested = true;
                break;
            }
        }

        this.contested = contested;
//        System.out.println("updated contested to: " + contested);

        if (holding != profile.getTeam() && !this.contested) {
            capturing = profile.getTeam();



        }

    }

    public void removeProfile(Profile profile) {
        profiles.remove(profile);
        profile.setActionBar("");
    }

    public void update() {

        contested = false;
        Team team = null;
        for (Profile profile : profiles) {
            if (team == null) team = profile.getTeam();
            if (team != profile.getTeam()) {
                this.contested = true;
            }
        }

        if (holding != null && !this.contested) {
            for (Profile profile : profiles) { // if being held and 2 conflicting teams arent on base, convert base
                if (profile.getTeam() != holding) {
                    // start converting into this new team
                    int total = blocks.length - 1;
                    captured += 2;
                    World w = capturing.getGame().getActiveWorld();
                    this.world = w;
                    w.playSound(NoWorldLocation.getLocationWithWorld(w, blocks[0]), Sound.ORB_PICKUP, 0.4f, 1.0f);

                    for (int i = 0; i <= captured; i++) {
                        if (!(i < blocks.length)) break;
                        NoWorldLocation.getLocationWithWorld(w, blocks[i]).getBlock().setData((byte) (capturing == capturing.getGame().getRedTeam() ? 14 : 3));
                    }

                    StringBuilder b = new StringBuilder(Color.cc("&a"));
                    for (int i = 0; i < captured; i++) {
                        b.append("&a&l[]");
                    }
                    for (int i = 0; i < (total - captured); i++) {
                        b.append("&c&l[]");
                    }
                    this.actionBarAll(b.toString());
                    break; // no need to keep going in the loop
                }
            }

            if (!this.converting) holding.addScore(2);

        }

        if (profiles.size() == 0 && captured > 0) {
            captured = 0;
            for (NoWorldLocation loc : blocks) {
                Location locW = NoWorldLocation.getLocationWithWorld(world, loc);
                Block block = world.getBlockAt(locW);
                block.setData((byte) 0);
            }
        }


        int total = blocks.length - 1;

        //TODO: check if conflicting teams are on base

        if (capturing != null) {
            captured += 2;
            World w = capturing.getGame().getActiveWorld();
            this.world = w;
            w.playSound(NoWorldLocation.getLocationWithWorld(w, blocks[0]), Sound.ORB_PICKUP, 0.4f, 1.0f);

            for (int i = 0; i <= captured; i++) {
                if (!(i < blocks.length)) break;
                    NoWorldLocation.getLocationWithWorld(w, blocks[i]).getBlock().setData((byte) (capturing == capturing.getGame().getRedTeam() ? 14 : 3));
            }

            StringBuilder b = new StringBuilder(Color.cc("&a"));
            for (int i = 0; i < captured; i++) {
                b.append("&a&l[]");
            }
            for (int i = 0; i < (total - captured); i++) {
                b.append("&c&l[]");
            }
            for (Profile profile : profiles) {
                profile.setActionBar(Color.cc(b.toString()));
            }
//            System.out.println("adding points towards capture");
        }


        if (captured >= total) {
            captured = 0;
            holding = capturing;
            capturing = null;
            Game game = holding.getGame();
//            System.out.println("set holding to " + capturing);
            int blockData = holding == game.getRedTeam() ? 14 : 3;
//            System.out.println(blockData);
            for (NoWorldLocation loc : blocks) {
                World world = getHolding().getGame().getActiveWorld();
                Location locW = NoWorldLocation.getLocationWithWorld(world, loc);
                Block block = world.getBlockAt(locW);
                block.setData((byte) blockData);
            }

            for (Profile profiles : profiles) {
                profiles.setCredits(profiles.getCredits() + 50);
            }
            return;
        } //TODO:  do blocks too, later lol
        // start switching the base to new team color over time




    }

    private void actionBarAll(String s) {
        for (Profile profile : profiles) {
            profile.setActionBar(Color.cc(s));
        }
    }



}
