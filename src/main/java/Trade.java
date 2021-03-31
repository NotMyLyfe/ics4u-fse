public class Trade{
    private final int DESERT = 0,
            GRAIN = 1,
            LUMBER = 2,
            WOOL = 3,
            ORE = 4,
            BRICK = 5;
    int[] resources = {0,0,0,0,0,0};
    public Trade(int[] stuff){

        for (int i = 1; i < 6; i ++) {
            resources[i] += stuff[i];
        }

    }

    public boolean canTrade(Player p){
        for (int i = 1; i < 6; i ++){
            if (-resources[i] > p.resourceNum(i)){
                return false;
            }
        }
        return true;
    }

    public void makeTrade(Player p){
        for (int i = 1; i < 6; i ++){
            p.resources[i] += resources[i];
        }
    }
}
