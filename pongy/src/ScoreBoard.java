public class ScoreBoard extends Entity {

    private int score;

    public void registerHit()
    {
        score++;
        System.out.println("score: " + score);
        AudioManager.getInstance().play("score");
    }

    

}
